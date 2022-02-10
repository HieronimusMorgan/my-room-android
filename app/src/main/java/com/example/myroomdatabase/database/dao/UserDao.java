package com.example.myroomdatabase.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myroomdatabase.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE '%'||:name||'%' OR last_name LIKE '%'||:name||'%'")
    List<User> loadAllByFirstName(String name);

    @Query("SELECT * FROM user WHERE id = :uid")
    User getUserById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUser(List<User> order);

    @Update
    void updateUser(User... users);

    @Delete
    void delete(User user);
}
