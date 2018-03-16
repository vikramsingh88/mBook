package vik.com.mbooks.retrofit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import vik.com.mbooks.bookdetails.BookDetails;
import vik.com.mbooks.login.request.LoginRequest;
import vik.com.mbooks.login.response.LoginResponse;
import vik.com.mbooks.main.response.BooksResponse;
import vik.com.mbooks.myrequest.MyRequestedBooksDetails;
import vik.com.mbooks.requestbook.BookRequest;
import vik.com.mbooks.userdetails.UserDetails;
import vik.com.mbooks.utils.Constants;

/**
 * Created by M1032130 on 1/8/2018.
 */

public interface IAppDataAPIs {
    String CONTENT_TYPE = "Content-Type";
    String JSON_TYPE = "application/json";
    String BASE_URL = "http://"+ Constants.IP +":3000/";

    @POST("users/login-user")
    Observable<LoginResponse> login(@Body LoginRequest body);

    @GET("books/get-book")
    Observable<BooksResponse> getBooksByLoginUserPincode(@Header("Authorization") String token);

    @GET("books/{bookId}")
    Observable<BookDetails> getBookDetailsByBookId(@Path("bookId") String bookId, @Header("Authorization") String token);

    @Multipart
    @POST("books/add-book")
    Observable<Response> donateBook(@Header("Authorization") String token,
                                    @Part MultipartBody.Part bookImage,
                                    @Part("bookTitle") RequestBody bookTitle,
                                    @Part("bookAuther") RequestBody bookAuthor,
                                    @Part("bookQuantity") RequestBody bookQuantity);

    @GET("users/get-user-byid/{userId}")
    Observable<UserDetails> getUserDetailsByUserId(@Path("userId") String userId, @Header("Authorization") String token);

    @POST("books/request-book")
    Observable<Response> requestBookByBookId(@Body BookRequest body, @Header("Authorization") String token);

    @GET("books/my-request")
    Observable<MyRequestedBooksDetails> getMyRequestedBooks(@Header("Authorization") String token);

    @GET("books/notifications")
    Observable<MyRequestedBooksDetails> getDonatedBooks(@Header("Authorization") String token);
}
