package com.example.qlct;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleView_itemTouchHelper extends ItemTouchHelper.SimpleCallback{
    private ItemTouchHelperListener listener;
    public RecycleView_itemTouchHelper(int dragDirs, int swipeDirs, ItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener!=null)
            listener.onSwipe(viewHolder);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null)
        {
            View fg= ((Notification_Adapter.NotiHolder)viewHolder).linearLayout;
            getDefaultUIUtil().onSelected(fg);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View fg= ((Notification_Adapter.NotiHolder)viewHolder).linearLayout;
        getDefaultUIUtil().onDraw(c,recyclerView,fg,dX,dY,actionState,isCurrentlyActive);

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View fg= ((Notification_Adapter.NotiHolder)viewHolder).linearLayout;
        getDefaultUIUtil().clearView(fg);

    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View fg= ((Notification_Adapter.NotiHolder)viewHolder).linearLayout;
        getDefaultUIUtil().onDrawOver(c,recyclerView,fg,dX,dY,actionState,isCurrentlyActive);

    }
}
