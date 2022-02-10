package com.example.myroomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroomdatabase.database.AppDatabase;
import com.example.myroomdatabase.database.entity.User;
import com.example.myroomdatabase.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private EditText firstName;
    private EditText lastName;
    private MaterialButton btnSubmit, btnSearch;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firstName = binding.etFirstName;
        lastName = binding.etLastName;
        btnSubmit = binding.btnSubmit;
        btnSearch = binding.btnSearch;
        recyclerView = binding.recyclerView;

        AppDatabase db = AppDatabase.getInstance(binding.getRoot().getContext());
        UserAdapter userAdapter = new UserAdapter(db.userDao().getAll(), db, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(userAdapter);
        btnSubmit.setOnClickListener(view -> {
            User user = new User();
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            db.userDao().insert(user);
            firstName.setText("");
            lastName.setText("");
            userAdapter.addUser(db);
        });

        btnSearch.setOnClickListener(view -> startActivity(new Intent(this, SearchActivity.class)));
    }
}