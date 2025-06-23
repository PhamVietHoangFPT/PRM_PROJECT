package com.datj.mobile.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Blog;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private List<Blog> blogList;
    private OnBlogClickListener listener;

    public interface OnBlogClickListener {
        void onBlogClick(Blog blog);
    }

    public BlogAdapter(List<Blog> blogList, OnBlogClickListener listener) {
        this.blogList = blogList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onBlogClick(blog);
        });
        holder.blogNameAuthor.setText(blog.getAuthorName());
        holder.blogTitle.setText(blog.getTitle());

        // Cấu hình WebView
        holder.blogContent.getSettings().setJavaScriptEnabled(true);
        holder.blogContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.blogContent.setWebViewClient(new WebViewClient());

        holder.itemView.setOnClickListener(v -> {
            int id = blog.getBlogId();
            Fragment detailFragment = BlogDetailFragment.newInstance(id);
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Load nội dung bài viết
//        String htmlContent = "<html><body style='margin:0;padding:0;'>" + blog.getContent() + "</body></html>";
//        holder.blogContent.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    @Override
    public int getItemCount() {
        return blogList != null ? blogList.size() : 0;
    }

    public void updateBlogList(List<Blog> newList) {
        this.blogList = newList;
        notifyDataSetChanged();
    }

    static class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView blogNameAuthor;
        TextView blogTitle;
        WebView blogContent;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            blogNameAuthor = itemView.findViewById(R.id.blogNamePost);
            blogTitle = itemView.findViewById(R.id.tileBlog);
            blogContent = itemView.findViewById(R.id.webView);
        }
    }
}
