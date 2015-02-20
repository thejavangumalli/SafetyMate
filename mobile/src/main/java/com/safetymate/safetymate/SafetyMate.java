package com.safetymate.safetymate;

import android.content.Context;

import com.google.android.gms.location.DetectedActivity;

public class SafetyMate {
    public static boolean notification;

    public static Context context;

    public static Float heartRate;

    public static DetectedActivity userActivity;

    public static ActivityDetector activityDetector;

    public static ActivityDetector getActivityDetector() {
        return activityDetector;
    }

    public static void setActivityDetector(ActivityDetector activityDetector) {
        SafetyMate.activityDetector = activityDetector;
    }

    public static DetectedActivity getUserActivity() {
        return userActivity;
    }

    public static void setUserActivity(DetectedActivity userActivity) {
        SafetyMate.userActivity = userActivity;
    }


    public static Float getHeartRate() {
        return heartRate;
    }

    public static void setHeartRate(Float heartRate) {
        SafetyMate.heartRate = heartRate;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SafetyMate.context = context;
    }


    public static boolean isNotificationActive() {
        return notification;
    }

    public static void setNotificationState(boolean notificationState) {
        notification = notificationState;
    }
}
