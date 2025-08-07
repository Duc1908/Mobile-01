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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNext = findViewById(R.id.btnLogin);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText objEmail = findViewById(R.id.txtEmail);
                String txtEmail = objEmail.getText().toString();


                EditText objPass = findViewById(R.id.txtPass);
                String txtPass = objPass.getText().toString();

                if (txtEmail.equals("duc") && txtPass.equals("123")) {
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);

                    it.putExtra("email", txtEmail);
                    it.putExtra("pass", txtPass);


                    startActivity(it);
                } else {
                    Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),RegisterActivity.class);


                startActivity(it);
            }
        });
    }
}