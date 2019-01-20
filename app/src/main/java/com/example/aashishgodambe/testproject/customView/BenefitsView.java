package com.example.aashishgodambe.testproject.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aashishgodambe.testproject.R;

/**
 * Created by aashishgodambe on 1/16/19.
 */

public class BenefitsView extends LinearLayout{

    View rootView;
    TextView textView;
    ImageView imageView;
    String text;

    public BenefitsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){
        rootView = inflate(getContext(), R.layout.benefit_view,this);
        textView = rootView.findViewById(R.id.caption);
        imageView = rootView.findViewById(R.id.image);

        if(attrs == null){
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.BenefitsView);
        Drawable image = typedArray.getDrawable(R.styleable.BenefitsView_image);
        text = typedArray.getString(R.styleable.BenefitsView_text);
        imageView.setImageDrawable(image);
        textView.setText(text);
        typedArray.recycle();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        return true;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        event.getText().add(text);
        return true;
    }
}
