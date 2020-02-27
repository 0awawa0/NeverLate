package com.awawa.neverlate.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class NotificationWorker(private val context: Context, params: WorkerParameters)
    : Worker(context, params) {

    override fun doWork(): Result {
        showNotification(context)
        return Result.success()
    }
}