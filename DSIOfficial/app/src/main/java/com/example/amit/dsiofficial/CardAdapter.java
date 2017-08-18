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

        holder.hasAttachment = messageNotifications.get(position).getHasAttachment();
        if(messageNotifications.get(position).getHasAttachment().equals("true")){
            holder.attachmentName = messageNotifications.get(position).getAttachmentName();
            holder.attachmentType = messageNotifications.get(position).getAttachmentType();
        }

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
        private String hasAttachment, attachmentName, attachmentType;

        public ViewHolder(View view){
            super(view);
            messageTitleTextView = (TextView) view.findViewById(R.id.messageTitleTextView);
            messageBodyTextView = (TextView) view.findViewById(R.id.messageBodyTextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("ALERT !!", "View " + messageTitleTextView.getText() +" Clicked !!");
                    Intent intent = new Intent(context, NotificationInfoActivity.class);
                    intent.putExtra("hasAttachment", hasAttachment);
                    intent.putExtra("attachmentName", attachmentName);
                    intent.putExtra("attachmentType", attachmentType);
                    context.startActivity(intent);
                }
            });
        }
    }
}
