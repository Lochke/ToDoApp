package com.example.todoapp;

import java.io.Serializable;

public class ToDo implements Serializable {
    private int id; // 🔑 ID khóa chính
    private String title;
    private String description;
    private String contactName;
    private String contactPhone;
    private String dueDate;
    private boolean isCompleted;

    // ✅ Constructor có ID (dùng khi lấy từ DB)
    public ToDo(int id, String title, String description, String contactName, String contactPhone, String dueDate, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // ✅ Constructor không có ID (dùng khi tạo mới)
    public ToDo(String title, String description, String contactName, String contactPhone, String dueDate, boolean isCompleted) {
        this(-1, title, description, contactName, contactPhone, dueDate, isCompleted);
    }

    public ToDo(String title, String description, String contactName, String contactPhone) {
        this(-1, title, description, contactName, contactPhone, "", false);
    }


    // ✅ Getter / Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
