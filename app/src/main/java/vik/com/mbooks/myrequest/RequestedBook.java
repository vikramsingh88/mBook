package vik.com.mbooks.myrequest;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class RequestedBook {
    private String userId;
    private String bookId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
