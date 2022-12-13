package com.bsk.parentt.util

import android.content.Context
import android.content.SharedPreferences


class SharedPreference(mContext: Context) {

    private val prefMode: Int = 0
    private var sharedPref: SharedPreferences =
        mContext.getSharedPreferences("BSKParent", prefMode)
    val editor: SharedPreferences.Editor = sharedPref.edit()

    //forfirebasetoken
    open fun saveDeviceToken(token: String?): Boolean {
        val editor = sharedPref.edit()
        editor.putString("TAG_TOKEN", token)
        editor.apply()
        return true
    }

    fun getDeviceToken(): String? {
        return sharedPref.getString(
            "TAG_TOKEN",
            null
        )
    }

    // for language pref

    private var PRIVATE_MODE = 0
    private var langPref: SharedPreferences =
        mContext.getSharedPreferences("Language_Pref", PRIVATE_MODE)
    val langeditor: SharedPreferences.Editor = sharedPref.edit()


    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.commit()
    }

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    /*fun getValueString(KEY_NAME: String): String {
        return sharedPref.getString(KEY_NAME, "")
    }*/

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueLong(KEY_NAME: String): Long {
        return sharedPref.getLong(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference(): Boolean {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        val token = fcm_token
        val one_signal_player_id = one_signal_player_id
        editor.clear().commit()
        editor.putString("fcm_token", token)
        editor.putString("one_signal_player_id", one_signal_player_id)
        return editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }

    var prepareTime: Int = 0
        get() {
            return sharedPref.getInt("prepare_time", 0)
        }
        set(value) {
            field = value
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putInt("prepare_time", value)
            editor.commit()

        }

    var isLogin: Boolean?
        get() = sharedPref.getBoolean("isLogin", false)
        set(value) {
            editor.putBoolean("isLogin", value!!).commit()
        }



    var fcm_token: String? = ""
        get() {
            return langPref.getString("fcm_token", "")
        }
        set(value) {
            field = value
            val editor: SharedPreferences.Editor = langPref.edit()
            editor.putString("fcm_token", value)
            editor.commit()

        }
    var one_signal_player_id: String? = ""
        get() {
            return langPref.getString("one_signal_player_id", "")
        }
        set(value) {
            field = value
            val editor: SharedPreferences.Editor = langPref.edit()
            editor.putString("one_signal_player_id", value)
            editor.commit()

        }

    //Okta Details
    var oktaSession: String?
        get() = sharedPref.getString("oktaSession", "")
        set(value) {
            editor.putString("oktaSession", value).commit()
        }

    var oktaId: String?
        get() = sharedPref.getString("oktaId", "")
        set(value) {
            editor.putString("oktaId", value).commit()
        }

    //SignInDetails
    var s_email: String?
        get() = sharedPref.getString("s_email", "")
        set(value) {
            editor.putString("s_email", value).commit()
        }

    var s_pass: String?
        get() = sharedPref.getString("s_pass", "")
        set(value) {
            editor.putString("s_pass", value).commit()
        }


    //User Details
    var p_code: String?
        get() = sharedPref.getString("p_code", "")
        set(value) {
            editor.putString("p_code", value).commit()
        }

    var contact_title: String?
        get() = sharedPref.getString("contact_title", "")
        set(value) {
            editor.putString("contact_title", value).commit()
        }

    var contact_forename: String?
        get() = sharedPref.getString("contact_forename", "")
        set(value) {
            editor.putString("contact_forename", value).commit()
        }

    var contact_surname: String?
        get() = sharedPref.getString("contact_surname", "")
        set(value) {
            editor.putString("contact_surname", value).commit()
        }

    var bill_payer: Boolean
        get() = sharedPref.getBoolean("bill_payer", false)
        set(value) {
            editor.putBoolean("bill_payer", value).commit()
        }

    var email_address: String?
        get() = sharedPref.getString("email_address", "")
        set(value) {
            editor.putString("email_address", value).commit()
        }

    var email_location: String?
        get() = sharedPref.getString("email_location", "")
        set(value) {
            editor.putString("email_location", value).commit()
        }

    var email_primary: Boolean
        get() = sharedPref.getBoolean("email_primary", false)
        set(value) {
            editor.putBoolean("email_primary", value).commit()
        }

    var email_main: Boolean
        get() = sharedPref.getBoolean("email_main", false)
        set(value) {
            editor.putBoolean("email_main", value).commit()
        }

    var address_line: String?
        get() = sharedPref.getString("address_line", "")
        set(value) {
            editor.putString("address_line", value).commit()
        }

    var address_latitude: String?
        get() = sharedPref.getString("address_latitude", "")
        set(value) {
            editor.putString("address_latitude", value).commit()
        }

    var address_longitude: String?
        get() = sharedPref.getString("address_longitude", "")
        set(value) {
            editor.putString("address_longitude", value).commit()
        }

    var mobile_number_main: String?
        get() = sharedPref.getString("mobile_number_main", "")
        set(value) {
            editor.putString("mobile_number_main", value).commit()
        }

    var contact_gender: String?
        get() = sharedPref.getString("contact_gender", "")
        set(value) {
            editor.putString("contact_gender", value).commit()
        }

    var message_request: Boolean
        get() = sharedPref.getBoolean("message_request", false)
        set(value) {
            editor.putBoolean("message_request", value).commit()
        }

    var bus_route: Boolean
        get() = sharedPref.getBoolean("bus_route", false)
        set(value) {
            editor.putBoolean("bus_route", value).commit()
        }

    var bus_time_change: Boolean
        get() = sharedPref.getBoolean("bus_time_change", false)
        set(value) {
            editor.putBoolean("bus_time_change", value).commit()
        }

    var new_driver_assigned: Boolean
        get() = sharedPref.getBoolean("new_driver_assigned", false)
        set(value) {
            editor.putBoolean("new_driver_assigned", value).commit()
        }

    var driver_waiting: Boolean
        get() = sharedPref.getBoolean("driver_waiting", false)
        set(value) {
            editor.putBoolean("driver_waiting", value).commit()
        }

    var pick_up_location_change: Boolean
        get() = sharedPref.getBoolean("pick_up_location_change", false)
        set(value) {
            editor.putBoolean("pick_up_location_change", value).commit()
        }

    var news_and_general_updates: Boolean
        get() = sharedPref.getBoolean("news_and_general_updates", false)
        set(value) {
            editor.putBoolean("news_and_general_updates", value).commit()
        }

    var redirect_screen: String?
        get() = sharedPref.getString("redirect_screen", "")
        set(value) {
            editor.putString("redirect_screen", value).commit()
        }

    var user_type: String?
        get() = sharedPref.getString("user_type", "")
        set(value) {
            editor.putString("user_type", value).commit()
        }
}