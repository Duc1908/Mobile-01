package com.example.lehuynhduc_2122110265;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Tránh giao diện tràn status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText objEmail = findViewById(R.id.txtEmail);
                String txtEmail = objEmail.getText().toString();

                EditText objPass = findViewById(R.id.txtPass);
                String txtPass = objPass.getText().toString();

                if (txtEmail.equals("duc") && txtPass.equals("123"))
                {
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);

                    it.putExtra("duc",txtEmail);
                    it.putExtra("pass",txtPass);
                    startActivity(it);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}