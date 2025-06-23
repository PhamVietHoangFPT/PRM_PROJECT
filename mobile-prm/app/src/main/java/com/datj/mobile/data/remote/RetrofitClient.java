package com.datj.mobile.data.remote;

import android.content.Context;

import com.datj.mobile.data.remote.api.AccessoryApiService;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.api.OrderApiService;
import com.datj.mobile.data.remote.api.BlogApiService;
import com.datj.mobile.data.remote.api.PromotionApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {
        private static final String BASE_URL = "http://10.0.2.2:8080/";
        private static Retrofit retrofit = null;

        private static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                // Tạo HttpLoggingInterceptor để debug log (tùy chọn)
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                // Tạo OkHttpClient và thêm interceptor
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new AuthInterceptor())
                        .addInterceptor(logging)
                        .build();

                // Khởi tạo Retrofit với client
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }
            return retrofit;
        }
        // 🔄 Sử dụng lại getRetrofitInstance() để tạo AccountApiService
        public static AccountApiService getAccountApiService() {
            return getRetrofitInstance().create(AccountApiService.class);
        }
        public static OrderApiService getOrderApiService() {
            return getRetrofitInstance().create(OrderApiService.class);
        }
        public static AccessoryApiService getAccessoryApiService() {
            return getRetrofitInstance().create(AccessoryApiService.class);
        }
        public static BlogApiService getBlogApiService(){
            return getRetrofitInstance().create(BlogApiService.class);
        }

        public static PromotionApiService getPromotionApiService() {
            return getRetrofitInstance().create(PromotionApiService.class);
        }
        

    }

