package ru.vpcb.map.notes.manager;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public class FAManager {
    private static class Param {
        private static final String CONTENT_IMAGE = "image";
        private static final String CONTENT_VIDEO = "video";
        private static final String CONTENT_TEXT = "text";
        private static final String CONTENT_AUDIO = "audio";
        private static final String USER_NAME = "user_name";
        private static final String LOCATION = "location";
        private static final String NOTE_NAME = "note_name";
    }

    private static class Event {
        private static final String SET_LOCATION = "set_location";
        private static final String SET_NOTE = "set_note";
    }

    private FirebaseAnalytics firebaseAnalytics;


    public FAManager(Context context) {
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    private void logEvent(String id, String name, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    // test!!! works
    public void logEventImage(String id, String name) {
        logEvent(id, name, Param.CONTENT_IMAGE);
    }

    public void logEventVideo(String id, String name) {
        logEvent(id, name, Param.CONTENT_VIDEO);
    }

    public void logEventText(String id, String name) {
        logEvent(id, name, Param.CONTENT_TEXT);
    }

    public void logEventAudio(String id, String name) {
        logEvent(id, name, Param.CONTENT_AUDIO);
    }

    // test!!! works
    public void logEventNote(Note note) {
        String s = String.format(Locale.ENGLISH, "lat:%.3f lon:%.3f",
                note.getLatitude(), note.getLongitude());
        Bundle bundle = new Bundle();
        bundle.putString(Param.NOTE_NAME, note.getText());
        bundle.putString(Param.LOCATION, s);
        firebaseAnalytics.logEvent(Event.SET_NOTE, bundle);
    }

    // test!!! works
    public void logEventLocation(Location location) {
        String s = String.format(Locale.ENGLISH, "lat:%.3f lon:%.3f",
                location.getLatitude(), location.getLongitude());
        Bundle bundle = new Bundle();
        bundle.putString(Param.LOCATION, s);
        firebaseAnalytics.logEvent(Event.SET_LOCATION, bundle);
    }

    // test!!! works
    public void logEventLogin(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(Param.USER_NAME, name);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    public void setUserProperty(String s, String value) {
        if (TextUtils.isEmpty(value)) return;
        firebaseAnalytics.setUserProperty(s, value);
    }

    public void setUserId(String s) {
        firebaseAnalytics.setUserId(s);
    }


}
