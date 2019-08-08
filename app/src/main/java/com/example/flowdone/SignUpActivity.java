package com.example.flowdone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText newPassword;
    private EditText checkPassword;
    private TextView loginActivity;
    private Button createAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginActivity = (TextView)findViewById(R.id.loginActivityText);
        email = (EditText) findViewById(R.id.emailText);
        newPassword = (EditText)findViewById(R.id.newPassword);
        checkPassword = (EditText)findViewById(R.id.checkPassword);
        createAccount = (Button)findViewById(R.id.createAccount);

        progressDialog = new ProgressDialog(this);



        mAuth = FirebaseAuth.getInstance();

        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_user_details()) {
                    String passwordString = newPassword.getText().toString().trim();
                    String emailString = email.getText().toString().trim();

                    progressDialog.setMessage("Registering ...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                            .addOnCompleteListener( SignUpActivity.this, new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                                        finish();
                                        return;
                                    }else{
                                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                        Toast.makeText(SignUpActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        message.hide();
                                        return;
                                    }
                                }
                            });
                }
            }
        });
    }



    //checks if the users email is valid, also check  if the passwords match, and if they meet the 8 char requirement
    private boolean validate_user_details() {
        if (!isEmailValid(email)) {
            Toast.makeText(getApplicationContext(), "The email you entered is not valid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPassword.getText().toString().equals(checkPassword.getText().toString())) {
            if (newPassword.getText().length() >= 8) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Your password must have 8 or more characters", Toast.LENGTH_LONG).show();
                newPassword.setText("");
                checkPassword.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "The passwords do not match, please re-enter!", Toast.LENGTH_LONG).show();
            newPassword.setText("");
            checkPassword.setText("");
        }
        return false;
    }

    //checks if email is valid
    private boolean isEmailValid(EditText email) {
        String expression = "[\\w\\.-_]+@\\w+\\.[a-zA-Z]{2,4}";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(email.getText().toString());
        if(matcher.matches()){
            return true;
        }
        return false;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
        }
    }
}
