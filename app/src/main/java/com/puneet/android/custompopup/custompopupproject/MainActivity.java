package com.puneet.android.custompopup.custompopupproject;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View customPopupLayout = LayoutInflater.from(this).inflate(R.layout.layout_popupmenu, null);
        findViewById(R.id.sender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View sender) {

                final CustomPopupWindow popupWindow = new CustomPopupWindow(customPopupLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(customPopupLayout);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                //((CustomPopupWindow) popupWindow).setPointerBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_announcement_black_24dp), context);
                ((CustomPopupWindow) popupWindow).show(sender, CustomPopupWindow.ANCHOR.AUTO);
            }
        });
    }
}
