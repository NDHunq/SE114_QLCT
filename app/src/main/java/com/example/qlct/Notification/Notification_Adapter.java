package com.example.qlct.Notification;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.NotiHolder>{

    private ArrayList<Notification_class> notificationList;
    public Notification_Adapter(ArrayList<Notification_class> notificationList) {
        this.notificationList = notificationList;}
    @NonNull

    @Override
    public NotiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.thongbaoitem,parent,false);
        return new NotiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiHolder holder, int position) {
        Notification_class notification = notificationList.get(position);
        if(notification==null)
            return;
        holder.tvheader.setText(notification.getHeader());
        holder.tvcontent.setText(notification.getContent());
        String url=notification.getImageResId();
        Picasso.get().load(url).into( holder.img);

        holder.tvdate.setText(notification.getDate());

//        if (notification.getSeen() == 1) {
//            holder.tvheader.setTypeface(null, Typeface.BOLD);
//        } else {
//            holder.tvheader.setTypeface(null, Typeface.NORMAL);
//        }
//        if (notification.getSeen() == 1) {
//            holder.seenIcon.setVisibility(View.VISIBLE);
//        } else {
//            holder.seenIcon.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        if(notificationList!=null)
            return notificationList.size();
        return 0;

    }

    public Notification_class getItem(int i) {
        return notificationList.get(i);
    }

    public class NotiHolder extends RecyclerView.ViewHolder {

        TextView tvheader;
        TextView tvcontent;
        ImageView img;
        LinearLayout seenIcon;
        LinearLayout linearLayout;
        TextView tvdate;

        public NotiHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.notiimage);
            tvheader = itemView.findViewById(R.id.notiheader);
            tvcontent = itemView.findViewById(R.id.noticontent);

            linearLayout = itemView.findViewById(R.id.foreGround);

            tvdate = itemView.findViewById(R.id.ngaythang);

        }
    }
    public void RemoveItem(int position)
    {
        notificationList.remove(position);
        notifyItemRemoved(position);
    }
    public void UndoItem(int position, Notification_class deleted)
    {
        notificationList.add(position,deleted);
        notifyItemInserted(position);
    }
}