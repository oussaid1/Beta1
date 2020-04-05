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
        public TextView ArchiveID;
        public TextView ArchiveQuantity;
        public TextView ArchiveQuantifier;
        public TextView ArchiveItemName;
        public TextView ArchiveItemPrice;
        public TextView ArchiveShopName;
        public TextView ArchiveDateBought;
        public ArchSubRecycler(View itemView, final ArchRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            ArchiveID = itemView.findViewById( R.id.Archiveid);
            ArchiveQuantity = itemView.findViewById(R.id.ArchivequantityCV);
            ArchiveQuantifier = itemView.findViewById(R.id.ArchivequantifierCv);
            ArchiveItemName = itemView.findViewById(R.id.ArchiveItemNameCV);
            ArchiveItemPrice = itemView.findViewById(R.id.ArchivePriceCV);
            ArchiveShopName = itemView.findViewById(R.id.ArchivemolhanotCV);
            ArchiveDateBought = itemView.findViewById(R.id.ArchivedateCV);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.archivecardview, parent, false);
        return new ArchRecyclerViewAdapter.ArchSubRecycler(v, mListener);
    }

    @Override
    public void onBindViewHolder(ArchRecyclerViewAdapter.ArchSubRecycler holder, int position) {
        ArchexampleItem currentItem = mExampleList.get(position);

        holder.ArchiveID.setText(currentItem.getIdno());
        holder.ArchiveQuantity.setText(currentItem.getQuantity());
        holder.ArchiveQuantifier.setText(currentItem.getQuantifier());
        holder.ArchiveItemName.setText(currentItem.getItemName());
        holder.ArchiveItemPrice.setText(currentItem.getItemPrice());
        holder.ArchiveShopName.setText(currentItem.getShopName());
        holder.ArchiveDateBought.setText(currentItem.getDateBought());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}