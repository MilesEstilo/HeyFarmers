package com.example.heyfarmers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Back button functionality
        Button backButton = findViewById(R.id.backButton); // Make sure this matches the ID in the XML
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity (Login page)
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });

        // Add logic here for registration process if needed
    }
}
