package com.example.qlct.Notification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.API_Entity.DeleteNoti;
import com.example.qlct.API_Entity.GetAllNotificationEntity;
import com.example.qlct.API_Utils.NotificationAPIUntil;
import com.example.qlct.ItemTouchHelperListener;
import com.example.qlct.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;

public class Notificaiton_activity extends AppCompatActivity {
    ArrayList<String> listxoa = new ArrayList<>();

    private RecyclerView recyclerView;
    private Notification_Adapter notificationAdapter;
    private ArrayList<Notification_class> notificationList;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notificaiton);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
        relativeLayout = this.findViewById(R.id.main);
        recyclerView = this.findViewById(R.id.rcv_notic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getListnoti();
        notificationAdapter = new Notification_Adapter(notificationList);
        recyclerView.setAdapter(notificationAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        ItemTouchHelper.SimpleCallback simpleCallback = new RecycleView_itemTouchHelper(0,ItemTouchHelper.LEFT, new ItemTouchHelperListener() {
            @Override
            public void onSwipe(RecyclerView.ViewHolder viewHolder) {
                if(viewHolder instanceof Notification_Adapter.NotiHolder)
                {
                    String nameDeleted = notificationList.get(viewHolder.getAdapterPosition()).getHeader();
                    String id= notificationList.get(viewHolder.getAdapterPosition()).getId();

                    listxoa.add(id);
                    Notification_class deleted = notificationList.get(viewHolder.getAdapterPosition());
                    int indexdelte = viewHolder.getAdapterPosition();
                    notificationAdapter.RemoveItem(indexdelte);

                    Snackbar snackbar = Snackbar.make(relativeLayout,nameDeleted+" removed",Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notificationAdapter.UndoItem(indexdelte,deleted);
                            listxoa.remove(id);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        });
        ConstraintLayout clear = findViewById(R.id.clearall);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Notificaiton_activity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to clear all notifications?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for(int i = 0; i < notificationList.size(); i++) {
                                    listxoa.add(notificationList.get(i).getId());
                                }
                                for (String id : listxoa) {
                                    Log.d("idnoti", id);
                                }

                                for(int i = notificationList.size() - 1; i >= 0; i--) {
                                    notificationAdapter.RemoveItem(i);
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
    private void getListnoti()
    {
        notificationList  = new ArrayList<>();

        ArrayList<GetAllNotificationEntity> parseAPIList = new NotificationAPIUntil().getAllNotificationEntities();
        //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
        if(parseAPIList.size()>0)
        {
            for (GetAllNotificationEntity item : parseAPIList) {

                {
                    try {
                        notificationList.add(new Notification_class(item.getId(), item.getNotification().getTitle(), item.getNotification().getBody(),item.notification.getImageUrl(),1,item.getDate()));
                    } catch (Exception e) {
                        e.printStackTrace();


                    }


                }
            }
        }
        else
        {

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


        NotificationAPIUntil notificationAPIUntil = new NotificationAPIUntil();
        for(String id : listxoa) {
            Log.d("idnoti",id);
            DeleteNoti deleteNoti = new DeleteNoti();
            deleteNoti.notificationId = id;
            notificationAPIUntil.deleteNotification(deleteNoti);
        }
    }
}