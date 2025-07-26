package de.syntax_institut.androidabschlussprojekt.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

fun handleActionWithPermission(
    context: Context,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>,
    permission: String,
    action: () -> Unit,
    showRationale: () -> Unit
) {
    when {

        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
            action()
        }


        (context as? Activity)?.let {
            shouldShowRequestPermissionRationale(it, permission)
        } == true -> {
            showRationale()
        }


        else -> {
            requestSinglePermissionLauncher.launch(permission)
        }
    }
}