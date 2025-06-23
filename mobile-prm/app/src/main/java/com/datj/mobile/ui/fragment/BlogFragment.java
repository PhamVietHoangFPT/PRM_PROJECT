package com.datj.mobile.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.viewmodel.BlogViewModel;
import com.datj.mobile.data.remote.model.Blog;

import java.util.ArrayList;

public class BlogFragment extends Fragment {
    private RecyclerView recyclerView;
    private BlogAdapter adapter;
    private BlogViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        recyclerView = view.findViewById(R.id.blogRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ✅ Khởi tạo adapter với OnBlogClickListener
        adapter = new BlogAdapter(new ArrayList<>(), blog -> {
            // Xử lý khi click vào bài viết
            Toast.makeText(getContext(), "Clicked: " + blog.getTitle(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        // ✅ Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(BlogViewModel.class);

        // ✅ Quan sát dữ liệu từ ViewModel
        viewModel.getBlogs().observe(getViewLifecycleOwner(), blogs -> {
            if (blogs != null) {
                adapter.updateBlogList(blogs);
                Log.d("TestUI", "Adapter item count: " + adapter.getItemCount());
            }
        });

        // ✅ Gọi API để lấy dữ liệu
        viewModel.fetchBlogs(10, 1);


        return view;
    }
}
