package org.firezenk.foodieapp.ui.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener

fun Context.grantPermission(permission: String,
                            onPermissionsGranted: () -> Unit,
                            onPermissionDenied: (Boolean) -> Unit) = Dexter.withActivity(this as Activity)
        .withPermission(permission)
        .withListener(object: BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                onPermissionsGranted()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                onPermissionDenied(response.isPermanentlyDenied)
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                token.continuePermissionRequest()
            }
        })
        .check()

fun Context.grantPermissions(permissions: List<String>, onPermissionsChecked: (Boolean) -> Unit) =
        Dexter.withActivity(this as Activity)
                .withPermissions(permissions)
                .withListener(object : BaseMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        val permissionsGranted = permissions.
                                none { checkCallingOrSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
                        onPermissionsChecked(permissionsGranted)
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>,
                                                                    token: PermissionToken) {
                        token.continuePermissionRequest();
                    }

                })
                .check()