package id.bukusaku.bukusaku.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharePreference {
    companion object {
        const val SHARE_PREFERENCES_NAME = "SHARE_PREFERENCES_NAME"
    }

    private var sharePreference: SharedPreferences? = null

    fun init(context: Context) {
        if (null == sharePreference)
            sharePreference = context.getSharedPreferences(SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE)
    }

    fun readBoolean(key: String, value: Boolean) = sharePreference?.getBoolean(key, value)

    fun writeBoolean(key: String, value: Boolean) {
        val editor = sharePreference?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

}