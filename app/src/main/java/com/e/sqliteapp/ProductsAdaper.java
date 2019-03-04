package com.e.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ProductsAdaper extends SimpleCursorAdapter {

    private OnItemClickedListener listener;

    public ProductsAdaper(Context context, Cursor productsursor, OnItemClickedListener listener) {

        super(context, R.layout.item_product, productsursor, new String[]{"_id", "name", "price"}, new int[]{R.id.textViewID, R.id.textViewName, R.id.textViewPrice}, 0);
        this.listener = listener;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
        Button buttonDelete = view.findViewById(R.id.buttonDelete);
        TextView textView = view.findViewById(R.id.textViewID);
        final int id = Integer.parseInt(textView.getText().toString());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.updateProduct(id);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteProduct(id);
            }
        });
    }

    public interface OnItemClickedListener {
        void updateProduct(int id);
        void deleteProduct(int id);
    }
}
