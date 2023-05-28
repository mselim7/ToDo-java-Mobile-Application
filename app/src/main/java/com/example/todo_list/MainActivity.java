package com.example.todo_list;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoItemClickListener {
    private List<TodoItem> items;
    private TodoAdapter adapter;
    private RecyclerView recyclerView;
    ImageView addNote;
    EditText input;
    public List<TodoItem> items2;
    public TodoAdapter adapter2;
    public RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        input = findViewById(R.id.inp);
        addNote = findViewById(R.id.add_item);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(items, this, this);
        recyclerView.setAdapter(adapter);

        items2= new ArrayList<>();
        recyclerView2 = findViewById(R.id.recycler_views);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new TodoAdapter(items2, this,this);
        recyclerView2.setAdapter(adapter2);

        ImageView btn = findViewById(R.id.clearAllBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });
        Button logout = findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sign_in.class);
                startActivity(intent);
            }
        });


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = input.getText().toString();
                if (txt == null || txt.length() == 0) {
                    String msg = "Please Enter An Item";
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    addItem(txt);
                    String msg = txt + " is Added";
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                    toast.show();
                    adapter.notifyDataSetChanged();
                    input.setText("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        TodoItem item = items.get(position);
        item.setChecked(!item.isChecked());
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onItemLongClick(int position) {
        deleteItem(position);
    }

    public void addItem(String text) {
        TodoItem item = new TodoItem(text);
        items.add(item);
        adapter.notifyItemInserted(items.size() - 1);
    }

    public void addItem2(String text) {
        TodoItem newItem = new TodoItem(text);
        newItem.setChecked2(true); // set isChecked2 to true by default
        items2.add(newItem);
        adapter2.notifyItemInserted(items2.size() - 1);
    }

    public void deleteItem(int position) {
        items.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void clearAll() {
        items.clear();
        adapter.notifyDataSetChanged();
    }


}
