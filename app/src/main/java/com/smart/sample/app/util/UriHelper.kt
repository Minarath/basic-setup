package com.smart.sample.app.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import java.io.*
import java.util.*
import java.util.regex.Pattern

object UriHelper {
    /**
     * Get a customFile path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other customFile-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {

        // ExternalStorageProvider
        if (DocumentsContract.isDocumentUri(context, uri) && isGoogleDriveUri(uri)) {
            return getDriveFilePath(uri, context)
        } else if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            var realPath_1 = ""

            if ("primary".equals(type, ignoreCase = true)) {
                realPath_1 = Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else {
                val DIR_SEPORATOR: Pattern = Pattern.compile("/")
                val rv: MutableSet<String> = HashSet()
                val rawExternalStorage = System.getenv("EXTERNAL_STORAGE")
                val rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE")
                val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET")
                if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
                    if (TextUtils.isEmpty(rawExternalStorage)) {
                        rv.add("/storage/sdcard0")
                    } else {
                        rv.add(rawExternalStorage.toString())
                    }
                } else {
                    val rawUserId: String
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        rawUserId = ""
                    } else {
                        val path = Environment.getExternalStorageDirectory().absolutePath
                        val folders: Array<String> = DIR_SEPORATOR.split(path)
                        val lastFolder = folders[folders.size - 1]
                        var isDigit = false
                        try {
                            Integer.valueOf(lastFolder)
                            isDigit = true
                        } catch (ignored: NumberFormatException) {
                        }
                        rawUserId = if (isDigit) lastFolder else ""
                    }
                    if (TextUtils.isEmpty(rawUserId)) {
                        rv.add(rawEmulatedStorageTarget.toString())
                    } else {
                        rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
                    }
                }
                if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
                    val rawSecondaryStorages = rawSecondaryStoragesStr?.split(File.pathSeparator.toRegex())?.toTypedArray()
                    setOf(rv, rawSecondaryStorages)
                }
                val temp = rv.toTypedArray()
                for (i in temp.indices) {
                    val tempf = File(temp[i] + "/" + split[1])
                    if (tempf.exists()) {
                        realPath_1 = temp[i] + "/" + split[1]
                    }
                }
            }
            return realPath_1
        } else if (isDownloadsDocument(uri)) {

            val id = DocumentsContract.getDocumentId(uri)
            if (id.startsWith("raw:")) {
                return id.replaceFirst("raw:".toRegex(), "")
            }
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))

            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            val type = split[0]

            Log.e("type", type)

            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            } else if ("document" == type) {
                contentUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", File(uri.path.toString()))
                Log.e("type", " $contentUri")
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf<String>(split[1])

            return getDataColumn(
                    context,
                    contentUri,
                    selection,
                    selectionArgs
            )
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }

            if (isNewGooglePhotosUri(uri)) {

                val pathUri = uri.path
                Log.e("URI Helper", "pathUri: " + pathUri!!)
                var newUri = ""

                if (pathUri.contains("mediaKey") || pathUri.contains("mediakey")) {
                    //newUri = pathUri.substring(pathUri.indexOf("mediaKey"), pathUri.lastIndexOf("/ACTUAL"));
                    return getImageUrlWithAuthority(context, uri)
                } else {
                    if (pathUri.contains("NO_TRANSFORM")) {
                        newUri = pathUri.substring(pathUri.indexOf("content"), pathUri.lastIndexOf("/NO_TRANSFORM"))
                        Log.e("URI Helper", "newUri: $newUri")
                        return getDataColumn(
                                context,
                                Uri.parse(newUri),
                                null,
                                null
                        )
                    } else if (pathUri.contains("ACTUAL")) {
                        newUri = pathUri.substring(pathUri.indexOf("content"), pathUri.lastIndexOf("/ACTUAL"))
                        Log.e("URI Helper", "newUri: $newUri")
                        return getDataColumn(context, Uri.parse(newUri), null, null)
                    } else {
                        return getRealPathFromURI(context, uri)
                    }

                }
            } else if (isGoogleDriveUri(uri)) {
                return getImageUrlWithAuthority(context, uri)
            }
            return getDataColumn(context, uri, null, null)
        } else if ("customFile".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File  /-1/1/content://media/external/images/media/47994/NO_TRANSFORM/1029156210
        // MediaStore (and general)
        // MediaProvider
        // DownloadsProvider

        return null
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    //====================================================================================================================================================

    fun getImageUrlWithAuthority(context: Context, uri: Uri): String? {
        var `is`: InputStream? = null
        if (uri.authority != null) {
            try {
                `is` = context.contentResolver.openInputStream(uri)
                val bmp = BitmapFactory.decodeStream(`is`)
                Log.e("URI Helper", "Downloaded BitMap Width: " + bmp.width)
                Log.e("URI Helper", "Downloaded BitMap Width: " + bmp.height)
                //return writeToTempImageAndGetPathUri(context, bmp).toString();
                return getPath(
                        context,
                        writeToTempImageAndGetPathUri(context, bmp)
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                try {
                    `is`!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }

    fun writeToTempImageAndGetPathUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        Log.e("UriHelper", "downloaded path: $path")
        Log.e("UriHelper", "downloaded path Uri: " + Uri.parse(path))

        //	Utils.refreshGallery(inContext, new File(path));

        return Uri.parse(path)
    }


    //====================================================================================================================================================

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other customFile-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a customFile path.
     */
    private fun getDataColumn(context: Context, uri: Uri?,
                              selection: String?, selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection,
                    selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                Log.e("URIHelper", "index: $index")
                Log.e("URIHelper", "cursor string at index: " + cursor.getString(index))
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }

        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
                .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */


    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.ic_face_intro_two.content" == uri.authority
    }

    fun isGoogleDriveUri(uri: Uri): Boolean {
        return "com.google.android.apps.docs.storage" == uri.authority
    }

    fun isNewGooglePhotosUri(uri: Uri): Boolean {
        if ("com.google.android.apps.ic_face_intro_two.contentprovider" == uri.authority) {
            Log.e("URIHelper", "isGooglePhotosUri : true")
            return true
        } else {
            Log.e("URIHelper", "isGooglePhotosUri : false")
            return false
        }


    }

    private fun getDriveFilePath(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)

        /*Get the column indexes of the data in the Cursor,
        move to the first row in the Cursor, get the data,
        and display it.
        */
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = java.lang.Long.toString(returnCursor.getLong(sizeIndex))
        val file = File(context.cacheDir, name)
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable = inputStream!!.available()

            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)
            Log.e("File Size", "Size " + file.length())
        } catch (e: Exception) {
            e.message?.let { Log.e("Exception", it) }
        }
        return file.path
    }

}
