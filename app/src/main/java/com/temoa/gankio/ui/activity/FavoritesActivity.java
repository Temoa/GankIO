package com.temoa.gankio.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.temoa.gankio.R;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.db.DBHelper;
import com.temoa.gankio.db.DBTools;
import com.temoa.gankio.ui.adapter.RecyclerAdapter;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fav_activity_toolbar);
        toolbar.setTitle(R.string.collect);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initRecycler();
        getDataFromDB();
    }

    private void initRecycler() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.fav_activity_rv);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecyclerAdapter(this, recycler, null);
        adapter.isTypeReplaceAuthor(true);
        adapter.addItemClickListener(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, NewGankData.Results data, int position) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(data.getUrl()));
                startActivity(intent);
            }
        });
        adapter.addItemLongClickListener(new RecyclerAdapter.ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, NewGankData.Results data, int position) {
                createPopupMenu(v, data.getDesc());
            }
        });
        recycler.setAdapter(adapter);
    }

    private void getDataFromDB() {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<NewGankData.Results> dataList = DBTools.queryAll(dbHelper);
        adapter.setNewData(dataList);
    }

    private void deleteDataInDB(String desc) {
        DBHelper dbHelper = new DBHelper(this);
        DBTools.deleteByDesc(dbHelper, desc);
    }

    private void createPopupMenu(View v, final String desc) {
        final PopupMenu menu = new PopupMenu(this, v);
        menu.getMenuInflater().inflate(R.menu.fav_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteDataInDB(desc);
                menu.dismiss();
                getDataFromDB();
                return true;
            }
        });
        menu.show();
    }
}
