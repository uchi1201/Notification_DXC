package com.android.example.notification.ui.notification

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.notification.MainActivity
import com.android.example.notification.R
import com.android.example.notification.constant.MyConstant.Companion.CHANNEL_ID
import com.android.example.notification.data.NotificationData
import com.android.example.notification.databinding.FragmentNotificationManageBinding
import com.android.example.notification.utils.CustomDialog
import com.android.example.notification.utils.LoadingDialogUtils



/**
 * A simple [Fragment] subclass.
 * Use the [NotificationManageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationManageFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationManageViewModel
    private var mLoadingDialog: Dialog? = null
    private var _binding: FragmentNotificationManageBinding? = null
    private val binding get() = _binding!!
    private lateinit var frequencylist: Array<String>
    private lateinit var frequencylistSub: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationManageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initData()
        initView()
        notificationChannelCreate()
        return root
    }

    override fun onResume() {
        super.onResume()
        val isNotificationChannelEnable = checkNotificationsChannelEnabled(requireContext(),CHANNEL_ID)
        binding.paySwitch.isChecked = isNotificationChannelEnable
    }

    private fun initData(){
        notificationsViewModel =
            ViewModelProvider(this)[NotificationManageViewModel::class.java]
        activity.let {notificationsViewModel.getNotificationsList()}
    }

    private fun initView() {
        //PullDownRefresh時DiaLog表示用
        var loadingDialog = LoadingDialogUtils()
        val swipeRefreshLayout: SwipeRefreshLayout = binding.refresh
        frequencylist = resources.getStringArray(R.array.frequency)
        frequencylistSub= resources.getStringArray(R.array.week)
        val recycleView: RecyclerView = binding.notificationList
        val title = binding.titleSetting
        val payText = binding.payTxt
        //通知名クリックで設定用ポップアップ表示
        payText.setOnClickListener{
            context?.let { it1 -> dialogShow(it1) }
        }
        title.title.text = getString(R.string.notification_btn)
        notificationsViewModel.notificationsListLiveData.observe(viewLifecycleOwner) {
            var init: (View, NotificationData) -> Unit = { v: View, d: NotificationData ->
                var dateView = v.findViewById<TextView>(R.id.date)
                var shopNameView = v.findViewById<TextView>(
                    R.id.shop_name_txt
                )
                var categoryView = v.findViewById<TextView>(R.id.category_tx)
                var moneyView = v.findViewById<TextView>(R.id.money_tx)

                dateView.text = d.date
                shopNameView.text = d.shopName
                categoryView.text = d.category
                moneyView.text = d.money
            }
            if (it.getOrNull() != null) {
                binding.notificationList.visibility = View.VISIBLE
                binding.errorMsg.visibility = View.GONE
                var adapter = it.getOrNull()?.let { it1 ->
                    NotificationListViewAdapter(
                        R.layout.notification_item,
                        it1.notificationList, init
                    )
                }
                recycleView.layoutManager = LinearLayoutManager(activity)
                recycleView.adapter = adapter
            } else {
                binding.notificationList.visibility = View.GONE
                binding.errorMsg.visibility = View.VISIBLE
            }
        }
        notificationsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                mLoadingDialog = loadingDialog.createLoadingDialog(activity, "Loading")
            } else {
                loadingDialog.closeDialog(mLoadingDialog)
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            notificationsViewModel.getPTRNotificationsList()
        }
        notificationsViewModel.pullToRefreshLiveData.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = it
        }
     }

    private fun notificationChannelCreate(){
        // Create the NotificationChannel
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        //通知権限を有効にするかどうか
        val isNotificationEnable = checkNotificationsEnabled()
        if(!isNotificationEnable){
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, "com.android.example.notification")
                putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID)
            }
            startActivity(intent)
        }
        val isNotificationChannelEnable = checkNotificationsChannelEnabled(requireContext(),CHANNEL_ID)
        //スイッチ設定
        binding.paySwitch.isChecked = isNotificationChannelEnable
        binding.paySwitch.setOnClickListener {
        //通知権限がなし場合、システムに設定いく
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, "com.android.example.notification")
                putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID)
            }
            startActivity(intent)

        }
    }
    /**
     * 通知が有効かどうかを判断する（単一のメッセージチャネルではない）
     * @return true 開ける
     */
    private fun checkNotificationsEnabled(): Boolean {
        val notificationManagerCompat =
            NotificationManagerCompat.from(requireContext())
        return notificationManagerCompat.areNotificationsEnabled()
    }
      /**
     * 通知チャネルがオープンしているかどうかを判断する（単一のメッセージチャネル）
     * @param context
     * @param channelID チャネル id
     * @return true 開ける
     */
    private fun checkNotificationsChannelEnabled(context: Context, channelID: String?): Boolean {
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = manager.getNotificationChannel(channelID)
        return channel.importance != NotificationManager.IMPORTANCE_NONE
    }

    fun sendNotification(channelId: String?, title: String?, content: String?, intent: Intent?) {
        var pendingIntent: PendingIntent? = null
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (intent != null) {
            pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
        val notification: Notification = Notification.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }

    private fun dialogShow(context:Context){
        var customDialog = CustomDialog()
        val sp: SharedPreferences = context.getSharedPreferences("sp_name", Context.MODE_PRIVATE)
        val freqIndex = sp.getInt("freqIndex", 0)
        val freqIndexSub = sp.getInt("freqIndexSub", 0)
        val frequencyDialog = customDialog.createDialog(freqIndex,freqIndexSub,context,R.style.CustomDialog)
        customDialog.settingInfo(context,frequencyDialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}