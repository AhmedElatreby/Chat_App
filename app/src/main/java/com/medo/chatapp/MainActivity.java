package com.medo.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  private EditText edtUserName, edtEmail, edtPassword;
  private Button btnSubmit;
  private TextView txtLoginInfo;

  private boolean isSignUp = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    edtUserName = findViewById(R.id.edtUserName);
    edtEmail = findViewById(R.id.edtEmail);
    edtPassword = findViewById(R.id.edtPassword);

    btnSubmit = findViewById(R.id.btnSignUp);

    txtLoginInfo = findViewById(R.id.txtLoginInfo);
    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
      startActivity(new Intent(MainActivity.this, FriendsActivity.class));
      finish();
    }

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isSignUp) {
          handleSignUp();
        } else {
          handleLogin();
        }
      }
    });

    txtLoginInfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
          if (isSignUp && edtUserName.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
          }
        }

        if (isSignUp) {
          handleSignUp();
//          edtUserName.setVisibility(View.GONE);
//          btnSubmit.setText(R.string.logIn);
//          txtLoginInfo.setText(R.string.donotHaveAccount);
        } else {
          handleLogin();
//          isSignUp = true;
//          edtUserName.setVisibility(View.VISIBLE);
//          btnSubmit.setText(R.string.signUp);
//          txtLoginInfo.setText(R.string.alreadHaveAccount);
        }
      }
    });


  }

  private void handleLogin() {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          startActivity(new Intent(MainActivity.this, FriendsActivity.class));
          Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    });

  }

  private void handleSignUp() {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                          .setValue(new User(edtUserName.getText().toString(), edtEmail.getText().toString()
                          ,""));

          startActivity(new Intent(MainActivity.this, FriendsActivity.class));
          Toast.makeText(MainActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
      }
    });

  }

}