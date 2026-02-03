package com.example.touristlist; // Đảm bảo đúng package

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {
    EditText edtName, edtDesc;
    Button btnSave, btnPickImage;
    ImageView imgPreview;
    TouristSpot currentSpot;
    boolean isEditMode = false;
    Uri selectedImageUri = null; // Lưu Uri ảnh được chọn

    // Launcher cho việc chọn ảnh
    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imgPreview.setImageURI(selectedImageUri); // Hiển thị xem trước
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDesc);
        btnSave = findViewById(R.id.btnSave);
        btnPickImage = findViewById(R.id.btnPickImage);
        imgPreview = findViewById(R.id.imgPreview);

        Intent intent = getIntent();
        if (intent.hasExtra("item_data")) {
            currentSpot = (TouristSpot) intent.getSerializableExtra("item_data");
            edtName.setText(currentSpot.getName());
            edtDesc.setText(currentSpot.getDescription());

            // Hiển thị lại ảnh cũ nếu có
            if (currentSpot.getImageUri() != null) {
                selectedImageUri = Uri.parse(currentSpot.getImageUri());
                imgPreview.setImageURI(selectedImageUri);
            }
            isEditMode = true;
        }

        // Sự kiện chọn ảnh
        btnPickImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickIntent);
        });

        // Sự kiện Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String desc = edtDesc.getText().toString();

                // Bắt buộc phải chọn ảnh
                if (selectedImageUri == null) {
                    Toast.makeText(InputActivity.this, "Vui lòng chọn hình ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String imageUriString = selectedImageUri.toString(); // Chuyển Uri thành String

                Intent resultIntent = new Intent();
                TouristSpot resultSpot;

                if (isEditMode) {
                    currentSpot.setName(name);
                    currentSpot.setDescription(desc);
                    currentSpot.setImageUri(imageUriString); // Cập nhật ảnh mới
                    resultSpot = currentSpot;
                } else {
                    // Tạo mới với ảnh đã chọn
                    resultSpot = new TouristSpot((int)System.currentTimeMillis(), name, desc, imageUriString);
                }

                resultIntent.putExtra("result_data", resultSpot);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}