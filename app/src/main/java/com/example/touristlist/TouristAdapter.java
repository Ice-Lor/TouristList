package com.example.touristlist; // Đảm bảo đúng package

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TouristAdapter extends RecyclerView.Adapter<TouristAdapter.ViewHolder> {
    private List<TouristSpot> list;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(TouristSpot spot, int position);
        void onDeleteClick(int position);
    }

    public TouristAdapter(List<TouristSpot> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tourist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TouristSpot spot = list.get(position);
        holder.tvName.setText(spot.getName());
        holder.tvDesc.setText(spot.getDescription());

        // --- PHẦN MỚI: HIỂN THỊ ẢNH TỪ URI ---
        if (spot.getImageUri() != null && !spot.getImageUri().isEmpty()) {
            try {
                holder.imgThumb.setImageURI(Uri.parse(spot.getImageUri()));
            } catch (Exception e) {
                // Nếu lỗi thì hiện ảnh mặc định để không crash
                holder.imgThumb.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            holder.imgThumb.setImageResource(R.drawable.ic_launcher_background);
        }
        // -------------------------------------

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(spot, position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        ImageView imgThumb;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}