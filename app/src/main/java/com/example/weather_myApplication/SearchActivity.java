package com.example.weather_myApplication;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_myApplication.Model.City;
import com.example.weather_myApplication.Presentor.MyPresentor;
import com.example.weather_myApplication.View.ISearchView;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.R.color.holo_blue_light;
import static android.R.color.white;
import static com.example.weather_myApplication.R.id.toolbar_search;
import static org.litepal.LitePalApplication.getContext;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ISearchView {
    private EditText editText;
    private ImageView imageView;
    private TextView textView;
    private MyPresentor myPresentor;
    private City city;
    private Toolbar toolbar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(toolbar_search);
        toolbar.setTitleTextColor(getResources().getColor(white));
        setSupportActionBar(toolbar);
        Window window = SearchActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(Build.VERSION.SDK_INT>=23){
            window.setStatusBarColor(getResources().getColor(holo_blue_light));
        }
        editText = (EditText) findViewById(R.id.edit_cityname);
        imageView = (ImageView) findViewById(R.id.search_city);
        textView = (TextView) findViewById(R.id.show_city);
        view = findViewById(R.id.add);
        view.setVisibility(View.INVISIBLE);
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        myPresentor = new MyPresentor(this);
        textView.setClickable(false);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){
                    View view=new View(getContext());
                    view.setId(R.id.search_city);
                    onClick(view);
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_city) {
            String cityname = editText.getText().toString();
            List<City> list = DataSupport.findAll(City.class);
            boolean isExist = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCity_name().equals(cityname)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("该城市已存在与列表中")
                            .setInverseBackgroundForced(true)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                    isExist = true;
                }
            }
            if (!isExist) {
                myPresentor.getCity(cityname);
            }
        }
        if (v.getId() == R.id.show_city) {
            this.city.save();
            finish();
        }
    }

    @Override
    public void setCity() {
        textView.setText(editText.getText().toString());
    }

    @Override
    public void getCityInfo(City city) {
        this.city = city;
    }

    @Override
    public void showError(String error) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error)
                .setInverseBackgroundForced(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void changeClickState() {
        textView.setClickable(true);
        view.setVisibility(View.VISIBLE);
    }


}
