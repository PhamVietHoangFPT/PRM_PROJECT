package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Order;
import com.datj.mobile.data.remote.response.CaptureResponse;
import com.datj.mobile.data.remote.response.PaypalOrderResponse;
import com.datj.mobile.data.remote.response.TransactionResponse;
import com.datj.mobile.data.remote.resquest.CapturePaypalOrderRequest;
import com.datj.mobile.data.remote.resquest.CreatePaypalOrderRequest;
import com.datj.mobile.data.remote.resquest.OrderRequest;
import com.datj.mobile.data.remote.resquest.TransactionRequest;
import com.datj.mobile.data.remote.response.OrderResponse; // ✔️ đúng
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    @GET("api/Orders") // Giả định endpoint, thay bằng endpoint thực tế
    Call<List<Order>> getOrders();

    @POST("api/Order")
    Call<OrderResponse> createOrder(
            @Body OrderRequest orderRequest
    );

    // Gửi orderId và reference lên server để lưu thông tin đơn PayPal
    @POST("api/Order/createPaypalOrder")
    Call<PaypalOrderResponse> createPaypalOrder(
            @Body CreatePaypalOrderRequest request
    );

    // Capture đơn hàng sau khi thanh toán PayPal thành công
    @POST("api/Order/capturePaypalOrder")
    Call<CaptureResponse> capturePaypalOrder(
            @Body CapturePaypalOrderRequest request
    );

    // Tạo transaction tương ứng với order đã thanh toán
    @POST("api/Transactions")
    Call<TransactionResponse> createTransaction(
            @Body TransactionRequest request
    );

    // Hoàn tất giao dịch
    @PUT("api/Transactions/completePayment/{id}")
    Call<ResponseBody> completeTransaction(
            @Path("id") String transactionId
    );

    // Hoàn tất đơn hàng
    @PUT("api/Order/completePayment/{orderId}")
    Call<ResponseBody> completeOrder(
            @Path("orderId") String orderId
    );

}
