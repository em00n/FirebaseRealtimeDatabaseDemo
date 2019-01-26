package com.emon.demofirebasertdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpDeActivity extends AppCompatActivity {
EditText emailEt,nameEt;
Button updateBtn,deletBtn;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    String muid;
    String preUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_de);
        mAuth=FirebaseAuth.getInstance();

        emailEt=findViewById(R.id.emailET);
        nameEt=findViewById(R.id.nameET);
        updateBtn=findViewById(R.id.updateBTN);
        deletBtn=findViewById(R.id.deletBTN);
        final String selectedkey=getIntent().getStringExtra("selectedkey");
        String email=getIntent().getStringExtra("em");
        String name=getIntent().getStringExtra("na");
         preUid=getIntent().getStringExtra("uid");


         emailEt.setText(email);
         nameEt.setText(name);

//        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
//        mCurrentUser = mAuth.getCurrentUser();
//        muid=mCurrentUser.getUid();
        muid=mAuth.getCurrentUser().getUid();

        //firebase
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EMON");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (mAuth.getCurrentUser().getUid().equals(preUid)){

            updateBtn.setVisibility(View.VISIBLE);
            deletBtn.setVisibility(View.VISIBLE);
        }else {
            updateBtn.setVisibility(View.INVISIBLE);
            deletBtn.setVisibility(View.INVISIBLE);
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference
                        .child(selectedkey)
                        .setValue(new Post(nameEt.getText().toString(), emailEt.getText().toString(), muid))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                clear();
                                Intent intent=new Intent(UpDeActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(UpDeActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpDeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference
                        .child(selectedkey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                clear();
                                Intent intent=new Intent(UpDeActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(UpDeActivity.this, "delete", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpDeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
    public void clear(){
        emailEt.setText("");
        nameEt.setText("");
    }
}
