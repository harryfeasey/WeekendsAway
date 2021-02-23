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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.PACKAGE_USAGE_STATS)

        //Check if permission enabled
        var appOpsManager: AppOpsManager = this.applicationContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsManager.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), this.packageName)
        val granted = mode == AppOpsManager.MODE_ALLOWED

        if (!granted) {
            Log.i("main", "Permission to get stats denied")
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }
}


