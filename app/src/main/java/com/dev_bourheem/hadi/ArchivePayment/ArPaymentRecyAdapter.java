package com.dev_bourheem.hadi.ArchivePayment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev_bourheem.hadi.R;

import java.util.ArrayList;

public class ArPaymentRecyAdapter  extends RecyclerView.Adapter<ArPaymentRecyAdapter.ArchPSubRecycler> {

    private ArrayList<ArPaymentExampleItem> mExampleList;
    private ArPaymentRecyAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnItemDelete(int position);
    }

    void setOnItemClickListener(ArPaymentRecyAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    static class ArchPSubRecycler extends RecyclerView.ViewHolder {
        public TextView ArPayID;
        public TextView ArPaidMoney;
        public TextView ArPaidShop;
        public TextView dateDePay;
        public ArchPSubRecycler(View itemView, final ArPaymentRecyAdapter.OnItemClickListener listener) {
            super(itemView);
            ArPayID = itemView.findViewById( R.id.ArPayId);
            ArPaidShop = itemView.findViewById(R.id.arPaidShop);
            ArPaidMoney = itemView.findViewById(R.id.arPaidAmount);
            dateDePay = itemView.findViewById(R.id.arPayDate);

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

    ArPaymentRecyAdapter(ArrayList<ArPaymentExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ArPaymentRecyAdapter.ArchPSubRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arcardviewpay, parent, false);
        return new ArPaymentRecyAdapter.ArchPSubRecycler(v, mListener);
    }

    @Override
    public void onBindViewHolder(ArPaymentRecyAdapter.ArchPSubRecycler holder, int position) {
        ArPaymentExampleItem currentItem = mExampleList.get(position);

        holder.ArPayID.setText(currentItem.getIdno());
        holder.ArPaidMoney.setText(currentItem.getItemPrice());
        holder.ArPaidShop.setText(currentItem.getShopName());
        holder.dateDePay.setText(currentItem.getDateBought());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}