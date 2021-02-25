package com.example.weekendsaway

import android.app.AppOpsManager
import android.content.Context
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import com.example.weekendsaway.MainActivity.PermissionManager.checkPermissions


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val granted = checkPermissions(applicationContext, packageName)

        if (!granted) {
            //Navigate to screen explaining why it should be enabled, with button to bring up settings.
            val dialogFragment = UsageStatsPermissionFragment()
            dialogFragment.show(supportFragmentManager, "main")

        }
    }
    object PermissionManager {
        fun checkPermissions(context: Context?, packageName: String?): Boolean {

            //Check if permission enabled
            var appOpsManager: AppOpsManager =
                context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOpsManager.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                packageName.toString()
            )
            val granted = mode == AppOpsManager.MODE_ALLOWED
            return granted
        }
    }
}


