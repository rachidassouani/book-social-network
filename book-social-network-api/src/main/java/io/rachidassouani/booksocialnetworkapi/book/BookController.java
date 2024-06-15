package io.rachidassouani.booksocialnetworkapi.book;

import io.rachidassouani.booksocialnetworkapi.common.PageResponse;
import io.rachidassouani.booksocialnetworkapi.history.BorrowedBookResponse;
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

    @GetMapping("{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
    }
}
