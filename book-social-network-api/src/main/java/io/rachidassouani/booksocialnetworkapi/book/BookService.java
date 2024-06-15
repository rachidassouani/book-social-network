package io.rachidassouani.booksocialnetworkapi.book;

import io.rachidassouani.booksocialnetworkapi.common.PageResponse;
import io.rachidassouani.booksocialnetworkapi.history.BookTransactionHistory;
import io.rachidassouani.booksocialnetworkapi.history.BookTransactionHistoryRepository;
import io.rachidassouani.booksocialnetworkapi.history.BorrowedBookResponse;
import io.rachidassouani.booksocialnetworkapi.user.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.rachidassouani.booksocialnetworkapi.book.BookSpecification.withOwnerId;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, BookTransactionHistoryRepository bookTransactionHistoryRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookTransactionHistoryRepository = bookTransactionHistoryRepository;
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

    public List<BookResponse> findAllBook() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<BookResponse> findAllBooks(int page,
                                                   int size,
                                                   Authentication connectedUser) {

        AppUser user = ((AppUser) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").descending());

        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());

        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BookResponse> findAllBooksByOwner(
            int page,
            int size,
            Authentication connectedUser) {

        AppUser user = ((AppUser) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").descending());

        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);

        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page,
                                       int size,
                                       Authentication connectedUser) {

        AppUser user = ((AppUser) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").descending());

        Page<BookTransactionHistory> borrowedBooks = bookTransactionHistoryRepository
                .findAllBorrowedBooks(pageable, user.getId());

        List<BorrowedBookResponse> borrowedBooksResponse = borrowedBooks
                .stream()
                .map(bookMapper::toBorrowedBookResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                borrowedBooksResponse,
                borrowedBooks.getNumber(),
                borrowedBooks.getSize(),
                borrowedBooks.getTotalElements(),
                borrowedBooks.getTotalPages(),
                borrowedBooks.isFirst(),
                borrowedBooks.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page,
                                       int size,
                                       Authentication connectedUser) {

        AppUser user = ((AppUser) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").descending());

        Page<BookTransactionHistory> returnedBooks = bookTransactionHistoryRepository
                .findAllReturnedBooks(pageable, user.getId());

        List<BorrowedBookResponse> borrowedBooksResponse = returnedBooks
                .stream()
                .map(bookMapper::toBorrowedBookResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                borrowedBooksResponse,
                returnedBooks.getNumber(),
                returnedBooks.getSize(),
                returnedBooks.getTotalElements(),
                returnedBooks.getTotalPages(),
                returnedBooks.isFirst(),
                returnedBooks.isLast());
    }
}