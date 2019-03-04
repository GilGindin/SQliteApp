package com.e.sqliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
    }

    public void buttonAdd_onClick(View view) {

        String name = editTextName.getText().toString();
        double price = Double.parseDouble(editTextPrice.getText().toString());

        Product product = new Product(name, price);
        Intent intent = new Intent();
        intent.putExtra("product", product);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void buttonCancel_onClick(View view) {
        finish();
    }


}
