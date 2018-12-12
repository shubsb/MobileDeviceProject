package net.uoit.csci4100.mobiledeviceproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FindUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecylerView;

    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mRecylerView = (RecyclerView)findViewById(R.id.finduser_Recycler);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mToolbar = (Toolbar)findViewById(R.id.findUser_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Users> opt = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(mRef, Users.class)
                .build();

        FirebaseRecyclerAdapter<Users, UserListAdapter> adapters = new FirebaseRecyclerAdapter<Users, UserListAdapter>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull UserListAdapter holder, int position, @NonNull Users model) {

                holder.userName.setText(model.getName());
                holder.userEmail.setText(model.getEmail());
                Picasso.get().load(model.getImage()).placeholder(R.drawable.avatar1).into(holder.userProfileImage);
            }

            @NonNull
            @Override
            public UserListAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userdisplay_layout, viewGroup,false );
                UserListAdapter userListAdapter = new UserListAdapter(view);

                return userListAdapter;
            }
        };

        mRecylerView.setAdapter(adapters);
        adapters.startListening();



    }
}
