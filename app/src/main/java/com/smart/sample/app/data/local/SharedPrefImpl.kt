package com.smart.sample.app.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.smart.sample.app.data.beans.LoginResponse
import com.smart.sample.app.data.beans.UserBean

class SharedPrefImpl(private val sharedPreferences: SharedPreferences) : SharedPref {

    override fun putUserData(value: LoginResponse) {
        sharedPreferences.edit().putString("userdata", Gson().toJson(value)).apply()

    }

    override fun putUserData(value: UserBean) {
        sharedPreferences.edit().putString("userdata", Gson().toJson(value)).apply()
    }

    override fun putRememberMeData(value: UserBean) {
        sharedPreferences.edit().putString("userdata", Gson().toJson(value)).apply()
    }

    override val rememberMeData: UserBean?
        get() = try {
            Gson().fromJson(sharedPreferences.getString("userdata", null), UserBean::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    override val userData: UserBean?
        get() = try {
            Gson().fromJson(sharedPreferences.getString("userdata", null), UserBean::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }

    override val userResponse: LoginResponse?
        get() = try {
            Gson().fromJson(sharedPreferences.getString("userdata", null), LoginResponse::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }

    override fun put(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun get(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun put(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun get(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    override fun put(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun put(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun get(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    override fun put(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    override fun delete(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun deleteAll() {
        sharedPreferences.edit().clear().apply()
    }
}