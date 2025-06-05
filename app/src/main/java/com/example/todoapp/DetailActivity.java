package com.example.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_CONTACT = 1001;

    private EditText contactText;
    private ToDo todo;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        EditText titleEdit = findViewById(R.id.title_edit);
        EditText descriptionEdit = findViewById(R.id.description_edit);
        EditText dueDateEdit = findViewById(R.id.due_date_edit);
        contactText = findViewById(R.id.contact_text); // ✅ Ô để chọn contact
        CheckBox completedCheckBox = findViewById(R.id.completed_checkbox);
        Button saveButton = findViewById(R.id.save_button);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        todo = (ToDo) intent.getSerializableExtra("todo");
        position = intent.getIntExtra("position", -1);

        if (todo != null) {
            titleEdit.setText(todo.getTitle());
            descriptionEdit.setText(todo.getDescription());
            dueDateEdit.setText(todo.getDueDate());
            completedCheckBox.setChecked(todo.isCompleted());

            String contactDisplay = todo.getContactName() + " - " + todo.getContactPhone();
            contactText.setText(contactDisplay);
        }

        // ✅ Mở danh bạ khi bấm vào ô liên hệ
        contactText.setOnClickListener(v -> {
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(pickContact, REQUEST_SELECT_CONTACT);
        });

        // ✅ Nút Lưu
        saveButton.setOnClickListener(v -> {
            if (todo == null) return;

            todo.setTitle(titleEdit.getText().toString());
            todo.setDescription(descriptionEdit.getText().toString());
            todo.setDueDate(dueDateEdit.getText().toString());
            todo.setCompleted(completedCheckBox.isChecked());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated_todo", todo);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // ✅ Nhận dữ liệu liên hệ từ danh bạ
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            if (contactUri != null) {
                Cursor cursor = getContentResolver().query(
                        contactUri,
                        null,
                        null,
                        null,
                        null
                );

                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String name = cursor.getString(nameIndex);
                    String phone = cursor.getString(phoneIndex);
                    cursor.close();

                    contactText.setText(name + " - " + phone);
                    if (todo != null) {
                        todo.setContactName(name);
                        todo.setContactPhone(phone);
                    }
                }
            }
        }
    }
}
