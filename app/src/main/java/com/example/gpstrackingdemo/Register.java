package com.example.gpstrackingdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText signupEmail, signupUsername, signupPassword,signupconformPassword;
    TextView alreadyhaveAccount;
    Button registerButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupEmail = findViewById(R.id.inputEmail);
        signupUsername = findViewById(R.id.inputUsername);
        signupPassword = findViewById(R.id.inputPassword);
        signupconformPassword = findViewById(R.id.inputConformPassword);
        registerButton = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateSignUp()){

                }else{
                    checkUser();
                }

            }
        });


    }

    private boolean validateSignUp() {
        String username = signupUsername.getText().toString();
        String password = signupPassword.getText().toString();
        String email = signupEmail.getText().toString();
        String conformpassword = signupconformPassword.getText().toString();
        if (username.isEmpty()) {
            signupUsername.setError("Enter Username");
        }else if (!email.matches(emailPattern)) {
            signupEmail.setError("Enter Proper Pattern of Email");
        } else if (password.isEmpty()) {
            signupPassword.setError("Enter Password");
        } else if (!password.equals(conformpassword)) {
            signupconformPassword.setError("Password not match Both Field");
        } else {
            return true;
        }
        return false;
    }

    private void checkUser() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String email = signupEmail.getText().toString();
        String username = signupUsername.getText().toString();
        String password = signupPassword.getText().toString();
        String conformPassword = signupconformPassword.getText().toString();

        HelperClass helperClass = new HelperClass(email, username, password,conformPassword);
        reference.child(username).setValue(helperClass);

        Toast.makeText(Register.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}