package io.rachidassouani.booksocialnetworkapi.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest bookRequest) {

        Book book = new Book();
        book.setId(bookRequest.id());
        book.setTitle(bookRequest.title());
        book.setSynopsis(bookRequest.synopsis());
        book.setIsbn(bookRequest.isbn());
        book.setAuthorName(bookRequest.authorName());
        book.setArchived(false);
        book.setShareable(bookRequest.shareable());

        return book;
    }


    public BookResponse toBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setAuthorName(book.getAuthorName());
        response.setIsbn(book.getIsbn());
        response.setSynopsis(book.getSynopsis());
        response.setTitle(book.getTitle());
        response.setShareable(book.isShareable());
        response.setArchived(book.isArchived());
        response.setOwner(book.getOwner().getFullName());
        response.setRate(book.calculateRate());
        // TODO: 6/8/2024 implement cover
        //response.setCover();
        return response;
    }
}
