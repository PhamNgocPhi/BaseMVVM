package com.example.basemvvm.data.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.basemvvm.utils.constants.Constant

class AppPreferences (val context: Context){

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        Constant.Preferences.PREF_FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * save access token
     */
    fun saveAccessToken(accessToken: String?) {
        if (accessToken != null) {
            sharedPreferences.edit().putString(Constant.Preferences.ACCESS_TOKEN, accessToken).apply()
        }
    }

    /**
     * get access token
     */
    fun getAccessToken() : String {
        return sharedPreferences.getString(Constant.Preferences.ACCESS_TOKEN, "") ?: ""
    }
}