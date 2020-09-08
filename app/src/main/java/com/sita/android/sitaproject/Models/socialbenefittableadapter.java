package com.sita.android.sitaproject.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.sita.android.sitaproject.R;

public class socialbenefittableadapter extends AbstractTableAdapter {

    Context context;
    public socialbenefittableadapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    public class cellviewholder extends AbstractViewHolder{

        public TextView cell_textview;
        public cellviewholder(View itemView) {
            super(itemView);
            cell_textview = itemView.findViewById(R.id.cell_data);
        }
    }
    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.social_benefit_cell_item,parent,false);

        return new cellviewholder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
          Cell cell = (Cell)cellItemModel;
          cellviewholder cellviewholder = (socialbenefittableadapter.cellviewholder)holder;
          cellviewholder.cell_textview.setText(cell.getData().toString());

    }

    public class mycolumnheaderviewholder extends AbstractViewHolder{

        TextView column_header_textview;
        public mycolumnheaderviewholder(View itemView) {
            super(itemView);
            column_header_textview = (TextView)itemView.findViewById(R.id.cell_data);
        }
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.social_benefit_column_header_item,parent,false);
        return new mycolumnheaderviewholder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
              Columnheader columnheader = (Columnheader)columnHeaderItemModel;
              mycolumnheaderviewholder mycolumnheaderviewholder = (socialbenefittableadapter.mycolumnheaderviewholder)holder;
              mycolumnheaderviewholder.column_header_textview.setText(columnheader.getData().toString());
    }


    public class rowheaderviewholder extends AbstractViewHolder{
       TextView cell;
        public rowheaderviewholder(View itemView) {
            super(itemView);
            cell = itemView.findViewById(R.id.cell_data);
        }
    }
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context).inflate(R.layout.social_benefit_rowheader_viewholder,parent,false);
        return new rowheaderviewholder(layout);

    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {

        rowheader rowheader = (com.sita.android.sitaproject.Models.rowheader)rowHeaderItemModel;
        rowheaderviewholder rowheaderviewholder = (socialbenefittableadapter.rowheaderviewholder)holder;
        rowheaderviewholder.cell.setText(rowheader.getData().toString());
    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(context).inflate(R.layout.social_benefit_corner_layout,null);
    }


}
