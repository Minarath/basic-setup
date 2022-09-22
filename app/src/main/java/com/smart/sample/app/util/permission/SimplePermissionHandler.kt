package com.smart.sample.app.util.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 * Created by iRoid on 06-Mar-17.
 */
open class SimplePermissionHandler : Fragment() {

    private var permissionInterface: PermissionInterface? = null
    private val REQUEST_PERMISSION = 5000
    private var fragmentWeakReference: WeakReference<Fragment>? = null
    private var permission: Array<String>? = null
    private var isShowNeverDialog: Boolean? = true

    private val weakFragment: Fragment
        get() = fragmentWeakReference!!.get()!!

    private val baseActivity: Activity?
        get() = weakFragment.activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentWeakReference = WeakReference(this)
        checkPermission(permission)
    }

    private fun checkPermissionHandler(
        fragmentActivity: FragmentActivity,
        permission: Array<String>,
        permissionInterface: PermissionInterface?,
        isShowNeverDialog: Boolean?
    ): SimplePermissionHandler {

        var permissionHandler =
            fragmentActivity.supportFragmentManager.findFragmentByTag(SimplePermissionHandler::class.java.name) as SimplePermissionHandler?
        if (permissionHandler != null) {
            permissionHandler.permissionInterface = permissionInterface
            permissionHandler.isShowNeverDialog = isShowNeverDialog
            permissionHandler.checkPermission(permission)
        } else {
            permissionHandler = SimplePermissionHandler()
            permissionHandler.permissionInterface = permissionInterface
            permissionHandler.permission = permission
            permissionHandler.isShowNeverDialog = isShowNeverDialog
            fragmentActivity.supportFragmentManager
                .beginTransaction()
                .add(permissionHandler, SimplePermissionHandler::class.java.name)
                .commitAllowingStateLoss()
        }
        return this
    }

    private fun checkPermission(permission: Array<String>?) {
        if (!checkSelfAllPermission(permission, weakFragment.activity)) {
            requestPermissions(permission!!, REQUEST_PERMISSION)
            return
        }
        removeFragment()
        if (permissionInterface != null) {
            permissionInterface!!.permissionGranted()
        }
    }

    private fun checkSelfAllPermission(permission: Array<String>?, activity: Activity?): Boolean {

        var isAllPermissionAccepted = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (aPermission in permission!!) {

                if (activity != null) {
                    if (activity.checkSelfPermission(aPermission) != PackageManager.PERMISSION_GRANTED) {
                        isAllPermissionAccepted = false
                        break
                    }

                } else {
                    isAllPermissionAccepted = false
                }
            }
        }
        return isAllPermissionAccepted
    }

    fun shouldShowRequestPermissionRationaleDialog(permission: String): Boolean {
        return shouldShowRequestPermissionRationale(permission)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {

            var isAllPermissionAccepted = true

            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllPermissionAccepted = false
                    if (!shouldShowRequestPermissionRationaleDialog(permissions[i])) {
                        if (isShowNeverDialog!!) {
                            showPermissionDialog(permissions[i])
                        } else {
                            if (permissionInterface != null) {
                                permissionInterface!!.permissionShowDialog()
                            }
                        }
                        return
                    }
                }
            }
            removeFragment()
            if (permissionInterface != null) {
                if (isAllPermissionAccepted) {
                    permissionInterface!!.permissionGranted()
                } else {
                    permissionInterface!!.permissionDenied()
                }
            }
        }
    }

    private fun removeFragment() {
        if (fragmentManager != null) {
            requireFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss()
        }
    }

    private fun showPermissionDialog(permission: String) {

        Toast.makeText(weakFragment.context, "Please provide Permission", Toast.LENGTH_SHORT).show()

        //        new MaterialDialog.Builder(getBaseActivity())
        //                .content(App.getContext().getString(R.string.permission_msg))
        //                .dismissListener(new DialogInterface.OnDismissListener() {
        //                    @Override
        //                    public void onDismiss(DialogInterface dialog) {
        //                        removeFragment();
        //                        if (permissionInterface != null) {
        //                            permissionInterface.permissionDenied();
        //                        }
        //                    }
        //                })
        //                .positiveText("Open Setting")
        //                .onPositive(new MaterialDialog.SingleButtonCallback() {
        //                    @Override
        //                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        //                        try {
        //                            Intent intent = new Intent();
        //                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        //                            Uri uri = Uri.fromParts("package", getBaseActivity().getPackageName(), null);
        //                            intent.setData(uri);
        //                            getBaseActivity().startActivity(intent);
        //                        } catch (Exception e) {
        //                            e.printStackTrace();
        //                        }
        //                        removeFragment();
        //                        if (permissionInterface != null) {
        //                            permissionInterface.permissionDenied();
        //                        }
        //                    }
        //                }).show();

    }

    class Builder {
        private var fragmentActivity = FragmentActivity()
        private var permission: Array<String>? = null
        private var permissionInterface: PermissionInterface? = null
        private var isShowNeverDialog: Boolean? = true

        fun setContext(fragmentActivity: FragmentActivity): Builder {
            this.fragmentActivity = fragmentActivity
            return this
        }

        fun setAllPermission(permission: Array<String>): Builder {
            this.permission = permission
            return this
        }

        fun isShowNeverAskDialog(isShowNeverDialog: Boolean?): Builder {
            this.isShowNeverDialog = isShowNeverDialog
            return this
        }

        fun setPermissionCallback(permissionInterface: PermissionInterface): Builder {
            this.permissionInterface = permissionInterface
            return this
        }

        internal fun isGrantedAllPermission(permission: Array<String>?): Boolean {
            if (fragmentActivity == null) {
                throw NullPointerException("Context must not be null")
            } else if (permission == null || permission.size == 0) {
                throw NullPointerException("permission must not be null")
            }
            return SimplePermissionHandler().checkSelfAllPermission(permission, fragmentActivity)
        }

        fun show() {
            if (fragmentActivity == null) {
                throw NullPointerException("Context must not be null")
            } else if (permission == null || permission!!.size == 0) {
                throw NullPointerException("permission must not be null")
            }
            SimplePermissionHandler().checkPermissionHandler(
                fragmentActivity!!,
                permission!!, permissionInterface, isShowNeverDialog
            )
        }

        fun build() {
            if (fragmentActivity == null) {
                throw NullPointerException("Context must not be null")
            } else if (permission == null || permission!!.isEmpty()) {
                throw NullPointerException("permission must not be null")
            }

            SimplePermissionHandler().checkPermissionHandler(fragmentActivity!!, permission!!, permissionInterface, true)
        }


    }

    interface PermissionInterface {

        fun permissionGranted()

        fun permissionDenied()

        fun permissionShowDialog()

    }

}
