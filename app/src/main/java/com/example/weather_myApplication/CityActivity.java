package com.example.weather_myApplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.weather_myApplication.Model.City;
import com.example.weather_myApplication.Presentor.MyPresentor;
import com.example.weather_myApplication.View.ICityView;
import com.example.weather_myApplication.View.OnMyClickListener;
import com.example.weather_myApplication.View.SimpleItemTouchHelperCallback;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.holo_blue_light;
import static android.R.color.white;
import static com.example.weather_myApplication.R.id.toolbar_city;

public class CityActivity extends AppCompatActivity implements View.OnClickListener,ICityView {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<City> list;
    private Toolbar toolbar;
    private MyPresentor myPresentor;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        myPresentor=new MyPresentor(this);
        toolbar = (Toolbar) findViewById(toolbar_city);
        toolbar.setTitleTextColor(getResources().getColor(white));
        setSupportActionBar(toolbar);
        Window window=CityActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(Build.VERSION.SDK_INT>=23){
            window.setStatusBarColor(getResources().getColor(holo_blue_light));
        }
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list=new ArrayList<City>();
        list= DataSupport.findAll(City.class);
        myAdapter=new MyAdapter(list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnMyClickListener(new OnMyClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(CityActivity.this,MainActivity.class);
                intent.putExtra("data",position);
                Log.d("data","my click position is "+position);
                startActivity(intent);
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab){
            Intent intent=new Intent(CityActivity.this,SearchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        loadDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void loadDatabase(){
        list= DataSupport.findAll(City.class);
        myAdapter.setList(list);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void refreshDatabase(List<City> list) {

    }
}
