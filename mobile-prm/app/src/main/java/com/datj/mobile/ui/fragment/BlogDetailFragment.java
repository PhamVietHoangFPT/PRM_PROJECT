package com.datj.mobile.ui.fragment;

import static com.datj.mobile.data.remote.RetrofitClient.getBlogApiService;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.datj.mobile.R;
import com.datj.mobile.data.remote.api.BlogApiService;
import com.datj.mobile.data.remote.model.Blog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailFragment extends Fragment {
    private Blog blog;
    private int blogId;
    private Call<Blog> blogCall;

    public static BlogDetailFragment newInstance(int blogId) {
        BlogDetailFragment fragment = new BlogDetailFragment();
        Bundle args = new Bundle();
        args.putInt("blogId", blogId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_detail, container, false);

        blogId = getArguments().getInt("blogId");
        BlogApiService apiService = getBlogApiService();

        blogCall = apiService.getBlogsById(blogId);

        blogCall.enqueue(new Callback<Blog>() {
            @Override
            public void onResponse(Call<Blog> call, Response<Blog> response) {
                if (response.isSuccessful() && response.body() != null) {
                    blog = response.body();
                    setupUI(view);
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy bài viết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Blog> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setupUI(View view) {
        // Hiển thị tiêu đề và tác giả
        ((TextView) view.findViewById(R.id.titleName)).setText(blog.getTitle());
        ((TextView) view.findViewById(R.id.nameAuthor)).setText("Tác giả: " +
                (blog.getAuthor() != null ? blog.getAuthor().getName() : "Không rõ"));

        // Lấy nội dung bài viết
        String contentHtml = blog.getContent();

        // **Thêm meta viewport và chỉnh font-size**
        String formattedHtml = "<html><head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<style>"
                + "body {padding: 10px; font-size: 16px; line-height: 1.6; text-align: justify;}"
                + "img {max-width: 100%; height: auto; display: block; margin: 10px auto;}"
                + "</style>"
                + "</head><body>" + contentHtml + "</body></html>";

        // Hiển thị nội dung trong WebView
        WebView webView = view.findViewById(R.id.blogContent);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // Cho phép JavaScript nếu cần
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, formattedHtml, "text/html", "utf-8", null);

        // Xử lý nút Back
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }


    @Override
    public void onDestroyView() {
        if (blogCall != null && !blogCall.isCanceled()) {
            blogCall.cancel();
        }
        super.onDestroyView();
    }
}
