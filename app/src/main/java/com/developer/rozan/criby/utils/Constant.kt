package com.developer.rozan.criby.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DELAY_1500L = 1500L
const val DELAY_2000L = 2000L

fun showKeyboard(activity: Activity, view: View) {
    val input = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun closeKeyboard(activity: Activity, view: View) {
    val input: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.hideSoftInputFromWindow(view.windowToken, 0)
}

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun showSnackBar(activity: AppCompatActivity, message: String) {
    return Snackbar.make(activity.window.decorView.rootView, message, Snackbar.LENGTH_SHORT).show()
}

fun showToast(activity: AppCompatActivity, message: String) {
    return Toast.makeText(activity.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun formatDate(timestamp: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = sdf.parse(timestamp) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun getDate(): String {
    val sdf = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
    return sdf.format(Date())
}