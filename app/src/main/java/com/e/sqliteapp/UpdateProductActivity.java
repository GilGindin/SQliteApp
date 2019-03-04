package com.e.sqliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_product);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        editTextName.setText(product.getName());
        editTextPrice.setText("" + product.getPrice());
    }

    public void buttonUpdate_onClick(View view) {

        product.setName(editTextName.getText().toString());
        product.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
        Intent intent = new Intent();
        intent.putExtra("product", product);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void buttonCancel_onClick(View view) {
        finish();
    }


}
