package com.android.example.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.android.example.notification.constant.MyConstant
import com.android.example.notification.databinding.FragmentHomeBinding
import com.android.example.notification.databinding.FragmentNotificationSendTestBinding
import com.android.example.notification.room.data.BudgetTableData

class NotificationSendTestFragment : Fragment() {
    private var _binding: FragmentNotificationSendTestBinding? = null
    private val binding get() = _binding!!
    private var money:String?=null
    private var shopName:String = ""
    private var category:String?=null
    private var date:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationSendTestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var moneyInput = binding.money
        var messageData: MutableMap<String, String> = mutableMapOf()
        moneyInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Todo
            }
            //Edittextの値が変化中に処理
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Todo
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    money = moneyInput.text.toString()
                    messageData["money"] = money!!
                }catch (e:Exception){
                    Toast.makeText(context,"エラーを発生しまいした、入力値がNullできない。", Toast.LENGTH_SHORT).show()
                }

            }

        })
        var dateInput = binding.date
        dateInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Todo
            }
            //Edittextの値が変化中に処理
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Todo
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    date = dateInput.text.toString()
                    messageData["date"] = date!!
                }catch (e:Exception){
                    Toast.makeText(context,"エラーを発生しまいした、入力値がNullできない。", Toast.LENGTH_SHORT).show()
                }
            }

        })
        var shopNameInput = binding.shopName
        shopNameInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Todo
            }
            //Edittextの値が変化中に処理
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Todo
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                shopName = shopNameInput.text.toString()
                messageData["shopName"] = shopName!!
                }catch (e:Exception){
                    Toast.makeText(context,"エラーを発生しまいした、入力値がNullできない。", Toast.LENGTH_SHORT).show()
                }
            }

        })
        var categoryInput = binding.categoryName
        categoryInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Todo
            }
            //Edittextの値が変化中に処理
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Todo
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    category = categoryInput.text.toString()
                    messageData["category"] = category!!
                }catch (e:Exception){
                    Toast.makeText(context,"エラーを発生しまいした、入力値がNullできない。", Toast.LENGTH_SHORT).show()
                }

            }

        })
        binding.notificationSend.setOnClickListener {
            sendNotification(messageData,"支払通知","支払通知テスト実施")
            findNavController().navigateUp()
        }

        return root
    }
    /**
     * 通知を受け取って遷移先画面のデータ設定
     * @param context Context
     * @param messageData Map<String, String>?
     * @return PendingIntent?
     */
    private fun getPendingIntent(context: Context, messageData:Map<String, String>?): PendingIntent? {
        //目的画面のId
        val destId: Int = R.id.navigation_home
        /*
        支払通知のメッセージデータ（お金、時間、店舗名、カテゴリー）
        bundleに保存、遷移先画面にデータを取得用
         */
        val bundle = Bundle()
        bundle.putString("money",messageData?.get("money").toString())
        bundle.putString("date",messageData?.get("date").toString())
        bundle.putString("address",messageData?.get("shopName").toString())
        bundle.putString("category",messageData?.get("category").toString())
        //目的画面へ遷移
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .addDestination(destId)
            .setArguments(bundle)
            .createPendingIntent()
    }

    private fun sendNotification(messageData:Map<String, String>?,messageTitle:String,messageBody:String) {
        //Appのpackageと表示したいレイアウトを伝え設定
        val views = RemoteViews("com.android.example.notification", R.layout.layout_notification)
        views.setTextViewText(R.id.date,messageData?.get("date"))
        views.setTextViewText(R.id.shop_name_txt,messageData?.get("address"))
        views.setTextViewText(R.id.category_tx,messageData?.get("category"))
        views.setTextViewText(R.id.money_tx,messageData?.get("money")+"円")
        //取得遷移先のIntent
        val penIntent = context?.let { getPendingIntent(it,messageData) }
        //支払通知ChannelIdを設定
        val notificationBuilder = context?.let {
            NotificationCompat.Builder(it, MyConstant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)//通知表示アイコン
                .setContentTitle(messageTitle)//通知表示のタイトル
                .setContentText(messageBody)//Push通知のメッセージボディー
                .setCustomBigContentView(views)//レイアウトのビュー
                .setAutoCancel(true)
                .setContentIntent(penIntent)
        }

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        notificationManager.notify(MyConstant.CHANNEL_ID, 1, notificationBuilder?.build())
    }

}