package com.dev_bourheem.hadi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<exampleitem> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void OnItemClick(int position);
        void OnItemDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView idTv;
        public TextView quantity;
        public TextView quantifier;
        public TextView ItemName;
        public TextView ItemPrice;
        public TextView ShopName;
        public TextView dateBought;
        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            idTv = itemView.findViewById(R.id.id);
            quantity = itemView.findViewById(R.id.quantityCV);
            quantifier = itemView.findViewById(R.id.quantifierCv);
            ItemName = itemView.findViewById(R.id.ItemNameCV);
            ItemPrice = itemView.findViewById(R.id.PriceCV);
            ShopName = itemView.findViewById(R.id.molhanotCV);
            dateBought = itemView.findViewById(R.id.dateCV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION);{
                            listener.OnItemClick(position);
                        }

                    }
                }
            });

        }
    }

    public ExampleAdapter(ArrayList<exampleitem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        exampleitem currentItem = mExampleList.get(position);

        holder.idTv.setText(currentItem.getIdno());
        holder.quantity.setText(currentItem.getQuantity());
        holder.quantifier.setText(currentItem.getQuantifier());
        holder.ItemName.setText(currentItem.getItemName());
        holder.ItemPrice.setText(currentItem.getItemPrice());
        holder.ShopName.setText(currentItem.getShopName());
        holder.dateBought.setText(currentItem.getDateBought());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
