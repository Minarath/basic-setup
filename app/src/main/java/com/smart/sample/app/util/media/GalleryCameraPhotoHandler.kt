package com.smart.sample.app.util.media

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.smart.sample.app.util.UriHelper
import com.smart.sample.app.util.permission.SimplePermissionHandler
import java.io.File
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


open class GalleryCameraPhotoHandler : Fragment() {

    private var photoHandler: PhotoHandler? = null
    private var pickPhotoCallback: PickPhotoCallback? = null
    private var multiplePhotoCallback: MutltiplePhotoCallback? = null
    private var temp_uri_capture: Uri? = null
    private var weakReference: WeakReference<FragmentActivity>? = null
    private val PICK_GALLERY = 1005
    private val PICK_CAMERA = 1006
    private val REQUEST_VIDEO_CAPTURE = 1007
    private val PICK_VIDEOGALEERY = 1008
    private val ChooseMultiple = 1009
    private val DIRECTORY_NAME = "Hubknob"

    private val weakActivity: FragmentActivity
        get() = weakReference?.get()!!

    private val outputMediaFileUri: Uri
        get() = FileProvider.getUriForFile(
                weakActivity,
                "${context?.packageName!!}.provider",
                outputMediaFile
        )

    private val outputMediaFile: File
        get() {
            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME)
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e(DIRECTORY_NAME, "Oops! Failed create " + DIRECTORY_NAME + " directory")
                    return File("")
                }
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("en")).format(Date())
            val mediaFile: File
            mediaFile = File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")

            return mediaFile
        }

    enum class PhotoHandler {
        GALLERY, CAMERA
    }

    interface PickPhotoCallback {
        fun photoPath(path: String, photoHandler: PhotoHandler?)
    }

    interface MutltiplePhotoCallback {
        fun photoList(imagearray: Array<Parcelable>, photoHandler: PhotoHandler?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReference = WeakReference<FragmentActivity>(activity)
        pickPhoto()
    }

    private fun pickPhoto() {
        if (photoHandler == PhotoHandler.GALLERY) {
            chooseGallery()
        } else if (photoHandler == PhotoHandler.CAMERA) {
            chooseCamera()
        }

    }


    fun getGalleryCameraPhoto(
            fragmentActivity: FragmentActivity,
            photoHandler: PhotoHandler?,
            pickPhotoCallback: PickPhotoCallback?
    ): GalleryCameraPhotoHandler {

        var galleryCameraPhotoHandler =
            fragmentActivity.supportFragmentManager.findFragmentByTag(GalleryCameraPhotoHandler::class.java.name) as GalleryCameraPhotoHandler?
        if (galleryCameraPhotoHandler != null) {
            galleryCameraPhotoHandler.pickPhotoCallback = pickPhotoCallback
            galleryCameraPhotoHandler.photoHandler = photoHandler
            galleryCameraPhotoHandler.pickPhoto()
        } else {
            galleryCameraPhotoHandler = GalleryCameraPhotoHandler()
            galleryCameraPhotoHandler.pickPhotoCallback = pickPhotoCallback
            galleryCameraPhotoHandler.photoHandler = photoHandler
            fragmentActivity.supportFragmentManager
                .beginTransaction()
                .add(galleryCameraPhotoHandler, GalleryCameraPhotoHandler::class.java.name)
                .commitAllowingStateLoss()
        }
        return this
    }


    private fun chooseGallery() {
        SimplePermissionHandler.Builder().setContext(weakActivity)
            .setAllPermission(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            .setPermissionCallback(object : SimplePermissionHandler.PermissionInterface {
                override fun permissionGranted() {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, PICK_GALLERY)
                }

                override fun permissionDenied() {
                    removeFragment()
                }

                override fun permissionShowDialog() {

                }
            }).show()
    }


    private fun chooseCamera() {

        SimplePermissionHandler.Builder().setContext(weakActivity)
            .setAllPermission(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            )
            .setPermissionCallback(object : SimplePermissionHandler.PermissionInterface {
                override fun permissionGranted() {
                    temp_uri_capture = outputMediaFileUri

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, temp_uri_capture)
                    startActivityForResult(intent, PICK_CAMERA)
                }

                override fun permissionDenied() {
                    removeFragment()
                }

                override fun permissionShowDialog() {

                }
            }).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            val fileProfilePic: File

            if (requestCode == PICK_GALLERY) {

                if (data != null) {
                    val image_path: String? = data.data?.let { UriHelper.getPath(weakActivity, it) }

                    if (image_path != null && image_path.isNotEmpty()) {

                        fileProfilePic = File(image_path)

                        if (pickPhotoCallback != null) {
                            pickPhotoCallback.let {
                                pickPhotoCallback?.photoPath(fileProfilePic.absolutePath, photoHandler)
                            }
                        }
                    }
                }
            } else if (requestCode == PICK_CAMERA) {
                temp_uri_capture?.let {
                    if (temp_uri_capture != null && temp_uri_capture?.path != null) {

                        val capture_path_name = temp_uri_capture.toString().substring(
                            temp_uri_capture.toString().lastIndexOf("/") + 1,
                            temp_uri_capture.toString().length
                        )

                        fileProfilePic = File(
                            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME), capture_path_name
                        )

                        if (pickPhotoCallback != null) {

                            pickPhotoCallback.let {
                                pickPhotoCallback?.photoPath(fileProfilePic.absolutePath, photoHandler)
                            }
                        }
                    }
                }
            }


        }

    }

    private fun removeFragment() {
        if (fragmentManager != null) {
            fragmentManager.let {
                fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
            }
        }
    }

    class Builder {
        private var fragmentActivity: FragmentActivity? = null
        private var photoHandler: PhotoHandler? = null
        private var pickPhotoCallback: PickPhotoCallback? = null

        fun setContext(fragmentActivity: FragmentActivity): Builder {
            this.fragmentActivity = fragmentActivity
            return this
        }

        fun setPhotoHandler(photoHandler: PhotoHandler): Builder {
            this.photoHandler = photoHandler
            return this
        }

        fun setPhotoCallback(pickPhotoCallback: PickPhotoCallback): Builder {
            this.pickPhotoCallback = pickPhotoCallback
            return this
        }

        fun build() {
            if (fragmentActivity == null) {
                throw NullPointerException("Context must not be null")
            }
            GalleryCameraPhotoHandler()
                .getGalleryCameraPhoto(fragmentActivity!!, photoHandler, pickPhotoCallback)
        }

    }
}
