package com.nashorn.mac.gameclient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.SkuDetails;

import java.util.ArrayList;

public class InAppPurchaseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SkuDetails> products;
    private InAppPurchaseActivity activity;

    public InAppPurchaseAdapter(Activity activity) {
        this.activity = (InAppPurchaseActivity) activity;
        this.context = activity;
        this.products = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final SkuDetails sku = products.get(position);
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.store_item_list, viewGroup, false);
            holder = new ViewHolder();
            holder.ivIcon = view.findViewById(R.id.iv_icon);
            holder.tvLabel = view.findViewById(R.id.tv_label);
            holder.tvPrice = view.findViewById(R.id.tv_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvLabel.setText(sku.title.replaceAll("\\(.*\\)", ""));
        holder.tvPrice.setText(sku.priceText);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.purchaseProduct(sku.productId);
            }
        });

        return view;
    }


    public void update(ArrayList<SkuDetails> products) {
        if (products != null) {
            this.products = products;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        public ImageView ivIcon;
        public TextView tvLabel, tvPrice;
    }
}
