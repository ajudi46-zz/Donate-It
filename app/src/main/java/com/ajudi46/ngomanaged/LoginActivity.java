package com.ajudi46.ngomanaged;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private EditText memail,mpassword;
    private Button sign_in,sign_up,forget ;
    private FirebaseAuth.AuthStateListener mauthstatelistner;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);



        memail = (EditText)findViewById(R.id.email_id);
        mpassword = (EditText)findViewById(R.id.passwd);
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (Button) findViewById(R.id.sign_up) ;
        forget = findViewById(R.id.Forget_Pass);
        progressBar = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        mAuth = FirebaseAuth.getInstance();
        mauthstatelistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this,Home.class));
                }

            }
        };

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsignup();
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startsignin();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memail.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"Enter Email-Id in Email field and Try Again",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(memail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,"Password Reset, Check Email",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mauthstatelistner);
    }

    public void startsignup(){
        startActivity(new Intent(LoginActivity.this,sign_up.class));
    }
    public void startsignin(){
        final String email = memail.getText().toString();
        final String password = mpassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Enter Valid Entries",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Sign In Problem",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

            });
        }

    }


}
