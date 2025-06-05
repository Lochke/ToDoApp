package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 2; // ⬅️ Tăng version lên 2

    private static final String TABLE_TODO = "todos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CONTACT_NAME = "contact_name";
    private static final String COLUMN_CONTACT_PHONE = "contact_phone";
    private static final String COLUMN_DUE_DATE = "due_date";
    private static final String COLUMN_COMPLETED = "is_completed";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TODO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_CONTACT_PHONE + " TEXT, " +
                COLUMN_DUE_DATE + " TEXT, " +
                COLUMN_COMPLETED + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    // ✅ Cập nhật schema mà KHÔNG mất dữ liệu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_TODO + " ADD COLUMN " + COLUMN_DUE_DATE + " TEXT");
            } catch (Exception ignored) {}
            try {
                db.execSQL("ALTER TABLE " + TABLE_TODO + " ADD COLUMN " + COLUMN_COMPLETED + " INTEGER DEFAULT 0");
            } catch (Exception ignored) {}
        }
    }

    public void insertTodo(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todo.getTitle());
        values.put(COLUMN_DESCRIPTION, todo.getDescription());
        values.put(COLUMN_CONTACT_NAME, todo.getContactName());
        values.put(COLUMN_CONTACT_PHONE, todo.getContactPhone());
        values.put(COLUMN_DUE_DATE, todo.getDueDate());
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0);
        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public ArrayList<ToDo> getAllTodos() {
        ArrayList<ToDo> todos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_NAME));
                String contactPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_PHONE));
                String dueDate = "";
                boolean completed = false;

                // ⚠️ Tránh crash nếu column chưa tồn tại
                int dueIndex = cursor.getColumnIndex(COLUMN_DUE_DATE);
                int completedIndex = cursor.getColumnIndex(COLUMN_COMPLETED);
                if (dueIndex != -1) {
                    dueDate = cursor.getString(dueIndex);
                }
                if (completedIndex != -1) {
                    completed = cursor.getInt(completedIndex) == 1;
                }

                todos.add(new ToDo(id, title, desc, contactName, contactPhone, dueDate, completed));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return todos;
    }

    public void updateTodoById(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todo.getTitle());
        values.put(COLUMN_DESCRIPTION, todo.getDescription());
        values.put(COLUMN_CONTACT_NAME, todo.getContactName());
        values.put(COLUMN_CONTACT_PHONE, todo.getContactPhone());
        values.put(COLUMN_DUE_DATE, todo.getDueDate());
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0);
        db.update(TABLE_TODO, values, COLUMN_ID + "=?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }

    public void deleteTodoById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<ToDo> searchTodoByTitle(String keyword) {
        ArrayList<ToDo> todos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO, null, COLUMN_TITLE + " LIKE ?", new String[]{"%" + keyword + "%"}, null, null, COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_NAME));
                String contactPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_PHONE));
                String dueDate = "";
                boolean completed = false;

                int dueIndex = cursor.getColumnIndex(COLUMN_DUE_DATE);
                int completedIndex = cursor.getColumnIndex(COLUMN_COMPLETED);
                if (dueIndex != -1) {
                    dueDate = cursor.getString(dueIndex);
                }
                if (completedIndex != -1) {
                    completed = cursor.getInt(completedIndex) == 1;
                }

                todos.add(new ToDo(id, title, desc, contactName, contactPhone, dueDate, completed));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return todos;
    }
}
