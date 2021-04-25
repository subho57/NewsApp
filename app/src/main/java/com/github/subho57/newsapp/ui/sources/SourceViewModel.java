package com.github.subho57.newsapp.ui.sources;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.subho57.newsapp.data.NewsRepository;
import com.github.subho57.newsapp.models.Source;
import com.github.subho57.newsapp.models.Specification;

import java.util.List;

public class SourceViewModel extends AndroidViewModel {
    private final NewsRepository newsRepository;

    public SourceViewModel(@NonNull Application application) {
        super(application);
        this.newsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    LiveData<List<Source>> getSource(Specification specification) {
        return newsRepository.getSources(specification);
    }
}
