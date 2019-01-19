package com.example.aashishgodambe.testproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aashish on 10/1/16.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private ClickListener clickListener;
    private RecyclerView mRecyclerView;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        Log.d("RecyclerTouchListener", " Dashboard RecyclerTouchListener constructor invoked");
        this.clickListener = clickListener;
        mRecyclerView = recyclerView;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("RecyclerTouchListener", "onSingleTapUp invoked");
                return true;
            }
        });

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
            return true;
        }

        Log.d("RecyclerTouchListener", "onInterceptTouchEvent invoked" + gestureDetector.onTouchEvent(e) + "" + e);
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d("Main Activity", "onTouchEvent invoked" + e);

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}

