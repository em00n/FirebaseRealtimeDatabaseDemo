package com.emon.demofirebasertdb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
Button addBTN,updateBTN;
RecyclerView recyclerView;

//firebase

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Post> option;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;


    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    String muid;
    String preUid;

    Post selectedpost;
    String selectedkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //recyclerview
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showData();
    }



    @Override
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    //show data
    private void showData() {
        option= new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(databaseReference,Post.class)
                        .build();
        adapter= new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(option) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull final Post model) {
                        holder.nameTV.setText("Name : "+model.getName());
                        holder.emailTV.setText("Email : "+model.getEmail());
                        holder.setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public void onLongClick(View view, int position) {
                                preUid=model.getUid();
                                selectedkey=getSnapshots().getSnapshot(position).getKey();
                               // updateDelet(position);
                                String em=model.getEmail();
                                String na=model.getName();
                                Intent intent=new Intent(MainActivity.this,UpDeActivity.class);
                                intent.putExtra("selectedkey",selectedkey);
                                intent.putExtra("em",em);
                                intent.putExtra("na",na);
                                intent.putExtra("uid",preUid);
                                startActivity(intent);
                            }

//                            @Override
//                            public void onClick(View view, int position) {
//
//                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_post,parent,false);
                        return new MyRecyclerViewHolder(itemView);
                    }
                };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    //update and delete
//    public void updateDelet(int view) {
//
//        AlertDialog.Builder alert=new AlertDialog.Builder(this);
//        alert.setTitle("Update or Delete");
//        alert.setCancelable(false);
//
//        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
//                final Dialog dialogg = new Dialog(MainActivity.this);
//                dialogg.setContentView(R.layout.activity_update);
//                dialogg.setTitle("Add Data to Database");
//                final EditText name = (EditText) dialogg.findViewById(R.id.nameET);
//                final EditText email = (EditText) dialogg.findViewById(R.id.emailET);
//                final Button uptate=(Button)dialogg.findViewById(R.id.updateBTN);
//
//
//
//                uptate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        databaseReference
//                                .child(selectedkey)
//                                .setValue(new Post("Name: "+name.getText().toString(),"Email: "+email.getText().toString(),muid))
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        dialogg.dismiss();
//                    }
//                });
//                dialogg.show();
//
//            }
//        });
//        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
//                databaseReference
//                        .child(selectedkey)
//                        .removeValue()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
//        final AlertDialog alertDialog=alert.create();
//        alertDialog.show();
//
//    }

}
