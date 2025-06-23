package com.datj.mobile.ui.customview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Product;
import com.datj.mobile.ui.viewmodel.ProductViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    private TextView productName, productPrice, productDescription;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        int accessoryId = getIntent().getIntExtra("ACCESSORY_ID", -1); // Lấy đúng key

        if (accessoryId == -1) {
            Toast.makeText(this, "Invalid Accessory ID", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu ID không hợp lệ
            return;
        }

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProductByAccessoryId(accessoryId).observe(this, product -> {
            if (product != null) {
                Log.d("API_DATA", "Product Name: " + product.getName()); // Log dữ liệu lấy được
                productName.setText(product.getName());
                productPrice.setText("Processing Price: $" + product.getAccessoryType().getProcessingPrice());
            } else {
                Log.e("API_ERROR", "Product not found");
                Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
