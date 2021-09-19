package com.sohaghlab.voicerecoder;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo {

    public  String getTimeAge(long duration) {
        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);

        if (seconds < 60) {
            return "just now";
        } else if (minutes == 1) {
            return "a minutes age";
        } else if (minutes > 1 && minutes < 60) {
            return minutes + " minutes age";
        } else if (hours == 1) {
            return "an hour ago";
        } else if (hours > 1 && hours < 24) {
            return hours + " hours age";
        } else if (days == 1) {
            return "a day ago";
        } else {
            return days + " days ago";
        }

    }
}
