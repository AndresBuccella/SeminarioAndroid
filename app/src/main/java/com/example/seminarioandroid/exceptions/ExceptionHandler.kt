package com.example.seminarioandroid.exceptions

import android.util.Log
import com.example.seminarioandroid.R
import com.example.seminarioandroid.providers.ResourceProvider
import java.io.IOException
import java.sql.SQLException

object ExceptionHandler {

    fun handleException(throwable: Throwable, resourceProvider: ResourceProvider): String {
        return when (throwable) {
            is IOException -> resourceProvider.getString(R.string.network_error)
            is SQLException -> resourceProvider.getString(R.string.database_error)
            else -> resourceProvider.getString(R.string.unknown_error)
        }
    }
}