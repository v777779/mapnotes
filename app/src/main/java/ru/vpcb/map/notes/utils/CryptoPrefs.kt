package ru.vpcb.map.notes.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object CryptoPrefs {

    private const val SHARED_PREFERENCE_NAME = "secret_shared_prefs"
    private const val TOKEN = "account_token_key"
    private const val TIMESTAMP_USER = "account_timestamp_key"
    private const val TIMESTAMP_NOTES = "account_timestamp_notes_key"

    val keys = arrayOf(TOKEN)

    private var sp: SharedPreferences? = null

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sp = EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


   fun getPermissionState() = try {
        PermissionState.valueOf(get(PERMISSION_STATE) ?: PermissionState.FINE_RATIONALE.name)
    } catch (e: Exception) {
        PermissionState.LOCATION_EXPIRED
    }

    fun setPermissionState(state: PermissionState) {
        set(PERMISSION_STATE, state.name)
    }

    fun getPermissions(key:String) = (get(key)?.toIntOrNull()?:0)
    fun setPermissions(key:String, reset:Boolean) {
        if(!reset) {
            val count = (get(key)?.toIntOrNull() ?: 0) + 1
            set(key, (count).toString())
        }else{
            set(key, null)
        }
    }

    fun expireNotes(): Boolean {
        val value = get(TIMESTAMP_NOTES)?.toLong() ?: return true
        return System.currentTimeMillis() - value > NOTES_EXPIRED
    }

    fun setNotes() {
        set(TIMESTAMP_NOTES, (System.currentTimeMillis()).toString())
    }

    fun userExpired(): Boolean {
        val value = get(TIMESTAMP_USER)?.toLong() ?: return true
        return System.currentTimeMillis() - value > LOGIN_EXPIRED
    }

    fun resetUser() {
        set(TIMESTAMP_USER, null)
    }

    fun setUser() {
        set(TIMESTAMP_USER, (System.currentTimeMillis()).toString())
    }

    fun getToken(): String? {
        return get(TOKEN);
    }

    fun setToken(token: String?) {
        set(TOKEN, token)
    }

    fun reset() {
        keys.forEach {
            set(it, null)
        }
    }


    // thresholds

    private fun updateTimestamp(key: String, limit: Long, force: Boolean): Boolean {
        if (force) {
            set(key, null)
            return true
        }
        val current = System.currentTimeMillis()
        val result = get(key)?.let {
            try {
                current - it.toLong() > limit
            } catch (e: Exception) {
                true
            }
        } ?: true
        if (result) set(key, current.toString())
        return result
    }

    // helpers
    private fun getList(key: String): List<String>? {
        return getStringList(key)
    }

    private fun setList(key: String, value: List<String>?) {
        setStringList(key, value)
    }

    private fun asInt(value: String?, default: Int? = null): Int? {
        return try {
            if (value.isNullOrEmpty()) default
            else value.toInt()
        } catch (e: Exception) {
            default
        }
    }

    private fun getInt(key: String): Int? {
        return asInt(getString(key))
    }

    private fun setInt(key: String, value: Int?) {
        setString(key, value?.toString())
    }

    private fun get(key: String): String? {
        return getString(key)
    }

    private fun set(key: String, value: String?) {
        setString(key, value)
    }

    // preference

    private fun toLong(value: String, default: Long = INTEGER_FAIL.toLong()): Long {
        return try {
            if (value.isEmpty()) default
            else value.toLong()
        } catch (e: Exception) {
            default
        }
    }

    private fun toHex(value: String, default: Int = INTEGER_FAIL): Int {
        return try {
            if (value.isEmpty()) default
            else value.toInt(16)
        } catch (e: Exception) {
            default
        }
    }

    private fun toInt(value: String?, default: Int = INTEGER_FAIL): Int {
        return try {
            if (value.isNullOrEmpty()) default
            else value.toInt()
        } catch (e: Exception) {
            default
        }
    }

    private fun setIntArray(keys: Array<String>, values: Array<Int>) {
        sp?.let { sp ->
            val editor = sp.edit()
            for (i in keys.indices) {
                editor.putString(keys[i], values[i].toString())
            }
            editor.apply()
        }
    }

    private fun getIntArray(keys: List<String>, default: Int = INTEGER_ZERO): Array<Int> {
        val array = Array(keys.size) { default }
        for (i in keys.indices) {
            array[i] = getInt(keys[i], default)
        }
        return array
    }


    private fun setInt(key: String, value: Int) {
        sp?.let { sp ->
            val editor = sp.edit()
            editor.putString(key, value.toString())
            editor.apply()
        }
    }


    private fun getInt(key: String, default: Int): Int {
        return toInt(getString(key), default)
    }


    private fun getStringList(key: String): List<String>? {
        return sp?.getStringSet(key, null)?.toList()
    }

    private fun setStringList(key: String, value: List<String>? = null) {
        sp?.let { sp ->
            val editor = sp.edit()
            editor.putStringSet(key, value?.toSet())
            editor.apply()
        }
    }

    private fun getString(key: String): String? {
        return sp?.getString(key, null)
    }

    private fun setString(key: String, value: String? = null) {
        sp?.let { sp ->
            val editor = sp.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }


    enum class PermissionState {
        FINE_RATIONALE, FINE_EXPIRED, BACK_RATIONALE, BACK_EXPIRED, LOCATION_RATIONALE,
        LOCATION_EXPIRED, LOCATION_FINAL,
    }
}