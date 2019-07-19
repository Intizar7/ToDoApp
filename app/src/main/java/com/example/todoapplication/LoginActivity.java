package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText userName,passWord;
    private Button btnreg,btnlog;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pb = new ProgressBar(this);

        auth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.UserName);
        passWord = findViewById(R.id.Password);
        btnreg = findViewById(R.id.btnRegister);
        btnlog = findViewById(R.id.btnLogin);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final String email= userName.getText().toString();
               final String pass = passWord.getText().toString();

               try{
                   if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                       Toast.makeText(getApplicationContext(), "Please Enter the your E-mail or Password !", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   else
                       {
                       auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {

                               if (task.isSuccessful()){
                                   user = auth.getInstance().getCurrentUser();
                                   if (user!= null){
                                       startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                       finish();
                                   }else{
                                       Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                                       finish();
                                   }
                               }
                           }
                       });
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

            }
        });
       btnreg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent iReg = new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(iReg);
               finish();
           }
       });
    }
}
