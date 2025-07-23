package com.example.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private String image;
    private boolean active;
    
    public Category() {}
    
    public Category(String name, String description, String image, boolean active) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.active = active;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}