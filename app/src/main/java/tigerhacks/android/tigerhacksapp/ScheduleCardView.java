package tigerhacks.android.tigerhacksapp;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Conno on 9/15/2018.
 */

public class ScheduleCardView extends CardView {
    private int clickCount = 0;
    private TextView titleView, locationView, timeView;

    public ScheduleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();
        initializeChildren();
    }

    //this function finds the appropriate views using android:contentDescription tags
    private void initializeChildren()
    {

        ArrayList<View> outputViews = new ArrayList<>();
        this.findViewsWithText(outputViews, "title", FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        titleView = (TextView)outputViews.get(0);

        outputViews = new ArrayList<>();
        this.findViewsWithText(outputViews, "location", FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        locationView = (TextView)outputViews.get(0);

        outputViews = new ArrayList<>();
        this.findViewsWithText(outputViews, "time", FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        timeView = (TextView)outputViews.get(0);


        /*
        outputViews.clear();
        outputViews = new ArrayList<>();
        this.findViewsWithText(outputViews, "description", FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        descView = (TextView)outputViews.get(0);
        descView.setVisibility(View.GONE);
        */


    }

    public void onClickAction(final View rootView)
    {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(clickCount++ % 2 == 0) //if handles expansion of card; else handles collapse
                {
                    //onexpand
                }
                else
                {
                    //oncollapse
                }
            }
        });
    }

    //the following functions are setters for various parts of the prize card
    public void setTitle(String s) {
        titleView.setText(s);
    }
    public void setLocation(String s){
        locationView.setText(s);
    }
    public void setTime(String s)
    {
        String hours, minutes, full, ampm;
        Pattern searchPattern = Pattern.compile("T(\\d{2}):(\\d{2})");
        Matcher matcher = searchPattern.matcher(s);
        if(matcher.find()){
            hours = matcher.group(1);
            if(Integer.parseInt(hours) == 0)
            {
                hours = "12";
                ampm = "AM";
            }
            else if(Integer.parseInt(hours) >= 12)
            {
                hours = Integer.toString(Integer.parseInt(hours) - 12);

                ampm = "PM";
            }
            else
            {
                ampm = "AM";
            }

            minutes = matcher.group(2);
            full = hours + ":" + minutes + " " + ampm;
        }
        else
        {
            hours = "00";
            minutes = "00";
            ampm = "PM";
            full = hours + ":" + minutes + " " + ampm;
        }
        timeView.setText(full);
    }
}