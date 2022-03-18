package com.example.musek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
    }

    @Override
    public int getItemCount() {
        return laguList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView var_title;
        ImageView var_dot;

        public ViewHolder(View itemView) {
            super(itemView);

            var_title = itemView.findViewById(R.id.id_title);
            var_dot = itemView.findViewById(R.id.id_dot);
        }
    }
}
