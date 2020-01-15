package com.mobileChallenge.ToDolApp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHandler {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("todolist-d509c");

}
