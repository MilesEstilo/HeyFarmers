package com.example.heyfarmers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginProcess extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.button);
        Button registerButton = findViewById(R.id.button3);

        loginButton.setOnClickListener(v -> loginUser());

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginProcess.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL("http://pzf.22b.mytemp.website/api/validate.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setDoOutput(true);

                String requestBody = "api_key=" + URLEncoder.encode("ec1d953f9c4ac993f94308dfbfcfcb592bbf5b933926bd35d0ae99951edae04a", "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8");

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(requestBody);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                InputStream inputStream = (responseCode == HttpURLConnection.HTTP_OK) ? urlConnection.getInputStream() : urlConnection.getErrorStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                runOnUiThread(() -> handleLoginResponse(response.toString()));

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void handleLoginResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            String message = jsonResponse.getString("message");

            if (success) {
                SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("user_login", true);
                editor.apply();

                Intent intent = new Intent(LoginProcess.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid response format", Toast.LENGTH_SHORT).show();
        }
    }
}
