package com.app.expensetracker.feature.settings.features.support.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.app.expensetracker.core.utils.APP_VERSION

fun openEmail(context: Context, subject: String) {

    val body = """
        App Version: ${APP_VERSION}
        Android Version: ${Build.VERSION.RELEASE}
        Device: ${Build.MANUFACTURER} ${Build.MODEL}
        
        Please describe your issue below:
        
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:support@yourapp.com")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    context.startActivity(intent)
}

fun openPlayStore(context: Context) {
    val uri = Uri.parse("market://details?id=${context.packageName}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}