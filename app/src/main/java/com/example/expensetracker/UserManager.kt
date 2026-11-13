package com.example.expensetracker

import android.content.Context
import android.content.SharedPreferences

/**
 * UserManager manages saving and retrieving user profile data
 * using Android's local SharedPreferences.
 */
object UserManager {

    private const val PREF_NAME = "expense_tracker_user_prefs"
    private const val KEY_IS_FIRST_TIME = "is_first_time"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Checks if this is the user's first time opening the app.
     * Defaults to true until the user finishes the Welcome screen.
     */
    fun isFirstTime(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_FIRST_TIME, true)
    }

    /**
     * Marks first-time onboarding as completed.
     */
    fun setFirstTimeCompleted(context: Context) {
        getPrefs(context).edit().putBoolean(KEY_IS_FIRST_TIME, false).apply()
    }

    /**
     * Retrieves the saved user's name. Defaults to "User" if not set.
     */
    fun getUserName(context: Context): String {
        return getPrefs(context).getString(KEY_USER_NAME, "") ?: ""
    }

    /**
     * Saves or updates the user's name.
     */
    fun saveUserName(context: Context, name: String) {
        getPrefs(context).edit().putString(KEY_USER_NAME, name.trim()).apply()
    }

    /**
     * Retrieves the saved profile image URI (if any).
     */
    fun getProfileImageUri(context: Context): String? {
        return getPrefs(context).getString(KEY_PROFILE_IMAGE_URI, null)
    }

    /**
     * Saves or removes (if null) the profile image URI.
     */
    fun saveProfileImageUri(context: Context, uriString: String?) {
        getPrefs(context).edit().putString(KEY_PROFILE_IMAGE_URI, uriString).apply()
    }

    /**
     * Resets profile data (useful if the user wants to restart onboarding).
     */
    fun resetUserData(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}
