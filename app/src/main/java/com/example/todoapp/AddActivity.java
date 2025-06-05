package com.example.todoapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    private static final int REQUEST_CONTACT = 1;

    private EditText titleEdit, descriptionEdit, dueDateEdit, contactText;
    private CheckBox completedCheckbox;

    private String contactName = "";
    private String contactPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Todo");

        titleEdit = findViewById(R.id.title_edit);
        descriptionEdit = findViewById(R.id.description_edit);
        dueDateEdit = findViewById(R.id.due_date_edit);
        contactText = findViewById(R.id.contact_text);
        completedCheckbox = findViewById(R.id.completed_checkbox);

        Button selectContactButton = findViewById(R.id.button_select_contact);
        selectContactButton.setOnClickListener(v -> pickContact());

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveTodo());
    }

    private void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CONTACT);
    }

    private void saveTodo() {
        String title = titleEdit.getText().toString().trim();
        String description = descriptionEdit.getText().toString().trim();
        String dueDate = dueDateEdit.getText().toString().trim();
        boolean completed = completedCheckbox.isChecked();

        if (title.isEmpty() || contactName.isEmpty() || contactPhone.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ToDo todo = new ToDo(title, description, contactName, contactPhone, dueDate, completed);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_todo", todo);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            if (contactUri != null) {
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(contactUri,
                        new String[]{
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                        }, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    contactName = cursor.getString(0);
                    contactPhone = cursor.getString(1);
                    contactText.setText(contactName + " - " + contactPhone);
                    cursor.close();
                }
            }
        }
    }
}
