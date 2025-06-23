package com.datj.mobile.data.remote.model;

import com.google.gson.annotations.SerializedName;
    public class Blog {
        private int blogId;
        private int authorId;
        public Author author;
        private String authorName;
        private String title;
        private String content;
        private String createdAt;
        private boolean isHidden;

        public Blog() {
        }

        public Blog(int blogId, int authorId, Author author, String title, String content, String createdAt, boolean isHidden) {
            this.blogId = blogId;
            this.authorId = authorId;
            this.author = author;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
            this.isHidden = isHidden;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public int getBlogId() {
            return blogId;
        }

        public void setBlogId(int blogId) {
            this.blogId = blogId;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isHidden() {
            return isHidden;
        }

        public void setHidden(boolean hidden) {
            isHidden = hidden;
        }
        // Getters and Setters
    }


