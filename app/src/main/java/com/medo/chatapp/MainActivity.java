package com.medo.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
          isSignUp = false;
          edtUserName.setVisibility(View.GONE);
          btnSubmit.setText("Log in");
          txtLoginInfo.setText("Don't have an account? Sign up");
        } else {
          isSignUp = true;
          edtUserName.setVisibility(View.VISIBLE);
          btnSubmit.setText("Sign up");
          txtLoginInfo.setText("Already have an account? Log in");
        }
      }
    });


  }

  private void handleLogin() {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    });

  }

  private void handleSignUp() {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          Toast.makeText(MainActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
      }
    });

  }

}