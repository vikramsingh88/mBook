package vik.com.mbooks.main.response;

import java.util.List;

import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class BooksResponse extends Response {
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
