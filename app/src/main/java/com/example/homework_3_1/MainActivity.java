package com.example.homework_3_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private Button nextButton;
    private boolean isEmailSent = false; // Флаг для проверки отправки письма

    private String recipientEmail = "tut_byla_by_pocta_prepoda@gmail.com";
    private String senderEmail = "orozovnurtilek22@gmail.com";
    private String emailSubject = "Photo from gallery";
    private String emailBody = "Proverka pisma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addImageButton = findViewById(R.id.add_image_button);
        nextButton = findViewById(R.id.next_button);

        nextButton.setEnabled(false);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailSent) {
                    goToNextActivity();
                } else {
                    Toast.makeText(MainActivity.this, "Otprav pismo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                sendEmailWithImage(selectedImageUri);
            } else {
                Toast.makeText(this, "Выбери фотку", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmailWithImage(Uri imageUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("image/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
        emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Vyberi gmail"));
            isEmailSent = true;
            nextButton.setEnabled(true);
        } else {
            Toast.makeText(this, "Netu takogo", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToNextActivity() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("sender_email", senderEmail);
        intent.putExtra("recipient_email", recipientEmail);
        intent.putExtra("email_subject", emailSubject);
        intent.putExtra("email_body", emailBody);

        if (selectedImageUri != null) {
            intent.putExtra("file_name", selectedImageUri.getLastPathSegment());
        } else {
            intent.putExtra("file_name", "File ne vibran");
        }

        startActivity(intent);
    }
}
