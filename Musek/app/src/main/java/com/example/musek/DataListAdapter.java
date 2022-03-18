package com.example.musek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {

    ArrayList<DataModel> laguList;
    Context context;

    public DataListAdapter(ArrayList<DataModel> laguList, Context context) {
        this.laguList = laguList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new DataListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataListAdapter.ViewHolder holder, int position) {
        DataModel laguData = laguList.get(position);
        holder.var_title.setText(laguData.getTitle());
        holder.var_duration.setText(convertToMS(laguData.getDuration()));

        if (LaguPlayer.currentIndex == position) {
            holder.var_title.setTextColor(Color.parseColor("#29A5FF"));
            holder.var_title.setHorizontallyScrolling(true);
            holder.var_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.var_dot.setVisibility(View.VISIBLE);
        } else {
            holder.var_title.setTextColor(Color.parseColor("#000000"));
            holder.var_dot.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // navigate to detail
                LaguPlayer.getInstance().reset();
                LaguPlayer.currentIndex = position;

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("list", laguList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return laguList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView var_title, var_duration;
        ImageView var_dot;

        public ViewHolder(View itemView) {
            super(itemView);

            var_title = itemView.findViewById(R.id.id_title);
            var_duration = itemView.findViewById(R.id.id_duration);
            var_dot = itemView.findViewById(R.id.id_dot);

            var_title.setSelected(true);
        }
    }

    public static String convertToMS(String duration) {
        Long ms = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(ms) % TimeUnit.MINUTES.toSeconds(1));
    }
}
