package com.example.b07project.adminPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.LoginPage;
import com.example.b07project.R;

import java.util.ArrayList;

import com.example.b07project.adminPages.ComplaintAdapter.ComplaintAdapter;
import com.example.b07project.studentPages.Complaint.Complaint;
import com.example.b07project.studentPages.studentHomePage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminComplaintPage extends AppCompatActivity{
    Button buttonBack;

    ArrayList<Complaint> complaints= new ArrayList<>();
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint_page);

        RecyclerView recyclerView = findViewById(R.id.complaintRecycle);
        buttonBack = findViewById(R.id.button);
        setComplaints();




    }

    private void setComplaints(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("complaints");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int complaintCount = 0;


                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    complaintCount++;
                    String complaintNum = Integer.toString(complaintCount);
                    String description = userSnapshot.child("description").getValue(String.class);
                    String title = userSnapshot.child("title").getValue(String.class);
                    String userId = userSnapshot.child("userId").getValue(String.class);
                    String userName = userSnapshot.child("userName").getValue(String.class);
                    complaints.add(new Complaint(("Complaint " + complaintNum), userId,
                            userName, title, description));

                }

                // userIds now contains all the user IDs from the "users" node
                // You can store, process, or use these IDs as needed
                RecyclerView recyclerView = findViewById(R.id.complaintRecycle);
                ComplaintAdapter adapter = new ComplaintAdapter(adminComplaintPage.this, complaints);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(adminComplaintPage.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Intent intent = new Intent(adminComplaintPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

    }


}