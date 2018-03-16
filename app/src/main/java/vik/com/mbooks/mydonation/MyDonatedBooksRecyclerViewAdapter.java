package vik.com.mbooks.mydonation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vik.com.mbooks.R;
import vik.com.mbooks.myrequest.RequestedBook;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class MyDonatedBooksRecyclerViewAdapter extends RecyclerView.Adapter<MyDonatedBooksRecyclerViewAdapter.MyViewHolder> {

    private List<RequestedBook> mRequestedBooks, mFilterList;
    private Context mContext;
    private final OnItemListener listener;

    public interface OnItemListener {
        void onRequestedBook(String bookId,String requestedUser, MyDonatedBooksRecyclerViewAdapter.MyViewHolder holder);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPublisher;
        TextView txtRequester;
        TextView txtPublishedDate;
        TextView txtQuantity;
        TextView txtBookTitle;
        ImageView imgBook;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            txtPublisher = view.findViewById(R.id.txt_published_by);
            txtRequester= view.findViewById(R.id.txt_lbl_published_by);
            txtPublishedDate =  view.findViewById(R.id.txt_date);
            txtQuantity =  view.findViewById(R.id.txt_qty);
            txtBookTitle = view.findViewById(R.id.txt_book_title);
            imgBook =  view.findViewById(R.id.img_book);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    public MyDonatedBooksRecyclerViewAdapter(List<RequestedBook> books, Context context, OnItemListener listener) {
        this.mRequestedBooks = books;
        this.mFilterList = new ArrayList<>();
        this.mFilterList.addAll(this.mRequestedBooks);
        mContext = context;
        this.listener = listener;
    }

    @Override
    public MyDonatedBooksRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_book_item_row, parent, false);
        return new MyDonatedBooksRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyDonatedBooksRecyclerViewAdapter.MyViewHolder holder, int position) {
        RequestedBook requestedBook = mFilterList.get(position);
        listener.onRequestedBook(requestedBook.getBookId(), requestedBook.getUserId(), holder);
    }

    @Override
    public int getItemCount() {
        return (null != mFilterList ? mFilterList.size() : 0);
    }

}