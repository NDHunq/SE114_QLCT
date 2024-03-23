package com.example.qlct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Notificaiton extends AppCompatActivity {

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
                  Notification_class deleted = notificationList.get(viewHolder.getAdapterPosition());
                  int indexdelte = viewHolder.getAdapterPosition();
                    notificationAdapter.RemoveItem(indexdelte);
                   Snackbar snackbar = Snackbar.make(relativeLayout,nameDeleted+" removed",Snackbar.LENGTH_LONG);
                   snackbar.setAction("Undo", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           notificationAdapter.UndoItem(indexdelte,deleted);
                       }
                   });
                   snackbar.setActionTextColor(Color.YELLOW);
                   snackbar.show();
               }
            }
        });
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
    private void getListnoti()
    {
        notificationList = new ArrayList<>();
        notificationList.add(new Notification_class("header1","content1",R.drawable.budget,1));
        notificationList.add(new Notification_class("header2","content2",R.drawable.budget,0));
        notificationList.add(new Notification_class("header3","content3",R.drawable.budget,1));
        notificationList.add(new Notification_class("header4","content4",R.drawable.budget,1));
        notificationList.add(new Notification_class("header5","content5",R.drawable.budget,1));
    }
}