package com.dev_bourheem.hadi.Archieve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev_bourheem.hadi.R;

import java.util.ArrayList;


public class ArchRecyclerViewAdapter extends RecyclerView.Adapter<ArchRecyclerViewAdapter.ArchSubRecycler> {

    private ArrayList<ArchexampleItem> mExampleList;
    private ArchRecyclerViewAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnItemDelete(int position);
    }

    void setOnItemClickListener(ArchRecyclerViewAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    static class ArchSubRecycler extends RecyclerView.ViewHolder {
        public TextView idTv;
        public TextView quantity;
        public TextView quantifier;
        public TextView ItemName;
        public TextView ItemPrice;
        public TextView ShopName;
        public TextView dateBought;
        public ArchSubRecycler(View itemView, final ArchRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            idTv = itemView.findViewById( R.id.id);
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

    ArchRecyclerViewAdapter(ArrayList<ArchexampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ArchRecyclerViewAdapter.ArchSubRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ArchRecyclerViewAdapter.ArchSubRecycler(v, mListener);
    }

    @Override
    public void onBindViewHolder(ArchRecyclerViewAdapter.ArchSubRecycler holder, int position) {
        ArchexampleItem currentItem = mExampleList.get(position);

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