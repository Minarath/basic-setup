package com.smart.sample.app.data.remote.helper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object ApiUtils {

    fun createMultipartBody(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(
                keyName, file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(
                keyName, "",
                "".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }
    }

    fun createMultipartBodyForVideo(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(
                keyName, file.name,
                file.asRequestBody("video/*".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(
                keyName, "",
                "".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }
    }

    fun createMultipartBodyForFile(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(
                keyName, file.name,
                file.asRequestBody("*/*".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(
                keyName, "",
                "".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }
    }

    fun createMultipartBodyForDocument(file: File?, keyName: String): MultipartBody.Part {
        return if (file != null) {
            MultipartBody.Part.createFormData(
                keyName, file.name,
                file.asRequestBody("*/*".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(
                keyName, "",
                "".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }
    }
}