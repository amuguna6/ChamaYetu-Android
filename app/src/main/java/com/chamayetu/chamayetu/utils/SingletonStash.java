package com.chamayetu.chamayetu.utils;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils
 * Created by lusinabrian on 14/10/16.
 * Description: Contains methods that are used all over the application
 * Simply override the methods
 */

public class SingletonStash {

    public static void showToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.INFO);

    }
}
