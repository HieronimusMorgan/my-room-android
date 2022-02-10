package com.example.myroomdatabase;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroomdatabase.database.AppDatabase;
import com.example.myroomdatabase.databinding.ActivitySearchBinding;
import com.google.android.material.button.MaterialButton;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private EditText etSearch;
    private MaterialButton btnSearch;
    private RecyclerView recyclerView;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appDatabase = AppDatabase.getInstance(this);
        etSearch = binding.etSearch;
        btnSearch = binding.btnSearch;
        recyclerView = binding.recyclerView;
        UserAdapter userAdapter = new UserAdapter(appDatabase, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(userAdapter);

        btnSearch.setOnClickListener(view -> {
            userAdapter.searchUser(appDatabase.userDao().loadAllByFirstName(etSearch.getText().toString()));
        });
    }
}