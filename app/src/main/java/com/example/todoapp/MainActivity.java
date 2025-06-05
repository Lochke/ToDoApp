package com.example.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_DETAIL = 2;

    private ArrayList<ToDo> todos = new ArrayList<>();
    private TodoAdapter adapter;
    private TodoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TodoDatabaseHelper(this);
        todos = dbHelper.getAllTodos();

        ListView listView = findViewById(R.id.todo_list);
        adapter = new TodoAdapter(this, todos);
        listView.setAdapter(adapter);

        // ✅ Bấm vào item để xem chi tiết
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ToDo selectedTodo = todos.get(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("todo", selectedTodo);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_DETAIL);
        });

        // ✅ Nhấn giữ item để xóa
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ToDo selectedTodo = todos.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa công việc này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dbHelper.deleteTodoById(selectedTodo.getId());
                        todos.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });

        // ✅ Nút thêm công việc
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK && data != null) {
            ToDo newTodo = (ToDo) data.getSerializableExtra("new_todo");
            dbHelper.insertTodo(newTodo);
            todos.clear();
            todos.addAll(dbHelper.getAllTodos());
            adapter.notifyDataSetChanged();
        }

        else if (requestCode == REQUEST_DETAIL && resultCode == Activity.RESULT_OK && data != null) {
            ToDo updatedTodo = (ToDo) data.getSerializableExtra("updated_todo");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                todos.set(position, updatedTodo);
                dbHelper.updateTodoById(updatedTodo);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
