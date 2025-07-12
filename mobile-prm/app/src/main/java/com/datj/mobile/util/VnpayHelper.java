package com.datj.mobile.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VnpayHelper {
    private static final String TAG = "VNPAY";
    private static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNP_TMN_CODE = "9NKQMZ3V";
    private static final String VNP_HASH_SECRET = "4A4XAOITTI3XCENYTJ213A4UHUSV724E";

    public static void startPaymentWithIpFetch(Context context, double usdAmount) {
        try {
            BigDecimal exchangeRate = BigDecimal.valueOf(24700);
            BigDecimal usd = BigDecimal.valueOf(usdAmount).setScale(2, RoundingMode.DOWN);
            BigDecimal vnd = usd.multiply(exchangeRate);

            // ✅ Giới hạn tối đa 1 triệu VND
            BigDecimal maxVND = BigDecimal.valueOf(1_000_000);
            if (vnd.compareTo(maxVND) > 0) {
                Log.w(TAG, "⚠️ Tổng tiền quá lớn. Đang giới hạn còn 1 triệu VND.");
                vnd = maxVND;
            }

            String amount = vnd.multiply(BigDecimal.valueOf(100)).toBigInteger().toString(); // nhân 100 (VNP yêu cầu)

            String orderId = UUID.randomUUID().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String createDate = dateFormat.format(calendar.getTime());

            calendar.add(Calendar.MINUTE, 15);
            String expireDate = dateFormat.format(calendar.getTime());

            // ✅ Tạo Map tham số
            Map<String, String> params = new TreeMap<>();
            params.put("vnp_Version", "2.1.0");
            params.put("vnp_Command", "pay");
            params.put("vnp_TmnCode", VNP_TMN_CODE);
            params.put("vnp_Amount", amount);
            params.put("vnp_CreateDate", createDate);
            params.put("vnp_ExpireDate", expireDate);
            params.put("vnp_TxnRef", orderId);
            params.put("vnp_OrderInfo", "Thanh toán đơn hàng " + orderId);
            params.put("vnp_OrderType", "other");
            params.put("vnp_Locale", "vn");
            params.put("vnp_CurrCode", "VND");
            params.put("vnp_ReturnUrl", "http://localhost:5173/payment-success");

            String ip = getIpAddress(context);
            params.put("vnp_IpAddr", ip);

            // ✅ Tạo chuỗi hash data
            StringBuilder hashData = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (hashData.length() > 0) hashData.append("&");
                hashData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            // ✅ Tạo secure hash
            String secureHash = hmacSHA512(VNP_HASH_SECRET, hashData.toString());
            params.put("vnp_SecureHash", secureHash);

            // ✅ Tạo URL cuối cùng
            StringBuilder query = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (query.length() > 0) query.append("&");
                query.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                query.append("=");
                query.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

            }

            String finalUrl = VNP_URL + "?" + query;
            Log.d(TAG, "🔗 Hash data: " + hashData);
            Log.d(TAG, "🔐 Secure hash: " + secureHash);
            Log.d(TAG, "🌐 Final payment URL: " + finalUrl);

            // ✅ Mở trình duyệt
            Intent intent = new Intent(context, com.datj.mobile.ui.customview.PaymentWebActivity.class);
            intent.putExtra("payment_url", finalUrl);
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "❌ Lỗi khi tạo URL thanh toán", e);
        }
    }

    private static String hmacSHA512(String key, String data) throws GeneralSecurityException {
        Mac hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac.init(secretKey);
        byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipInt = wifiInfo.getIpAddress();
                return InetAddress.getByAddress(new byte[]{
                        (byte) (ipInt & 0xff),
                        (byte) (ipInt >> 8 & 0xff),
                        (byte) (ipInt >> 16 & 0xff),
                        (byte) (ipInt >> 24 & 0xff)
                }).getHostAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
