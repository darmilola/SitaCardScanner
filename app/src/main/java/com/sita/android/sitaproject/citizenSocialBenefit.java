package com.sita.android.sitaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.sita.android.sitaproject.Models.Cell;
import com.sita.android.sitaproject.Models.Columnheader;
import com.sita.android.sitaproject.Models.rowheader;
import com.sita.android.sitaproject.Models.socialbenefittableadapter;

import java.util.ArrayList;

public class citizenSocialBenefit extends AppCompatActivity {
    TextView socialBenefitTextView;
    ArrayList<Columnheader> columnheaders = new ArrayList<>();
    ArrayList<rowheader> rowheaders = new ArrayList<>();
    ArrayList<Cell> cells = new ArrayList<>();
    TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_social_benefit);
        tableView = findViewById(R.id.content_container);
        socialbenefittableadapter socialbenefittableadapter = new socialbenefittableadapter(this);


        tableView.setAdapter(socialbenefittableadapter);
      for(int  i =0; i<10; i++) {
          Columnheader columnheader = new Columnheader();
          rowheader rowheader = new rowheader();
          Cell cell = new Cell();
          columnheader.setData("column1");
          rowheader.setData("row1");
          cell.setData("cell1");
          columnheaders.add(columnheader);
          rowheaders.add(rowheader);
          cells.add(cell);
      }
      socialbenefittableadapter.setAllItems(columnheaders,rowheaders,cells);


    }
}
