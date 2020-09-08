package com.sita.android.sitaproject.Models;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sita.android.sitaproject.R;

import java.util.ArrayList;

public class OccupationRecyclerViewAdapter extends RecyclerView.Adapter<OccupationRecyclerViewAdapter.occupationViewholder> {
 private ArrayList<occupations> occupationsitem;
 private Context c;
 final private ListItemClickListener mOnClickListener;



    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }



    public class  occupationViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  TextView mOccupationTextView;

        public occupationViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }
    public OccupationRecyclerViewAdapter(Context c, ArrayList<occupations> occupationsitem, ListItemClickListener mOnClickListener){
        this.c = c;
        this.occupationsitem = occupationsitem;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public occupationViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.occupationrecyclerviewitem,parent,false);
        occupationViewholder viewholder = new occupationViewholder(v);
        viewholder.mOccupationTextView = (TextView)v.findViewById(R.id.occupationtextview);
        Typeface customfont= Typeface.createFromAsset(v.getContext().getAssets(),"Anteb-SemiLight.otf");
        viewholder.mOccupationTextView.setTypeface(customfont);

        return  viewholder;
    }


    @Override
    public void onBindViewHolder(occupationViewholder holder, int position) {

        occupations current = occupationsitem.get(position);
        holder.mOccupationTextView.setText(current.getOccupation());


    }



    @Override
    public int getItemCount() {
        return occupationsitem.size();
    }



}
