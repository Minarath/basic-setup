package com.iunctainc.iuncta.app.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*


private var toast: Toast? = null

fun Context.showToast(message: String) {
    showMessage(this, message)
}

fun Activity.showToast(message: String) {
    showMessage(this, message)
}

fun Fragment.showToast(message: String) {
    showMessage(context!!, message)
}

private fun showMessage(context: Context, message: String) {
    if (toast != null) {
        toast?.cancel()
    }
    toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast?.show()
}

fun getMimeType(context: Context, uri: Uri): String? {
    return if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        val cr = context.contentResolver
        cr.getType(uri)
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase(Locale.getDefault()))
    }
}