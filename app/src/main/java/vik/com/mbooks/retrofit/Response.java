package vik.com.mbooks.retrofit;

/**
 * Created by M1032130 on 2/22/2018.
 */

public class Response {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
