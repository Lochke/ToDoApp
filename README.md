# 📝 TodoApp

TodoApp là một ứng dụng Android đơn giản giúp bạn quản lý các công việc hàng ngày. Với giao diện trực quan và tiện lợi, bạn có thể thêm, chỉnh sửa, xoá, và theo dõi trạng thái hoàn thành của từng công việc.

## 🚀 Tính năng chính

- 📋 Hiển thị danh sách công việc theo thứ tự mới nhất.
- ➕ Thêm công việc mới với:
  - Tiêu đề
  - Mô tả
  - Ngày hoàn thành
  - Thông tin người liên hệ (tên và số điện thoại)
  - Trạng thái hoàn thành ✅/❌
- ✏️ Chỉnh sửa công việc đã có (bao gồm cả chọn lại contact).
- 🗑️ Xoá công việc bằng cách nhấn giữ.
- 📱 Tích hợp Contact Provider:
  - Chọn liên hệ từ danh bạ điện thoại khi tạo/chỉnh sửa task.
- 💾 Dữ liệu được lưu trữ bằng SQLite.
- 🔙 Nút điều hướng trở về (arrow back trên ActionBar).
- 📤 Hỗ trợ upload mã nguồn lên GitHub.

## 📂 Cấu trúc thư mục

```
.
├── app/
│   ├── java/com/example/todoapp/
│   │   ├── MainActivity.java
│   │   ├── AddActivity.java
│   │   ├── DetailActivity.java
│   │   ├── models/ToDo.java
│   │   ├── TodoDatabaseHelper.java
│   │   ├── TodoAdapter.java
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml
│       │   ├── activity_add.xml
│       │   ├── activity_details.xml
│       │   └── list_item_todo.xml
│       └── values/
│           ├── strings.xml
│           └── colors.xml
└── README.md
```

## ⚙️ Cài đặt

1. Tải mã nguồn về:
```bash
git clone https://github.com/Lochke/ToDoApp.git
cd ToDoApp
```

2. Mở project bằng **Android Studio**.

3. Kết nối thiết bị/emulator Android.

4. Nhấn **Run** để chạy ứng dụng.

## 🛠 Yêu cầu hệ thống

- Android Studio Arctic Fox trở lên
- Thiết bị/emulator Android 7.0+
- Cho phép quyền đọc danh bạ để chọn liên hệ

## 📸 Ảnh minh hoạ

| Màn hình chính | Thêm Task | Chi tiết Task |
|----------------|-----------|---------------|
| ![](docs/screenshot_main.png) | ![](docs/screenshot_add.png) | ![](docs/screenshot_detail.png) |

## 🧱 Dùng với Docify

Bạn có thể sử dụng [Docify](https://docify.dev) để tự động sinh trang tài liệu:

```bash
npm install -g docify-cli
docify dev
```

## 📬 Liên hệ

Phát triển bởi: **MINH LOC**  
GitHub: [@Lochke](https://github.com/Lochke)
