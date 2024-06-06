package io.rachidassouani.booksocialnetworkapi.exception;

import java.time.LocalDateTime;
import java.util.Set;

public record ExceptionResponse(
        Integer businessErrorCode,
        String businessErrorDescription,
        String exceptionMessage,
        String exceptionPath,
        Set<String> validationErrors,
        LocalDateTime exceptionDateTime) {}
