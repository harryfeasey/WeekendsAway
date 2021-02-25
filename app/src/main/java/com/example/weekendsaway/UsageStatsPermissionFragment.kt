package com.example.weekendsaway

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 */
class UsageStatsPermissionFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        isCancelable = true
        retainInstance = true
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usage_stats_permission, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val enableButton = view.findViewById<Button>(R.id.enableButton)
        enableButton.setOnClickListener{

            Log.i("main", "Permission to get stats denied")
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        Log.i("frag","onResume Called")
        val granted = MainActivity.PermissionManager.checkPermissions(activity?.applicationContext, activity?.packageName)
        if (granted) {
            dismiss()
        }
    }


}