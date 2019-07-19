package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

   private TextView txtTitle,txtExplanation,usernamesurname;

   DatabaseReference reference;

   public void setTODo(final String userId){

       reference = FirebaseDatabase.getInstance().getReference();

       reference.child("Title").addChildEventListener(new ChildEventListener(){
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            User user = dataSnapshot.getValue(User.class);
            usernamesurname.setText(user.getUserEmail());
           }
           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) { }
       } );
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

      txtTitle = findViewById(R.id.title);
      txtExplanation = findViewById(R.id.explanation);
      usernamesurname = findViewById(R.id.userNameSurname);

        Intent received = getIntent();
        Bundle bReceived = received.getExtras();
        if (bReceived!= null){
            String Title = (String)bReceived.get("Title");
            String Description = (String)bReceived.get("Description");
            String userEmail = (String)bReceived.get("UserEmail");
            txtTitle.setText(Title);
            txtExplanation.setText(Description);
            setTODo(userEmail);
        }
    }
}
