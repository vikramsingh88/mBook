package vik.com.mbooks.main.response;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class Book {
    private String userId;
    private String _id;
    private String bookTitle;
    private String bookAuther;
    private String bookQuantity;
    private String pincode;
    private String bookImagePath;

    public String getBookId() {
        return _id;
    }

    public void setBookId(String bookId) {
        this._id = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookTitle() {
        return bookTitle;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(String bookImagePath) {
        this.bookImagePath = bookImagePath;
    }
}
