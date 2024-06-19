package com.developer.rozan.criby.data.local.sharedpref

import android.content.Context
import android.content.SharedPreferences

class UserPref(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        this.sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

//    fun loggedIn() {
//        this.sharedPreferences.edit().putBoolean(IS_LOGIN, true).apply()
//    }
//
//    fun isLogin(): Boolean {
//        return this.sharedPreferences.getBoolean(IS_LOGIN, false)
//    }
//
//    fun logout() {
//        this.sharedPreferences.edit().clear().apply()
//    }
//
//    fun saveUser(name: String) {
//        this.sharedPreferences.edit().putString(PREF_NAME, name).apply()
//    }

    fun getUser(): String {
        return sharedPreferences.getString(PREF_NAME, null).toString()
    }

    companion object {

        private const val SHARED_PREFERENCES_NAME = "USER_PREFERENCES"

        private const val IS_LOGIN = "IS_LOGIN"

        private const val PREF_NAME = "PREF_NAME"
        private const val PREF_USER_ID = "PREF_USER_ID"
        private const val PREF_TOKEN = "PREF_TOKEN"

        lateinit var init: UserPref
    }
}