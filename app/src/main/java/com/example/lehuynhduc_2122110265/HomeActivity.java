package com.example.lehuynhduc_2122110265;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView tvTitle, tvProductName, tvProductPrice;
    private ImageView imgProduct;
    private Button btnLogout; // Thêm biến nút logout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ view
        tvTitle = findViewById(R.id.tvTitle);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        imgProduct = findViewById(R.id.imgProduct);
        btnLogout = findViewById(R.id.bntLogout);

        // Gán dữ liệu mẫu
        tvTitle.setText("Danh sách sản phẩm");
        tvProductName.setText("Cà phê rang xay");
        tvProductPrice.setText("Giá: 150.000₫");
        imgProduct.setImageResource(R.drawable.logocaffe);

        // Sự kiện nút đăng xuất
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // đóng HomeActivity để không quay lại khi bấm back
        });
    }
}
