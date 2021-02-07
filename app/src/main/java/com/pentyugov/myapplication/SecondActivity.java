package com.pentyugov.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    public static final String ANIMAL = "com.pentyugov.myapplication";
    private TextView tv_username;
    private Spinner animalsSpinner;
    private Button addAnimalBtn;
    private EditText animalEdit;
    private Button submitBtn;
    private AlertDialog dialogBuilder;
    private EditText animalEditName;
    private List<String> animalsSpinnerArray;
    private ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    public void init() {
        animalsSpinnerArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.animals)));


        addAnimalBtn = findViewById(R.id.second_btn_addAnimal);
        animalEdit = findViewById(R.id.second_ed_animal);
        tv_username = findViewById(R.id.second_tv_username);
        submitBtn = findViewById(R.id.second_btn_submit);

        animalsSpinner = findViewById(R.id.second_sp_animals);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.second_spinner_animals, animalsSpinnerArray);
        spinnerAdapter.setDropDownViewResource(R.layout.second_spinner_animals);
        animalsSpinner.setAdapter(spinnerAdapter);
        animalsSpinner.setOnLongClickListener(v -> {
            showPopupMenu(v);
            return true;
        });
        animalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_username.setText(animalsSpinnerArray.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addAnimalBtn.setOnClickListener(v -> {
            if(animalEdit.getText() != null && !"".contentEquals(animalEdit.getText())) {
                spinnerAdapter.add(animalEdit.getText().toString());
                spinnerAdapter.notifyDataSetChanged();
                animalEdit.setText("");
            }
        });

        submitBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(ANIMAL, animalsSpinner.getSelectedItem().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        createEditNameAlertDialog();
    }

    @SuppressLint("NonConstantResourceId")
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_animal_edit_edit: {
                    animalEditName.setText(animalsSpinner.getSelectedItem().toString());
                    dialogBuilder.show();
                } return true;

                case R.id.menu_animal_edit_delete: {
                    deleteItemFromSpinner();
                } return true;
                default:
                    return false;
            }
        });

        popupMenu.setOnDismissListener(menu -> Toast.makeText(getApplicationContext(), "onDismiss",
                Toast.LENGTH_SHORT).show());
        popupMenu.show();
    }

    private void createEditNameAlertDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_edit_text, null);
        animalEditName = dialogView.findViewById(R.id.edt_comment);

        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Enter new name")
                .setPositiveButton("Submit", (dialog, which) -> {
                    animalsSpinnerArray.set(animalsSpinner.getSelectedItemPosition(), animalEditName.getText().toString());
                    spinnerAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setView(dialogView)
                .create();
    }

    public void deleteItemFromSpinner() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Delete " + animalsSpinner.getSelectedItem().toString() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    animalsSpinnerArray.remove(animalsSpinner.getSelectedItemPosition());
                    spinnerAdapter.notifyDataSetChanged();
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create();
        alert.show();
    }
}
