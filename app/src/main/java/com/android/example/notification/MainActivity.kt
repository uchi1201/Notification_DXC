package com.android.example.notification


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.android.example.notification.databinding.ActivityMainBinding
import com.android.example.notification.ui.notification.NotificationManageFragment
import com.android.example.notification.ui.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_schedule,
                R.id.navigation_point,
                R.id.navigation_budget,
                R.id.navigation_setting
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        checkPushSwitchStatus()
    }

    private fun onAlertDialog(context: Context) {
        //Instantiate builder variable

        val builder = AlertDialog.Builder(this)
        // set title
        builder.setTitle("系統通知権限")

        //set content area
        builder.setMessage("通知権限が開かれていないので、クリックして開く")

        //set negative button
        builder.setPositiveButton(
            "開ける") { dialog, _ ->
            val intent = Intent()
            try {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, applicationInfo.uid)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.putExtra("package", packageName)
                startActivity(intent)
            }
            dialog.cancel()
        }


        //set positive button
        builder.setNegativeButton(
            "取消") { dialog, _ ->
            // User cancelled the dialog
            dialog.cancel()
        }


        builder.show()
    }

    private fun checkPushSwitchStatus() {
        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)
        val isOpened = notificationManager.areNotificationsEnabled()
        if(!isOpened) {
            onAlertDialog(this)
        }
    }

}