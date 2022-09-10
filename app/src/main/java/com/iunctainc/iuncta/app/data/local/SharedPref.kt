package com.iunctainc.iuncta.app.data.local

import com.iunctainc.iuncta.app.data.beans.LoginResponse
import com.iunctainc.iuncta.app.data.beans.UserBean

interface SharedPref {

    fun putUserData(value: LoginResponse)

    fun putUserData(value: UserBean)
    fun putRememberMeData(value: UserBean)

    val rememberMeData: UserBean?
    val userData: UserBean?
    val userResponse: LoginResponse?

    fun put(key: String, value: Int)
    operator fun get(key: String, defaultValue: Int): Int

    fun put(key: String, value: Float)
    operator fun get(key: String, defaultValue: Float): Float

    fun put(key: String, value: Boolean)
    operator fun get(key: String, defaultValue: Boolean): Boolean

    fun put(key: String, value: Long)
    operator fun get(key: String, defaultValue: Long): Long

    fun put(key: String, value: String)

    operator fun get(key: String, defaultValue: String): String

    fun delete(key: String)

    fun deleteAll()
}