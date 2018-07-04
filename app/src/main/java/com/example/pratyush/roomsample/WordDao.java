package com.example.pratyush.roomsample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
//The DAO must be an interface or abstract class
//By default, all queries must be executed on a separate thread.
@Dao
public interface WordDao {
    @Insert
    void insert(Word word);
    @Query("DELETE FROM word_table")
    void deleteAll();
    @Query("SELECT * FROM word_table ORDER BY word DESC")
    //In WordDao, change the getAllWords() method signature so that the returned List<Word> is wrapped with LiveData.
    LiveData<List<Word>> getAllWords();
}
