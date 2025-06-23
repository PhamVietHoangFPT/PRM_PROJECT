package com.datj.mobile.data.remote.response;

import com.datj.mobile.data.remote.model.Blog;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogResponse {
    @SerializedName("blogs")
    private List<Blog> blogs;
    public List<Blog> getBlog(){return blogs;}
}
