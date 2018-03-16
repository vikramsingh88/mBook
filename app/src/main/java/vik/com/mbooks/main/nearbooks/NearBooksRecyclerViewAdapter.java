package vik.com.mbooks.main.nearbooks;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vik.com.mbooks.R;
import vik.com.mbooks.login.response.User;
import vik.com.mbooks.main.response.Book;
import vik.com.mbooks.utils.Constants;
import vik.com.mbooks.utils.RoundedTransformation;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class NearBooksRecyclerViewAdapter extends RecyclerView.Adapter<NearBooksRecyclerViewAdapter.MyViewHolder> {

    private List<Book> mBooks, mFilterList;
    private Context mContext;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book item);
        void onBookImageClick(String bookTitle, String bookImagePath);
        void onUserDetails(String userId, TextView donor);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPublisher;
        TextView txtPublishedDate;
        TextView txtQuantity;
        TextView txtBookTitle;
        ImageView imgBook;
        Button btnInterested;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            txtPublisher = view.findViewById(R.id.txt_published_by);
            txtPublishedDate =  view.findViewById(R.id.txt_date);
            txtQuantity =  view.findViewById(R.id.txt_qty);
            txtBookTitle = view.findViewById(R.id.txt_book_title);
            imgBook =  view.findViewById(R.id.img_book);
            btnInterested =  view.findViewById(R.id.btn_interested);
            cardView = view.findViewById(R.id.card_view);
        }

        public void bind(final Book item, final NearBooksRecyclerViewAdapter.OnItemClickListener listener) {
            btnInterested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBookImageClick(item.getBookTitle(), item.getBookImagePath());
                }
            });
        }

    }

    private void setBookImage(String path, ImageView imageView) {
        path = path.replace("localhost", Constants.IP);
        Picasso.with(mContext)
                .load(path)
                .into(imageView);
    }


    public NearBooksRecyclerViewAdapter(List<Book> books, Context context, OnItemClickListener listener) {
        this.mBooks = books;
        this.mFilterList = new ArrayList<>();
        this.mFilterList.addAll(this.mBooks);
        mContext = context;
        this.listener = listener;
    }

    @Override
    public NearBooksRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row_item, parent, false);
        return new NearBooksRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NearBooksRecyclerViewAdapter.MyViewHolder holder, int position) {
        Book book = mFilterList.get(position);
        //holder.txtPublisher.setText(book.getBookAuther());
        holder.txtPublishedDate.setText(book.getBookAuther());
        holder.txtQuantity.setText(book.getBookQuantity());
        holder.txtBookTitle.setText(book.getBookTitle());
        setBookImage(book.getBookImagePath(), holder.imgBook);
        listener.onUserDetails(book.getUserId(), holder.txtPublisher);

        holder.bind(mFilterList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (null != mFilterList ? mFilterList.size() : 0);
    }

    //Searching item
    public void filter(final String text) {
        // Searching could be complex..so we will dispatch it to a different thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Clear the filter list
                mFilterList.clear();
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    mFilterList.addAll(mBooks);

                } else {
                    // Iterate in the original List and add it to filter list
                    for (Book item : mBooks) {
                        if (item.getBookTitle().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            mFilterList.add(item);
                        }
                    }
                }
                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }
}