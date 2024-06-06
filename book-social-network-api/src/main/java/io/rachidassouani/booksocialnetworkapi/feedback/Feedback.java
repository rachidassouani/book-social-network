package io.rachidassouani.booksocialnetworkapi.feedback;

import io.rachidassouani.booksocialnetworkapi.book.Book;
import io.rachidassouani.booksocialnetworkapi.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {
    private String comment;
    private Double note;

    //@ManyToOne
    //private AppUser user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }
}
