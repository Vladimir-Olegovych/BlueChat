package com.gigcreator.bluechat.core.activity

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

object ActivityResultUtils {
    private var arrayLauncher: ActivityResultLauncher<Array<String>>? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var onSuccess: (() -> Unit)? = null
    private var onFailure: (() -> Unit)? = null

    fun initialize(activity: AppCompatActivity){
        arrayLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                if (result.all { it.value }) {
                    onSuccess?.invoke()
                } else {
                    onFailure?.invoke()
                }
            }
        resultLauncher = activity.registerForActivityResult(StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onSuccess?.invoke()
            } else {
                onFailure?.invoke()
            }
        }
    }


    fun requestResult(
        array: Array<String>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure

        arrayLauncher?.launch(array)
    }

    fun requestResult(
        intent: Intent,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure

        resultLauncher?.launch(intent)
    }
}