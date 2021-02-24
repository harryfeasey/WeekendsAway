package com.example.weekendsaway

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.View.inflate
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val granted = checkPermissions()

        if (!granted) {
            //Navigate to screen explaining why it should be enabled, with button to bring up settings.
            val dialogFragment = UsageStatsPermissionFragment()
            dialogFragment.show(supportFragmentManager, "main")

        }
    }

    fun checkPermissions(): Boolean {

        //Check if permission enabled
        var appOpsManager: AppOpsManager =
            this.applicationContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsManager.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            this.packageName
        )
        val granted = mode == AppOpsManager.MODE_ALLOWED
        return granted
    }
}


