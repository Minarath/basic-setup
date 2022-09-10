package com.iunctainc.iuncta.app.data.remote.helper

class Resource<T> private constructor(val status: Status, val data: T, val message: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val resource = other as Resource<*>
        if (status !== resource.status) {
            return false
        }
        return if (message != resource.message) {
            false
        } else data == resource.data
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message.hashCode())
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    companion object {
        @JvmStatic
        fun <T> success(data: T, msg: String): Resource<T> {
            return Resource(Status.SUCCESS, data, msg)
        }

        fun <T> warn(data: T, msg: String): Resource<T> {
            return Resource(Status.WARN, data, msg)
        }

        @JvmStatic
        fun <T> error(data: T, errMsg: String): Resource<T> {
            return Resource(Status.ERROR, data, errMsg)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, "")
        }

        fun <T> loading(data: T, message: String): Resource<T> {
            return Resource(Status.LOADING, data, message)
        }
    }
}