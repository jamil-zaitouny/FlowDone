package com.example.flowdone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.AuthProvider;

public class MainActivity extends AppCompatActivity {
    private Button mSignUpButton;
    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mEmailAuth;
    private ProgressDialog progressDialog;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        //initializes firebbase app
        mEmailAuth = FirebaseAuth.getInstance();


        //Connects variables to the activity
        mSignUpButton = (Button)findViewById(R.id.signUp);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mEmail = (EditText) findViewById(R.id.emailText);
        mPassword = (EditText) findViewById(R.id.passwordText);
        progressDialog = new ProgressDialog(this);

        //Tells the user that sign up will be made available at a later date
        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sign up will become available at a later date!", Toast.LENGTH_SHORT).show();
            }
        });


        mLoginButton .setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //checking if the user entered his email and password
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        mEmailAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            //start the new activity
                            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        }
                    }
                }

        );

    }

//    public void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = mEmailAuth.getCurrentUser();
//    }
 }

