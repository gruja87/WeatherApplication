package com.example.dare.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dare.weather.R;
import com.example.dare.weather.model.Forecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<Forecast> mList;
    private LayoutInflater mInflater;

    public ForecastAdapter(Context mContext, ArrayList<Forecast> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = mInflater.inflate(R.layout.forecast,parent,false);
        return new MyHolder(row);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Forecast data = mList.get(position);
        holder.temp_max.setText(data.getHigh());
        holder.temp_min.setText(data.getLow());
        holder.day.setText(data.getDay());
        holder.description.setText(data.getText());
        holder.date.setText(data.getDate().substring(0,6));
        int resource = mContext.getResources().getIdentifier("drawable/icon_" + data.getCode(), null, mContext.getPackageName());
        Picasso.with(mContext).load(resource).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView temp_max,temp_min, day,description,date;
        public ImageView image;

        public MyHolder(View itemView) {
            super(itemView);

            temp_max = (TextView) itemView.findViewById(R.id.teperature_max);
            temp_min = (TextView) itemView.findViewById(R.id.teperature_min);
            day = (TextView) itemView.findViewById(R.id.day_forecast);
            date = (TextView) itemView.findViewById(R.id.date_forecast);
            description = (TextView) itemView.findViewById(R.id.description_forecast);
            image = (ImageView) itemView.findViewById(R.id.image_forecast);
        }
    }
}
