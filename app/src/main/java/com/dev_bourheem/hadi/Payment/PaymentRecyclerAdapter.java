package com.dev_bourheem.hadi.Payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev_bourheem.hadi.R;

import java.util.ArrayList;

public class PaymentRecyclerAdapter extends RecyclerView.Adapter<PaymentRecyclerAdapter.PaymentSubRecyclerClass> {

private ArrayList<PaymentItemsClass> mExampleList;
private PaymentRecyclerAdapter.OnItemClickListener mListener;


public interface OnItemClickListener {
    void OnItemClick(int position);

    void OnItemDelete(int position);
}

    void setOnItemClickListener(PaymentRecyclerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

static class PaymentSubRecyclerClass extends RecyclerView.ViewHolder {
    private TextView PaymentId;
    private TextView PaidShopNm;
    private TextView PaymentPrice;
    private TextView PAYDate;
    private PaymentSubRecyclerClass(View itemView, final PaymentRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        PaymentId = itemView.findViewById( R.id.payId);
        PaidShopNm = itemView.findViewById(R.id.payShopName);
        PaymentPrice = itemView.findViewById(R.id.payAmount);
        PAYDate = itemView.findViewById(R.id.payDate);

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

    PaymentRecyclerAdapter(ArrayList<PaymentItemsClass> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public PaymentRecyclerAdapter.PaymentSubRecyclerClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentcardview, parent, false);
        return new PaymentRecyclerAdapter.PaymentSubRecyclerClass(v, mListener);
    }

    @Override
    public void onBindViewHolder(PaymentRecyclerAdapter.PaymentSubRecyclerClass holder, int position) {
        PaymentItemsClass currentItem = mExampleList.get(position);

        holder.PaymentId.setText(currentItem.getId());
        holder.PaidShopNm.setText(currentItem.getShopName());
        holder.PaymentPrice.setText(currentItem.getpaidAmount());
        holder.PAYDate.setText(currentItem.getDatePaid());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}