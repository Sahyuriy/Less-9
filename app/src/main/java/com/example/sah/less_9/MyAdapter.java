package com.example.sah.less_9;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> data;



    public MyAdapter (ArrayList<String> strings){
        data = strings;


    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(data.get(position));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);


        }
    }



}