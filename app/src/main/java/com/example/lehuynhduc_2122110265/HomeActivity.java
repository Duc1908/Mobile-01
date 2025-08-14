package com.example.lehuynhduc_2122110265;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Product> allProductsList; // Danh sách tất cả sản phẩm
    private ArrayList<Product> displayedProductList; // Danh sách sản phẩm đang hiển thị
    private AdapterActivity adapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridView = findViewById(R.id.gridView);
        Button btnLogout = findViewById(R.id.bntLogout);
        etSearch = findViewById(R.id.etSearch); // Ánh xạ EditText tìm kiếm

        // Tạo danh sách sản phẩm mẫu
        allProductsList = new ArrayList<>();
        allProductsList.add(new Product("Cà phê rang say", 100000, R.drawable.logocaffe));
        allProductsList.add(new Product("Trà sữa mattcha", 200000, R.drawable.matcha));
        allProductsList.add(new Product("Sinh tố xoài", 150000, R.drawable.xoai));
        allProductsList.add(new Product("Ca phê của nu", 250000, R.drawable.logocaffe));
        // Thêm các sản phẩm khác nếu cần để có nhiều loại cho phần lọc

        // Ban đầu hiển thị tất cả sản phẩm
        displayedProductList = new ArrayList<>(allProductsList);

        // Gán adapter
        adapter = new AdapterActivity(this, displayedProductList);
        gridView.setAdapter(adapter);

        // Xử lý tìm kiếm khi người dùng nhập vào EditText
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì trước khi text thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi hàm lọc sản phẩm khi text thay đổi
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần làm gì sau khi text thay đổi
            }
        });

        // Xử lý logout
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Thêm lắng nghe sự kiện cho các nút danh mục (nếu bạn muốn lọc theo danh mục)
        // Ví dụ:
        // findViewById(R.id.btnCoffee).setOnClickListener(v -> filterByCategory("Cà phê"));
        // Bạn sẽ cần đặt ID cụ thể cho từng nút trong XML và tạo hàm filterByCategory
    }

    /**
     * Lọc danh sách sản phẩm dựa trên từ khóa tìm kiếm.
     * @param query Từ khóa tìm kiếm
     */
    private void filterProducts(String query) {
        displayedProductList.clear(); // Xóa danh sách hiển thị hiện tại
        if (query.isEmpty()) {
            // Nếu query rỗng, hiển thị tất cả sản phẩm
            displayedProductList.addAll(allProductsList);
        } else {
            // Chuyển đổi query về chữ thường để tìm kiếm không phân biệt hoa thường
            String lowerCaseQuery = query.toLowerCase();
            for (Product product : allProductsList) {
                // Nếu tên sản phẩm chứa từ khóa tìm kiếm, thêm vào danh sách hiển thị
                if (product.getName().toLowerCase().contains(lowerCaseQuery)) {
                    displayedProductList.add(product);
                }
            }
        }
        // Thông báo cho adapter rằng dữ liệu đã thay đổi để GridView cập nhật lại
        adapter.notifyDataSetChanged();
    }
}