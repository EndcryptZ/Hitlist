
package com.endcrypt.hitlist.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a");
    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    private static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat TIME_ONLY_FORMAT = new SimpleDateFormat("hh:mm a");

    /**
     * Formats epoch milliseconds to a full date string
     * Example: "January 15, 2024 at 03:30 PM"
     *
     * @param epochMs The epoch time in milliseconds
     * @return Formatted date string
     */
    public static String formatFullDate(long epochMs) {
        return FULL_DATE_FORMAT.format(new Date(epochMs));
    }

    /**
     * Formats epoch milliseconds to a short date string
     * Example: "01/15/2024 03:30 PM"
     *
     * @param epochMs The epoch time in milliseconds
     * @return Formatted date string
     */
    public static String formatShortDate(long epochMs) {
        return SHORT_DATE_FORMAT.format(new Date(epochMs));
    }

    /**
     * Formats epoch milliseconds to date only
     * Example: "01/15/2024"
     *
     * @param epochMs The epoch time in milliseconds
     * @return Formatted date string
     */
    public static String formatDateOnly(long epochMs) {
        return DATE_ONLY_FORMAT.format(new Date(epochMs));
    }

    /**
     * Formats epoch milliseconds to time only
     * Example: "03:30 PM"
     *
     * @param epochMs The epoch time in milliseconds
     * @return Formatted time string
     */
    public static String formatTimeOnly(long epochMs) {
        return TIME_ONLY_FORMAT.format(new Date(epochMs));
    }


    /**
     * Gets a relative time string from epoch milliseconds
     * Example: "2 hours ago", "in 5 minutes", "just now"
     *
     * @param epochMs The epoch time in milliseconds
     * @return Relative time string
     */
    public static String getRelativeTime(long epochMs) {
        long diff = epochMs - System.currentTimeMillis();
        boolean isFuture = diff > 0;

        // Convert to absolute value for calculations
        diff = Math.abs(diff);

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (diff < 1000) {
            return "just now";
        }

        String timeUnit;
        long value;

        if (days > 0) {
            value = days;
            timeUnit = days == 1 ? "day" : "days";
        } else if (hours > 0) {
            value = hours;
            timeUnit = hours == 1 ? "hour" : "hours";
        } else if (minutes > 0) {
            value = minutes;
            timeUnit = minutes == 1 ? "minute" : "minutes";
        } else {
            value = seconds;
            timeUnit = seconds == 1 ? "second" : "seconds";
        }

        return isFuture ?
                String.format("in %d %s", value, timeUnit) :
                String.format("%d %s ago", value, timeUnit);
    }


}