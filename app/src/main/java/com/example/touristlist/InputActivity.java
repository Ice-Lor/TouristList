package com.example.touristlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {
    EditText edtName, edtDesc;
    Button btnSave;
    TouristSpot currentSpot;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        edtName = findViewById(R.id.edtName); // Bạn tự tạo layout có id này nhé
        edtDesc = findViewById(R.id.edtDesc); // Bạn tự tạo layout có id này nhé
        btnSave = findViewById(R.id.btnSave); // Bạn tự tạo layout có id này nhé

        // Nhận Intent từ MainActivity
        Intent intent = getIntent();
        if (intent.hasExtra("item_data")) {
            // Trường hợp SỬA
            currentSpot = (TouristSpot) intent.getSerializableExtra("item_data");
            edtName.setText(currentSpot.getName());
            edtDesc.setText(currentSpot.getDescription());
            isEditMode = true;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String desc = edtDesc.getText().toString();

                Intent resultIntent = new Intent();
                // Đóng gói dữ liệu trả về
                TouristSpot resultSpot;
                if (isEditMode) {
                    currentSpot.setName(name);
                    currentSpot.setDescription(desc);
                    resultSpot = currentSpot;
                } else {
                    // Tạo mới (Giả sử ID random, hình ảnh mặc định)
                    resultSpot = new TouristSpot((int)System.currentTimeMillis(), name, desc, R.drawable.ic_launcher_background);
                }

                resultIntent.putExtra("result_data", resultSpot);
                setResult(RESULT_OK, resultIntent); // Trả về OK
                finish(); // Đóng Activity
            }
        });
    }
}