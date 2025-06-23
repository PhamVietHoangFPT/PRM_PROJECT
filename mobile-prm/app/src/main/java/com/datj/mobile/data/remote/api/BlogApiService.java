package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Blog;
import com.datj.mobile.data.remote.response.BlogResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlogApiService {
    @GET("api/Blogs")
    Call<BlogResponse> getBlogs(
            @Query("PageSize") int pageSize,
            @Query("PageNumber") int pageNumber
    );
    @GET("api/Blogs/{id}")
    Call<Blog> getBlogsById(@Path("id") int id);
}
