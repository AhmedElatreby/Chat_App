package com.medo.chatapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private ArrayList<Message> messages;
    private String senderImg, receiverImg;
    private Context context;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MessageHolder extends RecyclerView.ViewHolder{

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
