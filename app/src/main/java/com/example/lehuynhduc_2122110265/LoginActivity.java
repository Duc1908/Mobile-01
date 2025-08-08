package com.example.lehuynhduc_2122110265;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText txtEmail, txtPass;
    Button btnLogin;

    // API của bạn
    String API_URL = "https://68940f7fbe3700414e11e379.mockapi.io/User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String password = txtPass.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });
        // Nút sang màn Register
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });
    }

    private void loginUser(String email, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, API_URL,
                response -> {
                    Log.d(TAG, "RAW_RESPONSE length=" + (response == null ? 0 : response.length()));
                    Log.d("API_RESPONSE", response == null ? "" : response);
                    handleApiResponse(response, email, password);
                },
                error -> {
                    Toast.makeText(LoginActivity.this, "Không thể kết nối API", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Volley error:", error);
                });

        queue.add(request);
    }

    private void handleApiResponse(String response, String inputEmail, String inputPassword) {
        if (response == null) {
            Toast.makeText(this, "API trả về null", Toast.LENGTH_SHORT).show();
            return;
        }

        String trimmed = response.trim();
        try {
            Object json = new JSONTokener(trimmed).nextValue();

            boolean found = false;

            if (json instanceof JSONArray) {
                JSONArray arr = (JSONArray) json;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject user = arr.optJSONObject(i);
                    if (user == null) continue;

                    String apiEmail = getStringIgnoreCase(user, "email");
                    String apiPass = getStringIgnoreCase(user, "password");

                    Log.d("API_DATA", "Entry[" + i + "] Email: '" + apiEmail + "' - Pass: '" + apiPass + "'");

                    if (apiEmail != null && apiPass != null &&
                            apiEmail.equalsIgnoreCase(inputEmail) &&
                            apiPass.equals(inputPassword)) {
                        found = true;
                        break;
                    }
                }
            } else if (json instanceof JSONObject) {
                JSONObject obj = (JSONObject) json;
                // Trường hợp object là 1 user
                String apiEmail = getStringIgnoreCase(obj, "email");
                String apiPass = getStringIgnoreCase(obj, "password");

                Log.d("API_DATA", "Object Email: '" + apiEmail + "' - Pass: '" + apiPass + "'");

                if (apiEmail != null && apiPass != null &&
                        apiEmail.equalsIgnoreCase(inputEmail) &&
                        apiPass.equals(inputPassword)) {
                    found = true;
                } else {
                    // Nếu object chứa mảng ở 1 field (ví dụ "data": [ ... ])
                    Iterator<String> keys = obj.keys();
                    while (keys.hasNext() && !found) {
                        String k = keys.next();
                        Object child = obj.opt(k);
                        if (child instanceof JSONArray) {
                            JSONArray arr = (JSONArray) child;
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject user = arr.optJSONObject(i);
                                if (user == null) continue;
                                String e = getStringIgnoreCase(user, "email");
                                String p = getStringIgnoreCase(user, "password");
                                Log.d("API_DATA", "Nested[" + k + "][" + i + "] Email: '" + e + "' - Pass: '" + p + "'");
                                if (e != null && p != null &&
                                        e.equalsIgnoreCase(inputEmail) &&
                                        p.equals(inputPassword)) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "API trả về dữ liệu không phải JSON", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Non-JSON response");
            }

            if (found) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("email", inputEmail);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, "Lỗi xử lý dữ liệu JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "JSON parse error", e);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Unexpected error", e);
        }
    }

    /**
     * Lấy giá trị của key trong JSONObject mà không phân biệt hoa/thường.
     * Nếu không tìm thấy, trả về null.
     */
    private String getStringIgnoreCase(JSONObject obj, String key) {
        if (obj == null) return null;
        // nếu key có đúng tên thì trả trực tiếp (fast path)
        if (obj.has(key)) {
            return obj.optString(key, null);
        }
        // Duyệt các key và so sánh ignore case
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String k = keys.next();
            if (k.equalsIgnoreCase(key)) {
                return obj.optString(k, null);
            }
        }
        return null;
    }
}
