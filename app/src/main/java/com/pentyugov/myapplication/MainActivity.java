package com.pentyugov.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int ACTIVITY_SECOND = 2;
    private TextView nameView;
    private Button changeNameBtn;
    private EditText nameInput;
    private Button alertBtn;
    private Button aboutBtn;
    private Button secondActBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        nameView = findViewById(R.id.textView);
        changeNameBtn = findViewById(R.id.changeNameBtn);
        nameInput = findViewById(R.id.nameInput);
        alertBtn = findViewById(R.id.alertBtn);
        aboutBtn = findViewById(R.id.aboutBtn);
        secondActBtn = findViewById(R.id.main_secondActBtn);

        secondActBtn.setOnClickListener(v -> {
            if(nameInput.getText() != null && !"".equalsIgnoreCase(nameInput.getText().toString())) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("userName", nameInput.getText().toString());
                startActivityForResult(intent, ACTIVITY_SECOND);
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setMessage("Enter name").setCancelable(false).setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }

        });

        changeNameBtn.setOnClickListener(v -> {
            if(nameInput.getText() != null && !"".contentEquals(nameInput.getText())) {
                nameView.setText(nameInput.getText());
                Toast.makeText(MainActivity.this, nameView.getText(), Toast.LENGTH_LONG).show();
            }
        });

        alertBtn.setOnClickListener(v -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setMessage(R.string.alertMsg)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alertPosBtn, (dialog, which) -> finish())
                    .setNegativeButton(R.string.alertNegBtn, (dialog, which) -> dialog.cancel());
            AlertDialog alert = alertBuilder.create();
            alert.setTitle(R.string.alertCloseTitle);
            alert.show();
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_SECOND && resultCode == RESULT_OK) {
            nameView.setText(data.getStringExtra(SecondActivity.ANIMAL));
        }
    }
}