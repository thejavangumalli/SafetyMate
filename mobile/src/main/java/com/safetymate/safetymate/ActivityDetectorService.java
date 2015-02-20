package com.safetymate.safetymate;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by RockerZ on 2/16/15.
 */
public class ActivityDetectorService extends IntentService {
    NotifyWatch notifyWatch;

    public ActivityDetectorService() {
        super("ActivityDetectorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // If the intent contains an update
        if (ActivityRecognitionResult.hasResult(intent)) {

            // Get the update
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

            // Get the most probable activity from the list of activities in the update
            DetectedActivity mostProbableActivity = result.getMostProbableActivity();

         /*   // Get the confidence percentage for the most probable activity
            int confidence = mostProbableActivity.getConfidence();

            // Get the type of activity
            int activityType = mostProbableActivity.getType();
            mostProbableActivity.getVersionCode(); */
                DetectedActivity mode = mostProbableActivity;

                if (mostProbableActivity.getType() == DetectedActivity.ON_FOOT) {
                    DetectedActivity betterActivity = walkingOrRunning(result.getProbableActivities());

                    if (null != betterActivity)
                        mode = betterActivity;
                }
                notifyWatch = new NotifyWatch();
                notifyWatch.sendNotification(mode);
            }
    }

    private DetectedActivity walkingOrRunning(List<DetectedActivity> probableActivities) {
        DetectedActivity myActivity = null;
        int confidence = 0;
        for (DetectedActivity activity : probableActivities) {
            if (activity.getType() != DetectedActivity.RUNNING && activity.getType() != DetectedActivity.WALKING)
                continue;

            if (activity.getConfidence() > confidence)
                myActivity = activity;
        }

        return myActivity;
    }

    /**
     * Map detected activity types to strings
     */
    private String getNameFromType(int activityType) {
        switch (activityType) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "RIDE";
            case DetectedActivity.RUNNING:
                return "RUN";
            case DetectedActivity.WALKING:
                return "walking";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.TILTING:
                return "tilting";
            default:
                return "unknown";
        }


    }
}
