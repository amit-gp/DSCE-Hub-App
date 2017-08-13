package com.example.amit.dsiofficial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by Amit Kumar on 13-08-2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
{
    private Context context;
    private List<BookNotification> bookNotifications;

    public BookAdapter(List<BookNotification> messageNotifications, Context context){
        super();
        this.context = context;
        this.bookNotifications = messageNotifications;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {

        BookNotification notification = bookNotifications.get(position);
        //String notificationMessageHint = notification.getNotificationTitle().substring(0, 25) + "...";
        holder.bookTitleTextView.setText(notification.getTitle());
        holder.bookEditionTextView.setText(notification.getEdition());
        holder.bookPriceTextView.setText(notification.getPrice());
        holder.bookAuthorTextView.setText(notification.getAuthor());
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);
        return new BookAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return bookNotifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bookTitleTextView;
        private TextView bookEditionTextView;
        private TextView bookAuthorTextView;
        private TextView bookPriceTextView;

        public ViewHolder(View view){
            super(view);
            bookTitleTextView = (TextView) view.findViewById(R.id.bookTitleTextView);
            bookAuthorTextView = (TextView) view.findViewById(R.id.bookAuthorTextView);
            bookPriceTextView = (TextView) view.findViewById(R.id.bookPriceTextView);
            bookEditionTextView = (TextView) view.findViewById(R.id.bookEditionTextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Log.i("ALERT !!", "View " + messageTitleTextView.getText() +" Clicked !!");
                   // Intent intent = new Intent();

                }
            });
        }
    }
}
