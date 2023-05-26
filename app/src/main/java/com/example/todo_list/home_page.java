package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class home_page extends AppCompatActivity {
    ImageView addNote;
    ListView list;
    ArrayAdapter<String> adapter;
    ArrayList<String> tasks;
    ArrayList<String> completed;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Button logout = (Button) findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_page.this, sign_in.class);
                startActivity(intent);
            }
        });
        addNote = findViewById(R.id.add_item);
        tasks = new ArrayList<>();
        completed = new ArrayList<>();
        input = findViewById(R.id.inp);
        list  = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_item,tasks);
        list.setAdapter(adapter);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String txt = input.getText().toString();
                if(txt == null || txt.length()==0){
                    String msg = "Please Enter An Item";
                    Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tasks.add(txt);
                    String msg = txt+" is Added";
                    Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    toast.show();
                    input.setText("");
                    list.setAdapter(adapter);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item  = tasks.get(position);
                String msg = item+" is Remover";
                Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                toast.show();
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
