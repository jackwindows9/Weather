package com.example.weather_myApplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_myApplication.Model.City;
import com.example.weather_myApplication.Presentor.MyPresentor;
import com.example.weather_myApplication.View.IMainView;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.holo_blue_light;
import static android.R.color.white;

public class MainActivity extends AppCompatActivity implements IMainView {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private MenuItem refresh;
    private MenuItem city;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private MyPresentor myPresentor;
    private Toolbar toolbar;
    private int backPosition=-1;
    private List<City> list;
    private int lastValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(white));
        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(Build.VERSION.SDK_INT>=23){
            window.setStatusBarColor(getResources().getColor(holo_blue_light));
        }
        setSupportActionBar(toolbar);
        list = new ArrayList<City>();
        myPresentor = new MyPresentor(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //create database and table
        Connector.getDatabase();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset != 0) {
                    if (lastValue >= positionOffsetPixels) {
                        myPresentor.loadFromDatabases(position);
                    } else {
                        myPresentor.loadFromDatabases(position + 1);
                    }
                    lastValue = positionOffsetPixels;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        intent=getIntent();
        backPosition=intent.getIntExtra("data",-1);
        Log.d("data","backPosition is "+backPosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = DataSupport.findAll(City.class);
        //do update here

        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(backPosition==-1){
            myPresentor.refresh(0);
        }
        else{
            myPresentor.refresh(backPosition);
            mViewPager.setCurrentItem(backPosition);
        }
        myPresentor.refreshDatabases(list);
        mSectionsPagerAdapter.notifyDataSetChanged();
        Log.d("data","1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        refresh = menu.findItem(R.id.refresh);
        city = menu.findItem(R.id.city);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            //PlaceholderFragment placeholderFragment= (PlaceholderFragment) getSupportFragmentManager().getFragments().get(4);
            myPresentor.refresh(mViewPager.getCurrentItem());
            return true;
        }
        if (id == R.id.city) {
            Intent intent = new Intent(MainActivity.this, CityActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCityName(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.realtime_cityname.setText(text);
    }

    @Override
    public void setRealtimeWeather(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.realtime_weather.setText(text);
        if(text.indexOf("转")!=-1){
            text=text.substring(0,text.indexOf("转"));
        }
        if(("晴").equals(text)){
            placeholderFragment.main_weather_image.setImageResource(R.drawable.main_1);
        }
        if(("阴").equals(text)) {
            placeholderFragment.main_weather_image.setImageResource(R.drawable.main_3);
        }
        if(("多云").equals(text)){
            placeholderFragment.main_weather_image.setImageResource(R.drawable.main_2);
        }
        if(text.indexOf("雨")+1==text.length()){
            if(text.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.main_weather_image.setImageResource(R.drawable.main_6);
            }
            else{
                //雨
                placeholderFragment.main_weather_image.setImageResource(R.drawable.main_5);
            }
        }
        if(text.indexOf("雪")+1==text.length()){
            if(text.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.main_weather_image.setImageResource(R.drawable.main_4);
            }
            else{
                //雪
                placeholderFragment.main_weather_image.setImageResource(R.drawable.main_7);
            }
        }
    }

    @Override
    public void setRealtimeTemperature(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.realtime_temperature.setText(text+"°");
    }

    @Override
    public void setMaxinum(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.maxinum.setText(text+"°C");
    }

    @Override
    public void setMininum(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.mininum.setText(text);
    }

    @Override
    public void setWindPower(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.wind_power.setText(text);
    }

    @Override
    public void setWindDirection(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.wind_direction.setText(text);
    }

    @Override
    public void setHumitity(String text, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.humitity.setText("湿度 "+text+"%");
    }

    @Override
    public void showError(String error, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        new AlertDialog.Builder(placeholderFragment.getActivity())
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
    public void showError(String error) {
        Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDay0(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day0_date.setText(date);
        placeholderFragment.day0_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day0_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day0_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day0_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day0_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day0_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day0_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day0_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day0_weather.setText(weather);
        placeholderFragment.day0_maxinum.setText(maxinum+"°");
        placeholderFragment.day0_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay1(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day1_date.setText(date);
        placeholderFragment.day1_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day1_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day1_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day1_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day1_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day1_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day1_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day1_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day1_weather.setText(weather);
        placeholderFragment.day1_maxinum.setText(maxinum+"°");
        placeholderFragment.day1_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay2(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day2_date.setText(date);
        placeholderFragment.day2_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day2_image.setImageResource(R.drawable.seven_2);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day2_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day2_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day2_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day2_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day2_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day2_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day2_weather.setText(weather);
        placeholderFragment.day2_maxinum.setText(maxinum+"°");
        placeholderFragment.day2_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay3(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day3_date.setText(date);
        placeholderFragment.day3_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day3_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day3_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day3_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day3_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day3_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day3_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day3_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day3_weather.setText(weather);
        placeholderFragment.day3_maxinum.setText(maxinum+"°");
        placeholderFragment.day3_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay4(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day4_date.setText(date);
        placeholderFragment.day4_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day4_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day4_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day4_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day4_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day4_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day4_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day4_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day4_weather.setText(weather);
        placeholderFragment.day4_maxinum.setText(maxinum+"°");
        placeholderFragment.day4_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay5(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day5_date.setText(date);
        placeholderFragment.day5_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day5_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day5_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day5_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.indexOf("雷")!=-1){
                //雷阵雨
                placeholderFragment.day5_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day5_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day5_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day5_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day5_weather.setText(weather);
        placeholderFragment.day5_maxinum.setText(maxinum+"°");
        placeholderFragment.day5_mininum.setText(mininum+"°");
    }

    @Override
    public void setDay6(String date, String week, String weather, String maxinum, String mininum, int position) {
        PlaceholderFragment placeholderFragment = (PlaceholderFragment) getSupportFragmentManager().getFragments().get(position);
        placeholderFragment.day6_date.setText(date);
        placeholderFragment.day6_week.setText(week);
        if(weather.indexOf("转")!=-1){
            weather=weather.substring(0,weather.indexOf("转"));
        }
        if(("晴").equals(weather)){
            placeholderFragment.day6_image.setImageResource(R.drawable.seven_1);
        }
        if(("阴").equals(weather)) {
            placeholderFragment.day6_image.setImageResource(R.drawable.seven_3);
        }
        if(("多云").equals(weather)){
            placeholderFragment.day6_image.setImageResource(R.drawable.seven_2);
        }
        if(weather.indexOf("雨")+1==weather.length()){
            if(weather.contains("雷")){
                //雷阵雨
                placeholderFragment.day6_image.setImageResource(R.drawable.seven_6);
            }
            else{
                //雨
                placeholderFragment.day6_image.setImageResource(R.drawable.seven_5);
            }
        }
        if(weather.indexOf("雪")+1==weather.length()){
            if(weather.indexOf("夹")!=-1){
                //雨夹雪
                placeholderFragment.day6_image.setImageResource(R.drawable.seven_4);
            }
            else{
                //雪
                placeholderFragment.day6_image.setImageResource(R.drawable.seven_7);
            }
        }
        placeholderFragment.day6_weather.setText(weather);
        placeholderFragment.day6_maxinum.setText(maxinum+"°");
        placeholderFragment.day6_mininum.setText(mininum+"°");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public TextView realtime_cityname;
        public TextView realtime_weather;
        public ImageView main_weather_image;
        public TextView realtime_temperature;
        public TextView maxinum;
        public TextView mininum;
        public TextView wind_power;
        public TextView wind_direction;
        public TextView humitity;
        
        public TextView day0_date;
        public TextView day0_week;
        public TextView day0_weather;
        public ImageView day0_image;
        public TextView day0_maxinum;
        public TextView day0_mininum;
        
        public TextView day1_date;
        public TextView day1_week;
        public TextView day1_weather;
        public ImageView day1_image;
        public TextView day1_maxinum;
        public TextView day1_mininum;
        
        public TextView day2_date;
        public TextView day2_week;
        public TextView day2_weather;
        public ImageView day2_image;
        public TextView day2_maxinum;
        public TextView day2_mininum;
        
        public TextView day3_date;
        public TextView day3_week;
        public TextView day3_weather;
        public ImageView day3_image;
        public TextView day3_maxinum;
        public TextView day3_mininum;
        
        public TextView day4_date;
        public TextView day4_week;
        public TextView day4_weather;
        public ImageView day4_image;
        public TextView day4_maxinum;
        public TextView day4_mininum;
        
        public TextView day5_date;
        public TextView day5_week;
        public TextView day5_weather;
        public ImageView day5_image;
        public TextView day5_maxinum;
        public TextView day5_mininum;
        
        public TextView day6_date;
        public TextView day6_week;
        public TextView day6_weather;
        public ImageView day6_image;
        public TextView day6_maxinum;
        public TextView day6_mininum;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.top_city, container, false);
            realtime_cityname = (TextView) rootView.findViewById(R.id.realtime_cityname);
            realtime_weather = (TextView) rootView.findViewById(R.id.realtime_weather);
            main_weather_image= (ImageView) rootView.findViewById(R.id.main_weather_image);
            realtime_temperature = (TextView) rootView.findViewById(R.id.realtime_temperature);
            maxinum = (TextView) rootView.findViewById(R.id.maxinum);
            mininum = (TextView) rootView.findViewById(R.id.mininum);
            wind_power = (TextView) rootView.findViewById(R.id.wind_power);
            wind_direction = (TextView) rootView.findViewById(R.id.wind_direction);
            humitity = (TextView) rootView.findViewById(R.id.humidity);
            
            day0_date=(TextView) rootView.findViewById(R.id.day0_date);
            day0_week= (TextView) rootView.findViewById(R.id.day0_week);
            day0_weather= (TextView) rootView.findViewById(R.id.day0_weather_text);
            day0_image=(ImageView) rootView.findViewById(R.id.day0_weather);
            day0_maxinum= (TextView) rootView.findViewById(R.id.day0_maxinum);
            day0_mininum= (TextView) rootView.findViewById(R.id.day0_mininum);
            
            day1_date=(TextView) rootView.findViewById(R.id.day1_date);
            day1_week= (TextView) rootView.findViewById(R.id.day1_week);
            day1_weather= (TextView) rootView.findViewById(R.id.day1_weather_text);
            day1_image=(ImageView) rootView.findViewById(R.id.day1_weather);
            day1_maxinum= (TextView) rootView.findViewById(R.id.day1_maxinum);
            day1_mininum= (TextView) rootView.findViewById(R.id.day1_mininum);

            day2_date=(TextView) rootView.findViewById(R.id.day2_date);
            day2_week= (TextView) rootView.findViewById(R.id.day2_week);
            day2_weather= (TextView) rootView.findViewById(R.id.day2_weather_text);
            day2_image=(ImageView) rootView.findViewById(R.id.day2_weather);
            day2_maxinum= (TextView) rootView.findViewById(R.id.day2_maxinum);
            day2_mininum= (TextView) rootView.findViewById(R.id.day2_mininum);

            day3_date=(TextView) rootView.findViewById(R.id.day3_date);
            day3_week= (TextView) rootView.findViewById(R.id.day3_week);
            day3_weather= (TextView) rootView.findViewById(R.id.day3_weather_text);
            day3_image=(ImageView) rootView.findViewById(R.id.day3_weather);
            day3_maxinum= (TextView) rootView.findViewById(R.id.day3_maxinum);
            day3_mininum= (TextView) rootView.findViewById(R.id.day3_mininum);

            day4_date=(TextView) rootView.findViewById(R.id.day4_date);
            day4_week= (TextView) rootView.findViewById(R.id.day4_week);
            day4_weather= (TextView) rootView.findViewById(R.id.day4_weather_text);
            day4_image=(ImageView) rootView.findViewById(R.id.day4_weather);
            day4_maxinum= (TextView) rootView.findViewById(R.id.day4_maxinum);
            day4_mininum= (TextView) rootView.findViewById(R.id.day4_mininum);

            day5_date=(TextView) rootView.findViewById(R.id.day5_date);
            day5_week= (TextView) rootView.findViewById(R.id.day5_week);
            day5_weather= (TextView) rootView.findViewById(R.id.day5_weather_text);
            day5_image=(ImageView) rootView.findViewById(R.id.day5_weather);
            day5_maxinum= (TextView) rootView.findViewById(R.id.day5_maxinum);
            day5_mininum= (TextView) rootView.findViewById(R.id.day5_mininum);

            day6_date=(TextView) rootView.findViewById(R.id.day6_date);
            day6_week= (TextView) rootView.findViewById(R.id.day6_week);
            day6_weather= (TextView) rootView.findViewById(R.id.day6_weather_text);
            day6_image=(ImageView) rootView.findViewById(R.id.day6_weather);
            day6_maxinum= (TextView) rootView.findViewById(R.id.day6_maxinum);
            day6_mininum= (TextView) rootView.findViewById(R.id.day6_mininum);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("data","getItem is done");
            Log.d("data","2");
            return new PlaceholderFragment().newInstance(position);
        }

        @Override
        public int getCount() {
            list = DataSupport.findAll(City.class);
            Log.d("data","getCount is done "+"and the count is"+list.size());
            Log.d("data","3");
            if (list.size() == 0) {
                return 1;
            } else {
                return list.size();
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d("data","5");
            Log.d("data","new position is "+position);
            return super.instantiateItem(container, position);
        }
    }
}
