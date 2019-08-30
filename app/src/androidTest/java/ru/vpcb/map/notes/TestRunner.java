package ru.vpcb.map.notes;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.test.runner.AndroidJUnitRunner;

public class TestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, TestMainApp.class.getName(), context);
    }
}
