package com.example.expensetracker

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * UserManager manages saving and retrieving user profile data
 * using Android's local SharedPreferences and private internal storage.
 */
object UserManager {

    private const val PREF_NAME = "expense_tracker_user_prefs"
    private const val KEY_IS_FIRST_TIME = "is_first_time"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_PROFILE_IMAGE_PATH = "profile_image_path"
    private const val AVATAR_FILE_NAME = "profile_avatar.jpg"

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
     * Copies the picked image from the gallery content URI into app's private internal storage
     * (context.filesDir/profile_avatar.jpg). This guarantees the image persists permanently
     * without expiring when the app restarts. Overwrites any previous image cleanly.
     * Returns the absolute file path, or null if copy failed.
     */
    fun saveProfileImageFromUri(context: Context, sourceUri: Uri): String? {
        return try {
            val destinationFile = File(context.filesDir, AVATAR_FILE_NAME)
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                FileOutputStream(destinationFile).use { output ->
                    input.copyTo(output)
                }
            }
            val path = destinationFile.absolutePath
            getPrefs(context).edit().putString(KEY_PROFILE_IMAGE_PATH, path).apply()
            path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Returns the File of the profile image if it exists in internal storage and is valid, or null.
     */
    fun getProfileImageFile(context: Context): File? {
        val path = getPrefs(context).getString(KEY_PROFILE_IMAGE_PATH, null) ?: return null
        val file = File(path)
        return if (file.exists() && file.length() > 0) file else null
    }

    /**
     * Deletes the profile avatar file from internal storage and clears its preference.
     */
    fun deleteProfileImage(context: Context): Boolean {
        getPrefs(context).edit().remove(KEY_PROFILE_IMAGE_PATH).apply()
        val file = File(context.filesDir, AVATAR_FILE_NAME)
        return if (file.exists()) file.delete() else true
    }

    /**
     * Resets profile data (useful if the user wants to restart onboarding).
     */
    fun resetUserData(context: Context) {
        deleteProfileImage(context)
        getPrefs(context).edit().clear().apply()
    }
}
