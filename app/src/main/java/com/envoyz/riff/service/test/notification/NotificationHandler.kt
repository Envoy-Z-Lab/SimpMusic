package com.envoyz.riff.service.test.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.OptIn
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.util.UnstableApi
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.envoyz.riff.R
import com.envoyz.riff.ui.MainActivity
import kotlinx.coroutines.runBlocking

object NotificationHandler {
    private const val CHANNEL_ID = "transactions_reminder_channel"

    @OptIn(UnstableApi::class)
    fun createReminderNotification(
        context: Context,
        noti: NotificationModel,
    ) {
        //  No back-stack when launched
        val intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        intent.action = "com.envoyz.riff.service.test.notification.NOTIFICATION"
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val bitmap =
            runBlocking {
                val loader = ImageLoader(context)
                val request =
                    ImageRequest.Builder(context)
                        .data(
                            noti.single.firstOrNull()?.thumbnails?.lastOrNull()?.url
                                ?: noti.album.firstOrNull()?.thumbnails?.lastOrNull()?.url,
                        )
                        .allowHardware(false) // Disable hardware bitmaps.
                        .build()

                return@runBlocking when (val result = loader.execute(request)) {
                    is SuccessResult -> ((result as SuccessResult).drawable as BitmapDrawable).bitmap
                    else ->
                        AppCompatResources.getDrawable(context, R.drawable.holder)
                            ?.toBitmap(128, 128)
                }
            }
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.mono)
                .setContentTitle(noti.name)
                .setContentText(
                    if (noti.single.isNotEmpty()) {
                        "${context.getString(R.string.new_singles)}: ${noti.single.joinToString { it.title }}"
                    } else {
                        "${context.getString(R.string.new_albums)}: ${noti.album.joinToString { it.title }}"
                    },
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent) // For launching the MainActivity
                .setAutoCancel(true) // Remove notification when tapped
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(noti.hashCode(), builder.build())
        }
    }

    /**
     * Required on Android O+
     */
    fun createNotificationChannel(context: Context) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val name = "Update Followed Artists"
            val descriptionText =
                "This channel sends notification when followed artists release new music"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }
    }
}
