package com.datj.mobile.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.datj.mobile.data.remote.model.Product;
import com.datj.mobile.data.repository.ProductRepository;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    public ProductViewModel() {
        productRepository = new ProductRepository();
        loadProducts(); // Load danh sách sản phẩm khi khởi tạo ViewModel
    }

    private void loadProducts() {
        productRepository.getAllProducts().observeForever(products -> {
            productList.setValue(products);
        });
    }

    public LiveData<List<Product>> getAllProducts() {
        return productList;
    }
    public LiveData<Product> getProductByAccessoryId(int accessoryId) {
        return productRepository.getProductByAccessoryId(accessoryId);
    }

}
