package com.datj.mobile.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.BlogApiService;
import com.datj.mobile.data.remote.model.Blog;
import com.datj.mobile.data.remote.response.BlogResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogViewModel extends ViewModel {
    private final MutableLiveData<List<Blog>> blogs = new MutableLiveData<>();
    private final MutableLiveData<Blog> blogDetail = new MutableLiveData<>();

    private final BlogApiService service = RetrofitClient.getBlogApiService();

    public LiveData<List<Blog>> getBlogs() {
        return blogs;
    }

    public LiveData<Blog> getBlogDetail() {
        return blogDetail;
    }

    public void fetchBlogs(int pageSize, int pageNumber) {
        service.getBlogs(pageSize, pageNumber).enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(Call<BlogResponse> call, Response<BlogResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    blogs.setValue(response.body().getBlog());
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                } else {
                    blogs.setValue(new ArrayList<>());
                    Log.e("API_ERROR", "Lỗi phản hồi từ server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BlogResponse> call, Throwable t) {
                blogs.setValue(new ArrayList<>());
                Log.e("API_ERROR", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}
