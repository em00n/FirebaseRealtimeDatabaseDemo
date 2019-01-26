package com.emon.demofirebasertdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddData_Activity extends AppCompatActivity {
    EditText nameET,emailET;
    Button addBTN,showBTN;
    String useruid;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);

        nameET=findViewById(R.id.nameET);
        emailET=findViewById(R.id.emailET);
        addBTN=findViewById(R.id.addBTN);
        showBTN=findViewById(R.id.showBTN);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EMON");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        //mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
        useruid=mAuth.getCurrentUser().getUid();

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                startActivity(new Intent(AddData_Activity.this,MainActivity.class));
                Toast.makeText(AddData_Activity.this, "add", Toast.LENGTH_SHORT).show();
                clear();
                finish();

            }
        });

        showBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddData_Activity.this,MainActivity.class));
                finish();
            }
        });

    }


    //add data to database
    private void addData() {

        String name=nameET.getText().toString();
        String email=emailET.getText().toString();

        Post post=new Post(name,email,useruid);
        databaseReference.push()  //use this method to creat unik id
                .setValue(post);
       // adapter.notifyDataSetChanged();
    }
    public void clear(){
        nameET.setText("");
        emailET.setText("");
    }
}
