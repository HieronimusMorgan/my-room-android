package com.example.myroomdatabase;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myroomdatabase.database.AppDatabase;
import com.example.myroomdatabase.database.entity.User;
import com.google.android.material.button.MaterialButton;

public class DetailUser extends Dialog {
    private User user;
    private EditText etFirstName, etLastName;
    private MaterialButton btnUpdate;
    private AppDatabase appDatabase;
    private onClose onClose;

    public DetailUser(@NonNull Context context, User user, onClose onClose) {
        super(context);
        this.user = user;
        appDatabase = AppDatabase.getInstance(context);
        this.onClose = onClose;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_detail_user);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        btnUpdate = findViewById(R.id.btn_update);

        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
        btnUpdate.setOnClickListener(view -> {
            user.setFirstName(etFirstName.getText().toString());
            user.setLastName(etLastName.getText().toString());
            appDatabase.userDao().updateUser(user);
            dismiss();
            onClose.close();
        });
    }


}