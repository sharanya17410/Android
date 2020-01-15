package com.mobileChallenge.ToDolApp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    ArrayList<CustomCard> taskList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

         taskList = new ArrayList<>();     //uploads lisr
        databaseReference= FirebaseDatabase.getInstance().getReference("Tasks");
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                    Task task=postSnapShot.getValue(Task.class);

                    CustomCard card =new CustomCard(task.getTaskName(),task.getSubTask(),task.getPhoto());
                    taskList.add(card);
                    adapter = new RecyclerAdaper(getApplicationContext(),taskList);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                    //adapter.setOnItemClickListener(ActivityList.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });





        //taskList.add(card);






        //adapter = new RecyclerAdaper(getApplicationContext(),taskList);


     /*   recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/


        Button button= findViewById(R.id.createTask);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Hellp", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ActivityList.this, CreateTaskActivity.class);
                //myIntent.putExtra("key", value);
                startActivity(myIntent);
            }
        });




    }

}
