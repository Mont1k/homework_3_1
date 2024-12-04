package com.example.homework_3_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String senderEmail = intent.getStringExtra("sender_email");
        String recipientEmail = intent.getStringExtra("recipient_email");
        String emailSubject = intent.getStringExtra("email_subject");
        String emailBody = intent.getStringExtra("email_body");
        String fileName = intent.getStringExtra("file_name");

        String displayMessage = senderEmail + " sent message to " + recipientEmail + "\n\n" +
                "Subject: " + emailSubject + "\n" +
                "Body: " + emailBody + "\n";

        Log.d(TAG, displayMessage);

        TextView textView = findViewById(R.id.textView);
        textView.setText(displayMessage);
    }
}
