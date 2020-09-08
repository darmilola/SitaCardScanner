package com.sita.android.sitaproject.Models;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sita.android.sitaproject.R;

import java.util.ArrayList;

public class localgovernmentadapter extends RecyclerView.Adapter<localgovernmentadapter.Viewholder> {
    private ArrayList<localgovernmentmodel> localgovernmentmodelArrayList;
    private Context c;
    final private ListItemClickListener mOnClickListener;

    public localgovernmentadapter(ArrayList<localgovernmentmodel> localgovernmentmodelArrayList, Context c, ListItemClickListener mOnClickListener) {
        this.localgovernmentmodelArrayList = localgovernmentmodelArrayList;
        this.c = c;
        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public localgovernmentadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.localgovernmentitem,parent,false);
        return  new Viewholder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull localgovernmentadapter.Viewholder holder, int position) {
          holder.localGovernment.setText(localgovernmentmodelArrayList.get(position).getLocalGovernmentName());
    }

    @Override
    public int getItemCount() {
        return localgovernmentmodelArrayList.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView localGovernment;
        public Viewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Typeface customfont= Typeface.createFromAsset(itemView.getContext().getAssets(),"Anteb-SemiLight.otf");
            localGovernment = itemView.findViewById(R.id.localgovermenttextview);
            localGovernment.setTypeface(customfont);
        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
