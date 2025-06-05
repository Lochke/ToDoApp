package com.example.todoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<ToDo> {
    private final Activity context;
    private final ArrayList<ToDo> todos;
    public TodoAdapter(Activity context, ArrayList<ToDo> todos) {
        super(context, R.layout.list_item_todo, todos);
        this.context = context;
        this.todos = todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        }

        ToDo todo = todos.get(position);

        TextView titleView = rowView.findViewById(R.id.item_title);
        TextView contactView = rowView.findViewById(R.id.item_contact);
        TextView dueDateView = rowView.findViewById(R.id.item_due);
        TextView doneView = rowView.findViewById(R.id.item_done);

        titleView.setText(todo.getTitle());

        String contact = (todo.getContactName() != null && !todo.getContactName().isEmpty())
                ? todo.getContactName() + " - " + todo.getContactPhone()
                : "-";
        contactView.setText(contact);

        dueDateView.setText("Due: " + todo.getDueDate());
        doneView.setText(todo.isCompleted() ? "✔ Done" : "⏳ Not done");

        return rowView;
    }
    public void updateData(List<ToDo> newTodos) {
        this.todos.clear();
        this.todos.addAll(newTodos);
        notifyDataSetChanged();
    }
}
