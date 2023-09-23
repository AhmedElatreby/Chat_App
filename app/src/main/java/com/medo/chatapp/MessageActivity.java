package com.medo.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edtMessageInput;
    private TextView txtCattingWith;
    private ProgressBar progressBar;
    private ImageView imgToolbar, imgSend;
    private ArrayList<Message> messages;
    String userNameOfRoommate, emailOfRoommate, chatRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userNameOfRoommate = getIntent().getStringExtra("username_of_roommate");
        emailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        recyclerView = findViewById(R.id.recyclerMessages);
        edtMessageInput = findViewById(R.id.edtText);
        txtCattingWith = findViewById(R.id.txtChattingWith);
        progressBar = findViewById(R.id.progressMessages);
        imgToolbar = findViewById(R.id.img_toolbar);
        imgSend = findViewById(R.id.imgSendMessage);
        txtCattingWith.setText(userNameOfRoommate);

        messages = new ArrayList<>();
        setUpChatRoom();

    }
    private void setUpChatRoom() {
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUsername = snapshot.getValue(User.class).getUsername();
                if (userNameOfRoommate.compareTo(myUsername) > 0) {
                    chatRoomId = myUsername + userNameOfRoommate;
                } else if (userNameOfRoommate.compareTo(myUsername) == 0) {
                    chatRoomId = myUsername + userNameOfRoommate;
                } else {
                    chatRoomId = userNameOfRoommate = myUsername;
                }
                attachMessageListener(chatRoomId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatRoomId) {
        FirebaseDatabase.getInstance().getReference("messages/"+ chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                recyclerView.scrollToPosition(messages.size() -1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}