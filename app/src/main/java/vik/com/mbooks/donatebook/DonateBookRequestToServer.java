package vik.com.mbooks.donatebook;

import android.net.Uri;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Part;
import vik.com.mbooks.donatebook.request.DonateBookRequest;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class DonateBookRequestToServer {
    IAppDataAPIs mClient;
    DonateBookRequest donateBookRequest;
    String authToken;

    public DonateBookRequestToServer(DonateBookRequest donateBookRequest, String authToken) {
        mClient = AppDataClient.getClient();
        this.donateBookRequest = donateBookRequest;
        this.authToken = authToken;
    }

    public Observable<Response> callService() {
        RequestBody requestBookTitle = RequestBody.create(okhttp3.MultipartBody.FORM, donateBookRequest.getBookTitle());
        RequestBody requestBookAuthor = RequestBody.create(okhttp3.MultipartBody.FORM, donateBookRequest.getBookAuther());
        RequestBody requestBookQuantity = RequestBody.create(okhttp3.MultipartBody.FORM, donateBookRequest.getBookQuantity());

        MultipartBody.Part bookImage = null;
        File file = new File(donateBookRequest.getBookImagePath().getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        bookImage = MultipartBody.Part.createFormData("book-image", file.getName(), requestFile);

        Observable<Response> observable = mClient.donateBook(
                authToken,
                bookImage,
                requestBookTitle,
                requestBookAuthor,
                requestBookQuantity);
        return observable;
    }
}
