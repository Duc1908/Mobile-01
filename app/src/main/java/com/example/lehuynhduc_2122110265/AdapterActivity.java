package com.example.lehuynhduc_2122110265;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterActivity extends BaseAdapter {

    private Context context;
    private ArrayList<Product> productList;
    private LayoutInflater inflater;

    public AdapterActivity(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu
        Product product = productList.get(position);
        holder.imgProduct.setImageResource(product.getImage());
        holder.tvName.setText(product.getName());

        // Format giá sang VND
        String priceFormatted = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(product.getPrice());
        holder.tvPrice.setText(priceFormatted);

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice;
    }
}
