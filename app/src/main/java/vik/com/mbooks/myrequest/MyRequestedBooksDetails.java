package vik.com.mbooks.myrequest;

import java.util.List;

import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class MyRequestedBooksDetails extends Response{
    List<RequestedBook> books;

    public List<RequestedBook> getRequestedBookList() {
        return books;
    }

    public void setRequestedBookList(List<RequestedBook> requestedBookList) {
        this.books = requestedBookList;
    }
}
