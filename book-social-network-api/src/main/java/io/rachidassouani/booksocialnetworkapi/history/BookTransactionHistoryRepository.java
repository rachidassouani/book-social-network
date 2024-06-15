package io.rachidassouani.booksocialnetworkapi.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long> {
    @Query("""
            SELECT bookHistory
            FROM BookTransactionHistory bookHistory
            AND bookHistory.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Long userId);

    @Query("""
            SELECT bookHistory
            FROM BookTransactionHistory bookHistory
            AND bookHistory.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Long userId);
}
