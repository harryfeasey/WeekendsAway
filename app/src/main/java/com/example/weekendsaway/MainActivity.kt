package com.example.weekendsaway

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weekendsaway.MainActivity.PermissionManager.checkPermissions
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mUsageStatsManager: UsageStatsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val granted = checkPermissions(applicationContext, packageName)

        if (!granted) {
            //Navigate to screen explaining why it should be enabled, with button to bring up settings.
            val dialogFragment = UsageStatsPermissionFragment()
            dialogFragment.show(supportFragmentManager, "main")

        }

        mUsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        printUsageStats(getDailyStats())


    }

    fun getDailyStats(): List<UsageStats>{
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_WEEK, -1)
        val queryUsageStats: List<UsageStats> = mUsageStatsManager
                .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(),
                        System.currentTimeMillis())

        return queryUsageStats;
    }


    fun printUsageStats(usageStatsList: List<UsageStats>) {
        for (u in usageStatsList) {
            Log.d("TAG", "Pkg: " + u.packageName + "\t" + "ForegroundTime: "
                    + u.totalTimeInForeground)
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


