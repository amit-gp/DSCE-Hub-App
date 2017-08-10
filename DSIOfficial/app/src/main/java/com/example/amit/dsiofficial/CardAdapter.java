package com.example.amit.dsiofficial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amit on 10-08-2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private Context context;
    private List<MessageNotification> messageNotifications;

    public CardAdapter(List<MessageNotification> messageNotifications, Context context){
        super();
        this.context = context;
        this.messageNotifications = messageNotifications;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MessageNotification notification = messageNotifications.get(position);
        //String notificationMessageHint = notification.getNotificationTitle().substring(0, 25) + "...";
        holder.messageTitleTextView.setText(notification.getNotificationTitle());
        holder.messageBodyTextView.setText(notification.getGetNotificationBody());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return messageNotifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTitleTextView;
        private TextView messageBodyTextView;

        public ViewHolder(View view){
            super(view);
            messageTitleTextView = (TextView) view.findViewById(R.id.messageTitleTextView);
            messageBodyTextView = (TextView) view.findViewById(R.id.messageBodyTextView);
        }
    }
}
