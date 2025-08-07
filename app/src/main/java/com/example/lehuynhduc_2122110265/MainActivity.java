package com.example.lehuynhduc_2122110265;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://fakestoreapi.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy email từ LoginActivity
        Intent intent = getIntent();
        String txtEmail = intent.getStringExtra("email");

        TextView txtWelcome = findViewById(R.id.txtWelcome);

        if ("duc".equalsIgnoreCase(txtEmail)) {
            txtWelcome.setText("Welcome " + txtEmail);
        } else {
            txtWelcome.setText("Xin chào " + txtEmail);
        }

        // Nút Back quay về Login
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent it = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        });

        getData(); // Gọi API
    }

    private void getData() {
        mRequestQueue = Volley.newRequestQueue(this);

        mStringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d(TAG, "Response: " + response);
                    Toast.makeText(getApplicationContext(), "Đã tải dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.e(TAG, "Volley Error: " + error.toString());
                    Toast.makeText(getApplicationContext(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                });

        mRequestQueue.add(mStringRequest);
    }
}
