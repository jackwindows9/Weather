package com.example.weather_myApplication;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_myApplication.Model.City;
import com.example.weather_myApplication.View.OnMyClickListener;
import com.example.weather_myApplication.View.OnSwipedListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 司维 on 2017/6/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements OnSwipedListener{
    public List<City> list;

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }

    public MyAdapter(List<City> list){
        this.list=list;
    }

    public OnMyClickListener onMyClickListener;

    public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
        this.onMyClickListener = onMyClickListener;
    }

    @Override
    public void onItemDismiss(int position) {
        City city=list.get(position);
        city.delete();
        list= DataSupport.findAll(City.class);
        notifyItemRemoved(position);
        if (position != list.size()) {
            notifyItemRangeChanged(position, list.size() - position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city_view;
        private TextView temperature_view;
        private TextView weather_view;
        private ImageView weather;
        private CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            city_view= (TextView) itemView.findViewById(R.id.cityname);
            temperature_view=(TextView)itemView.findViewById(R.id.temperature);
            weather_view=(TextView)itemView.findViewById(R.id.weather);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            weather= (ImageView) itemView.findViewById(R.id.citylist_image);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.citylist,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                onMyClickListener.onClick(view,position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        City city=list.get(position);
        holder.weather_view.setText(city.getWeather());
        holder.city_view.setText(city.getCity_name());
        holder.temperature_view.setText(city.getTemperature());
        String city_weather="";
        if(city.getDay0_weather().indexOf("转")!=-1){
            city_weather=city.getDay0_weather().substring(0,city.getDay0_weather().indexOf("转"));
        }
        else{
            city_weather=city.getDay0_weather();
        }
        if(("晴").equals(city_weather)){
            holder.cardView.setBackgroundColor(Color.parseColor("#90ceff"));
            holder.weather.setImageResource(R.drawable.main_weather_1);
        }
        if(("阴").equals(city_weather)) {
            holder.cardView.setBackgroundColor(Color.parseColor("#aaaaaa"));
            holder.weather.setImageResource(R.drawable.main_weather_3);
        }
        if(("多云").equals(city_weather)){
            holder.cardView.setBackgroundColor(Color.parseColor("#dadada"));
            holder.weather.setImageResource(R.drawable.main_weather_2);
        }
        if(city_weather.indexOf("雨")+1==city_weather.length()){
            if(city_weather.indexOf("雷")!=-1){
                //雷阵雨
                holder.cardView.setBackgroundColor(Color.parseColor("#1874cd"));
                holder.weather.setImageResource(R.drawable.main_weather_6);
            }
            else{
                //雨
                holder.cardView.setBackgroundColor(Color.parseColor("#1874cd"));
                holder.weather.setImageResource(R.drawable.main_weather_5);
            }
        }
        if(city_weather.indexOf("雪")+1==city_weather.length()){
            if(city_weather.indexOf("夹")!=-1){
                //雨夹雪
                holder.cardView.setBackgroundColor(Color.parseColor("#dadada"));
                holder.weather.setImageResource(R.drawable.main_weather_4);
            }
            else{
                //雪
                holder.cardView.setBackgroundColor(Color.parseColor("#dadada"));
                holder.weather.setImageResource(R.drawable.main_weather_7);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
