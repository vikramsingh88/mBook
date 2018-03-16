package vik.com.mbooks.donatebook;

import android.content.Context;

import io.reactivex.Observable;
import vik.com.mbooks.donatebook.request.DonateBookRequest;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/23/2018.
 */

public interface IDonateBookModel {
    Observable<Response> donateBook(DonateBookRequest donateBookRequest, String authToken);
}
