package io.rachidassouani.booksocialnetworkapi.auth;

import io.rachidassouani.booksocialnetworkapi.email.EmailService;
import io.rachidassouani.booksocialnetworkapi.email.EmailTemplateName;
import io.rachidassouani.booksocialnetworkapi.role.RoleRepository;
import io.rachidassouani.booksocialnetworkapi.user.AppUser;
import io.rachidassouani.booksocialnetworkapi.user.Token;
import io.rachidassouani.booksocialnetworkapi.user.TokenRepository;
import io.rachidassouani.booksocialnetworkapi.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AuthenticationService {

    @Value("${application.mailing.frontend.activateAccountUrl}")
    private String activateAccountUrl;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void register(RegistrationRequest registrationRequest) throws MessagingException {
        var role = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER not found"));

        AppUser userToSave = new AppUser();
        userToSave.setFirstName(registrationRequest.getFirstName());
        userToSave.setLastName(registrationRequest.getLastName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setAccountLocked(false);
        userToSave.setEnabled(false);
        userToSave.setRoles(List.of(role));

        AppUser savedUser = userRepository.save(userToSave);

        // send validation email
        sendValidationEmail(savedUser);

    }

    private void sendValidationEmail(AppUser appUser) throws MessagingException {
        var savedToken = saveToken(appUser);
        emailService.sendEmail(
                appUser.getEmail(),
                appUser.getFullName(),
                "Account Activation",
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activateAccountUrl,
                savedToken
        );

    }

    private String saveToken(AppUser appUser) {
        String token = generateToken();
        Token tokenToSave = new Token();
        tokenToSave.setUser(appUser);
        tokenToSave.setToken(token);
        tokenToSave.setCreatedAt(LocalDateTime.now());
        tokenToSave.setExpiresAt(LocalDateTime.now().plusHours(1));

        Token savedToken = tokenRepository.save(tokenToSave);
        return savedToken.getToken();
    }

    private String generateToken() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(charSet.length());
            stringBuilder.append(charSet.charAt(randomInt));
        }
        return stringBuilder.toString();
    }
}
