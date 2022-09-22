package com.smart.sample.app.data.beans.connection

data class ConnectionBean(
    val type: State,
    val isConnected: Boolean
) {
    enum class State {
        WIFI, MOBILE, NONE
    }
}