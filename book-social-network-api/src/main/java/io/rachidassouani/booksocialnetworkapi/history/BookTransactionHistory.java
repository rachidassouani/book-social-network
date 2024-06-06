package io.rachidassouani.booksocialnetworkapi.history;

import io.rachidassouani.booksocialnetworkapi.book.Book;
import io.rachidassouani.booksocialnetworkapi.common.BaseEntity;
import io.rachidassouani.booksocialnetworkapi.user.AppUser;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_transaction_history")
public class BookTransactionHistory extends BaseEntity {
    private boolean returned;
    private boolean returnedApproved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
