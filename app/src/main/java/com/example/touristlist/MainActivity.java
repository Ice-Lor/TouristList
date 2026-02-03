package com.example.touristlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TouristAdapter adapter;
    ArrayList<TouristSpot> spotList;
    Button btnAdd;
    int editingPosition = -1; // Lưu vị trí đang sửa

    // Launcher để nhận dữ liệu từ Activity 2
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                TouristSpot returnedSpot = (TouristSpot) result.getData().getSerializableExtra("result_data");

                if (editingPosition >= 0) {
                    // Cập nhật (Sửa)
                    spotList.set(editingPosition, returnedSpot);
                    adapter.notifyItemChanged(editingPosition);
                    editingPosition = -1; // Reset
                } else {
                    // Thêm mới
                    spotList.add(returnedSpot);
                    adapter.notifyItemInserted(spotList.size() - 1);
                }
            }
        }
    }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        // Khởi tạo dữ liệu mẫu
        spotList = new ArrayList<>();
        spotList.add(new TouristSpot(1, "Vịnh Hạ Long", "Di sản thiên nhiên", R.drawable.ic_launcher_background));

        // Cấu hình RecyclerView
        adapter = new TouristAdapter(spotList, this, new TouristAdapter.OnItemClickListener() {
        @Override
        public void onEditClick(TouristSpot spot, int position) {
            // Chuyển sang Activity 2 để sửa
            editingPosition = position;
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            intent.putExtra("item_data", spot);
            launcher.launch(intent);
        }

        @Override
        public void onDeleteClick(int position) {
            // Xóa trực tiếp
            spotList.remove(position);
            adapter.notifyItemRemoved(position);
        }
    });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Nút Thêm (Màu Xanh)
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingPosition = -1; // Đánh dấu là đang thêm mới
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                launcher.launch(intent);
            }
        });
    }
}