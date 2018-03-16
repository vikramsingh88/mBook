package vik.com.mbooks.donatebook;

import android.content.Context;

import io.reactivex.Observable;
import vik.com.mbooks.donatebook.request.DonateBookRequest;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class DonateBookViewModel {
    IDonateBookModel donateBookModel;
    Context mContext;

    public DonateBookViewModel(Context context) {
        donateBookModel = new DonateBookModel();
        mContext = context;
    }

    public Observable<Response> donateBook(DonateBookRequest donateBookRequest, String authToken) {
        return donateBookModel.donateBook(donateBookRequest, authToken);
    }
}
