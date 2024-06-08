package io.rachidassouani.booksocialnetworkapi.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Long> saveBook(@RequestBody @Valid BookRequest bookRequest,
                                      Authentication connectedUser) {
        return ResponseEntity.ok(bookService.saveBook(bookRequest, connectedUser));
    }

    @PostMapping("{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }
}
