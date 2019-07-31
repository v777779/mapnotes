package ru.vpcb.map.notes.ext;

import android.util.Patterns;

import java.util.regex.Matcher;

public class ValidationExt {

    public static boolean isValidEmail(String s) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(s);
        return matcher.matches();
    }
}
