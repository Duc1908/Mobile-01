package com.example.lehuynhduc_2122110265;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> list;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            row = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        } else {
            row = convertView;
        }

        Product product = list.get(position);

        ImageView imgProduct = row.findViewById(R.id.imgProduct);
        TextView tvName = row.findViewById(R.id.tvName);
        TextView tvPrice = row.findViewById(R.id.tvPrice);
        ImageButton btnAddToCart = row.findViewById(R.id.btnAddToCart);

        // Chọn ảnh fallback dựa trên loại sản phẩm
        int fallbackImage = R.drawable.logocaffe; // mặc định cà phê
        String nameLower = product.getName().toLowerCase();
        if (nameLower.contains("cà phê") || nameLower.contains("coffee")) {
            fallbackImage = R.drawable.logocaffe;
        } else if (nameLower.contains("sinh tố")) {
            fallbackImage = R.drawable.xoai;
        } else if (nameLower.contains("nước ngọt")) {
            fallbackImage = R.drawable.coca;
        } else if (nameLower.contains("nước ép")) {
            fallbackImage = R.drawable.tradao;
        }

        // Load ảnh từ API, nếu lỗi thì hiển thị ảnh fallback
        Glide.with(context)
                .load(product.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background) // ảnh tạm khi load
                .error(fallbackImage) // ảnh fallback khi lỗi
                .into(imgProduct);

        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice());

        btnAddToCart.setOnClickListener(v ->
                Toast.makeText(context, "Đã thêm " + product.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show()
        );

        return row;
    }
}
