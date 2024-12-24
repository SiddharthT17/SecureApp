package com.dummyapp.sharesecureapp

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureStorage {
    private const val PREFS_NAME = "encrypted_prefs"

    private fun getEncryptedSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveBankDetails(context: Context, accountNumber: String, routingNumber: String, sharedCode : String) {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        encryptedPrefs.edit().apply {
            putString("account_number", accountNumber)
            putString("routing_number", routingNumber)
            val value = accountNumber + routingNumber + sharedCode
            putString("account_routing_number_sharecode", value)
            apply()
        }
    }

    fun getBankDetails(context: Context): Pair<String?, String?> {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        val accountNumber = encryptedPrefs.getString("account_number", null)
        val routingNumber = encryptedPrefs.getString("routing_number", null)
        return accountNumber to routingNumber
    }

    fun clearBankDetails(context: Context) {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        encryptedPrefs.edit().clear().apply()
    }

    fun saveUserDetails(context: Context, email: String, password: String) {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        encryptedPrefs.edit().apply {
            putString("email" + "_${email}", email)
            putString("password" + "_${email}", password)
            apply()
        }
    }

    fun getUserDetails(context: Context, email: String, password: String): Pair<String?, String?> {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        val email = encryptedPrefs.getString("email" + "_${email}", null)
        val password = encryptedPrefs.getString("password" + "_${email}", null)
        return email to password
    }
}
