package com.github.subho57.newsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.subho57.newsapp.models.Article;

import java.util.List;

@Dao
public interface HeadlinesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<Article> articles);

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM articles WHERE category=:category ORDER BY published_at DESC")
    LiveData<List<Article>> getArticleByCategory(String category);
}
