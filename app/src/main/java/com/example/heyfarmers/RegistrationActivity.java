package com.example.heyfarmers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private Button registerButton;

    private static final String API_URL = "http://pzf.22b.mytemp.website/api/register.php";
    private static final String API_KEY = "ec1d953f9c4ac993f94308dfbfcfcb592bbf5b933926bd35d0ae99951edae04a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize UI components
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.registerButton);

        // Back button functionality
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity (Login page)
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set onClick listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate inputs and start registration if valid
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Get user inputs
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(firstName)) {
            firstNameEditText.setError("First name is required");
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            lastNameEditText.setError("Last name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        // Create a new thread for the network request
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create URL object
                    URL url = new URL(API_URL);

                    // Create connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setDoOutput(true);

                    // Create the POST parameters
                    String postData = "api_key=" + API_KEY
                            + "&first_name=" + firstName
                            + "&last_name=" + lastName
                            + "&email=" + email
                            + "&password=" + password;

                    // Send request
                    OutputStream os = connection.getOutputStream();
                    os.write(postData.getBytes());
                    os.flush();
                    os.close();

                    // Get the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Handle the API response on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Check the response (simplified for this example)
                            if (response.toString().contains("success")) {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                // Redirect to MainActivity (Login page)
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed: " + response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    // Handle the error on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
