package com.example.lehuynhduc_2122110265;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private String[] tutorials = {
            "Algorithms",
            "Data Structures",
            "Languages",
            "Interview Corner",
            "GATE",
            "ISRO CS",
            "UGC NET CS",
            "CS Subjects",
            "Web Technologies"
    };

    private TextView tvTitle, tvProductName, tvProductPrice;
    private ImageView imgProduct;
    private Button btnLogout;

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
        listView = findViewById(R.id.list);

        // Lấy email từ LoginActivity
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            tvTitle.setText("Chào mừng " + email + " đến với cửa hàng");
        } else {
            tvTitle.setText("Danh sách sản phẩm");
        }

        // Thông tin sản phẩm mẫu
        tvProductName.setText("Cà phê rang xay");
        tvProductPrice.setText("Giá: 150.000₫");
        imgProduct.setImageResource(R.drawable.logocaffe);

        // Hiển thị danh sách Tutorials
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                tutorials
        );
        listView.setAdapter(arrAdapter);

        // Sự kiện Logout
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
