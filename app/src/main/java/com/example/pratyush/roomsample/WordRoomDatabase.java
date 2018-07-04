package com.example.pratyush.roomsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Word.class},version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao
    public abstract WordDao wordDao();

    //Make the WordRoomDatabase a singleton to prevent having multiple instances of the database opened at the same time
    private static WordRoomDatabase INSTANCE;
    static WordRoomDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE==null){
                    //This code uses Room's database builder to create a RoomDatabase object in the application context from the WordRoomDatabase class and names it "word_database".
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class,"word_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }
}

