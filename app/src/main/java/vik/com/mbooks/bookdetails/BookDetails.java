package vik.com.mbooks.bookdetails;

import vik.com.mbooks.login.response.User;
import vik.com.mbooks.main.response.Book;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class BookDetails extends Response {
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
