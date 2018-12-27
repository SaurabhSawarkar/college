package com.example.project.network.preference;

import android.content.Context;

/**
 * Handles geofence and location preferences
 */

public class GeofenceSharedPreference extends AbstractSharedPreference {

    private static final String BASE_URL_PREF = "geofence_shrared_preference";
    private static final String LOCATION_LIST_KEY = "location_list_key";

    public GeofenceSharedPreference(Context context) {
        super(context, BASE_URL_PREF);
    }
}
