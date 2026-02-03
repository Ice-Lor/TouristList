package com.example.touristlist; // Đảm bảo đúng package của bạn

import java.io.Serializable;

public class TouristSpot implements Serializable {
    private int id;
    private String name;
    private String description;
    private String imageUri; // ĐỔI TỪ INT SANG STRING

    public TouristSpot(int id, String name, String description, String imageUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // GETTER/SETTER MỚI CHO STRING
    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}