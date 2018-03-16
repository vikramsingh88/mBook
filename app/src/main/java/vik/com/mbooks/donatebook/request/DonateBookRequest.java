package vik.com.mbooks.donatebook.request;

import android.net.Uri;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class DonateBookRequest {
    private String bookTitle;
    private String bookAuther;
    private String bookQuantity;
    private Uri bookImagePath;

    public DonateBookRequest(String bookTitle, String bookAuther, String bookQuantity, Uri bookImagePath) {
        this.bookTitle = bookTitle;
        this.bookAuther = bookAuther;
        this.bookQuantity = bookQuantity;
        this.bookImagePath = bookImagePath;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Uri getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(Uri bookImagePath) {
        this.bookImagePath = bookImagePath;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuther() {
        return bookAuther;
    }

    public void setBookAuther(String bookAuther) {
        this.bookAuther = bookAuther;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
