package com.example.xfocus;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AxisDateFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis){
        Integer position = Math.round(value);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");

        switch ((int)Math.round(value)){
            //write your logic here
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "March";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Des";
            default:
                return "Month Error";
        }
    }
}
