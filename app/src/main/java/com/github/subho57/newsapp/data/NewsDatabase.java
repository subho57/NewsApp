package com.github.subho57.newsapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.github.subho57.newsapp.data.dao.HeadlinesDao;
import com.github.subho57.newsapp.data.dao.SavedDao;
import com.github.subho57.newsapp.data.dao.SourcesDao;
import com.github.subho57.newsapp.models.Article;
import com.github.subho57.newsapp.models.SavedArticle;
import com.github.subho57.newsapp.models.Source;

@Database(entities = {Article.class, Source.class, SavedArticle.class},
        version = 1,
        exportSchema = false)
@TypeConverters(DatabaseConverters.class)
public abstract class NewsDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "news";
    private static NewsDatabase sInstance;

    public static NewsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDatabase.class,
                        DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract HeadlinesDao headlinesDao();

    public abstract SourcesDao sourcesDao();

    public abstract SavedDao savedDao();
}
