package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase ;

    private Toolbar toolbar;
    private DatabaseReference databaseReference;
    private DatabaseReference dtRef;
    private FirebaseAuth auth;
    private String userName,Pass;
    private EditText Name,Surname,Email,pass;
    private Button btnRegister,btnaddPhoto;
    private  ProgressBar progressBar;
    private StorageReference mStorageRef;
    private static final int GALLERY_INTENT = 2;
    private ImageView ivProfail;
    private String CurrentImgPath="-";
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dtRef= firebaseDatabase.getReference();
        final DatabaseReference memberRef = dtRef.child("Registrations").child("Users");
        mStorageRef= FirebaseStorage.getInstance().getReference();

        //get Instance
        Name = findViewById(R.id.name);
        Surname = findViewById(R.id.surname);
        Email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btnRegister = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressbar_circle);
        btnaddPhoto = findViewById(R.id.btnTakePhoto);
        ivProfail = findViewById(R.id.user_image);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(Pass)){
                    Toast.makeText(getApplicationContext(), "Please Enter the your E-mail or Password !", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    auth.createUserWithEmailAndPassword(userName,Pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                final User user = new User();
                                user.setUserName(Name.getText().toString());
                                user.setUserName(Surname.getText().toString());
                                user.setUserEmail(Email.getText().toString());
                                user.setUserPassword(pass.getText().toString());

                                btnaddPhoto.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Intent.ACTION_PICK);
                                        i.setType("image/*");
                                        startActivityForResult(i,GALLERY_INTENT);
                                    }
                                });
                                Toast.makeText(RegisterActivity.this,"Registered successfully !",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                Intent iMain = new Intent(RegisterActivity.this, ProfileActivity.class);
                startActivity(iMain);
                finish();
            }
        });
    }
}
