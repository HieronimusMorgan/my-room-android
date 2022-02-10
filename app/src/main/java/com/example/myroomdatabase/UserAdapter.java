package com.example.myroomdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroomdatabase.database.AppDatabase;
import com.example.myroomdatabase.database.entity.User;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;
    private AppDatabase appDatabase;
    private Context context;

    public UserAdapter(List<User> userList, AppDatabase appDatabase, Context context) {
        this.userList = userList;
        this.appDatabase = appDatabase;
        this.context = context;
    }

    public UserAdapter(AppDatabase appDatabase, Context context) {
        userList = new ArrayList<>();
        this.appDatabase = appDatabase;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvFirstName.setText(user.firstName);
        holder.tvLastName.setText(user.lastName);
//        holder.tvId.setText(user.uid);
        holder.btnDelete.setOnClickListener(view -> {
            appDatabase.userDao().delete(user);
            addUser(appDatabase);
        });
        holder.item.setOnClickListener(view -> {
            DetailUser fileDialog = new DetailUser(context, user, this::notifyDataSetChanged);
            fileDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFirstName;
        private final TextView tvLastName;
        private final TextView tvId;
        private final ImageView btnDelete;
        private final MaterialCardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tv_first_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvId = itemView.findViewById(R.id.tv_uid);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void addUser(AppDatabase db) {
        userList.clear();
        userList.addAll(db.userDao().getAll());
        notifyDataSetChanged();
    }

    public void searchUser(List<User> users) {
        userList.clear();
        userList.addAll(users);
        notifyDataSetChanged();
    }
}
