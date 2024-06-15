package io.rachidassouani.booksocialnetworkapi.history;

public class BorrowedBookResponse {
    private Long id;
    private String title;
    private String authorName;
    private String isbn;
    private double rate;
    private boolean returned;
    private boolean returnApproved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturnApproved() {
        return returnApproved;
    }

    public void setReturnApproved(boolean returnApproved) {
        this.returnApproved = returnApproved;
    }
}
