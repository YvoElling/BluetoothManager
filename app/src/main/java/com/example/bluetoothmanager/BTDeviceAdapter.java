package com.example.bluetoothmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class BTDeviceAdapter extends RecyclerView.Adapter<BTDeviceAdapter.ViewHolder> {

    private ArrayList<Device> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    BTDeviceAdapter(Context context, ArrayList<Device> dataset) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = dataset;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.devicelist_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Retrieve name and mac-address of the bluetooth device
        String name = mData.get(position).getName();
        String mac = mData.get(position).getMac();

        //combine them into a placeholder with a newline
        String text = name + '\n' + mac;

        //Set text in textview
        holder.myTextView.setText(text);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.device);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, DeviceOptionsActivity.class);
            context.startActivity(intent);
        }

    }

    // convenience method for getting data at click position
    Device getItem(int id) {
        return mData.get(id);
    }

}
