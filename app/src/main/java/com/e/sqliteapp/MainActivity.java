package com.e.sqliteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ProductsAdaper.OnItemClickedListener {

    private final int REQUEST_CODE_ADD_PRODUCT = 1;
    private final int REQUEST_CODE_UPDATE_PRODUCT = 2;
    private ProductsAdaper productsAdaper;
    private ListView listViewProducts;
    private TextView textViewCount;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewProducts = findViewById(R.id.listViewProduct);
        textViewCount = findViewById(R.id.textViewCount);

        dataBase = new DataBase(this);
        productsAdaper = new ProductsAdaper(this, dataBase.getProductsCursor(), this);
        listViewProducts.setAdapter(productsAdaper);
        displayProductCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void menuItemAddProduct_onClick(MenuItem item) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);
    }

    public void menuItemExitPorduct_onClick(MenuItem item) {
        finish();
    }

    @Override
    public void updateProduct(int id) {
        Intent intent = new Intent(this, UpdateProductActivity.class);
        intent.putExtra("product", dataBase.getOneProduct(id));
        startActivityForResult(intent, REQUEST_CODE_UPDATE_PRODUCT);
    }

    @Override
    public void deleteProduct(final int id) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.delete)
                .setTitle("Confirm Delete ")
                .setMessage("Delete " + dataBase.getProductName(id) + "$")
                .setCancelable(false)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.deleteProduct(id);
                        productsAdaper.swapCursor(dataBase.getProductsCursor());
                        displayProductCount();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode != RESULT_CANCELED && data != null) {

            Product product = (Product) data.getSerializableExtra("product");

            dataBase.addProduct(product);

            productsAdaper.swapCursor(dataBase.getProductsCursor());
            displayProductCount();
        } else if (requestCode == REQUEST_CODE_UPDATE_PRODUCT && resultCode == RESULT_OK) {
            Product product = (Product) data.getSerializableExtra("product");

        }
    }

    private void displayProductCount() {
        int count = productsAdaper.getCount();
        if (count == 0) {
            textViewCount.setText("No Products");
        } else if (count == 1) {
            textViewCount.setText("One Product");
        } else {
            textViewCount.setText(count + "Products");
        }
    }
}
