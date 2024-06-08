package io.rachidassouani.booksocialnetworkapi.book;

import io.rachidassouani.booksocialnetworkapi.feedback.Feedback;
import io.rachidassouani.booksocialnetworkapi.user.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Long saveBook(BookRequest bookRequest, Authentication connectedUser) {
        AppUser user = ((AppUser) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(bookRequest);
        book.setOwner(user);
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    public BookResponse findBookById(Long bookId) {
        BookResponse foundedBook = bookRepository
                .findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID "+ bookId));

        return foundedBook;
    }

}
