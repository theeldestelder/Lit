package com.lit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<DeviceItem> devices;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // allows clicks events to be caught
    /*
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    */

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        private Context context;

        ViewHolder(final View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.deviceName);
            //itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                DeviceItem device = devices.get(position);
                Toast.makeText(context, device.getName(), Toast.LENGTH_LONG).show();
            }


            /*
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
            */
        }
    }

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<DeviceItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.devices = data;
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
        DeviceItem device = devices.get(position);

        // add line break between device name and device MAC address
        holder.myTextView.setText(device.getName() + "\n" + device.getMacAddress());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return devices.size();
    }



    // convenience method for getting data at click position
    DeviceItem getItemName(int id) {
        return devices.get(id);
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
