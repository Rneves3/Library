package crud.vaadin;

import java.io.Serializable;

import crud.backend.Book;

public class BookModifiedEvent implements Serializable {

    private final Book book;

    public BookModifiedEvent(Book b) {
        this.book = b;
    }

    public Book getBook() {
        return book;
    }
    
}
