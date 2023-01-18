package com.android.example.notification.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentHomeBinding
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.dao.NotificationDao
import com.android.example.notification.room.data.NotificationTableData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.ui.notification.NotificationListViewAdapter
import com.android.example.notification.ui.notification.NotificationManageViewModel
import com.android.example.notification.utils.LoadingDialogUtils
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //DB呼び出すDao定義
    private var notificationDao: NotificationDao? = null
    //Push通知のDBの定義
    private var dataBase: NotificationDataBase? = null
    //Push通知のメッセージデータリスト
    private var notificationsListData = mutableListOf<NotificationTableData>()
    private  var homeViewModel: HomeViewModel? = null
    //読み込み中…ダイアログ
    private var mLoadingDialog: Dialog? = null
    //今月の利用額
    private var currentMoney: Int? = 0
    //残り額
    private var totalBudget = "0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //DBの取得
        dataBase = MainApplication.instance().notificationDataBase
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //タイトル設定
        val title = binding.titleHome
        title.title.text = "ホーム"

        initData()
        initView()

        return root
    }


    private fun initData(){
        //通知のDBのDao設定
        notificationDao = dataBase?.notificationDao()
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        //今月の利用額の初期化
        currentMoney = 0
    }

    private fun initView(){
        //読み込み中時DiaLog表示用
        var loadingDialog = LoadingDialogUtils()
        //loadingLiveDataにより、読み込み中時DiaLog表示するかを判断
        homeViewModel?.loadingLiveData?.observe(viewLifecycleOwner) {
            if (it) {
                mLoadingDialog = loadingDialog.createLoadingDialog(activity, "Loading")
            } else {
                loadingDialog.closeDialog(mLoadingDialog)
            }
        }
        //PullDownデータの更新
        val swipeRefreshLayout: SwipeRefreshLayout = binding.refresh
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel?.getPTRNotificationsList()
        }
        //回転等のアニメーションアイコンを表示するか設定
        homeViewModel?.pullToRefreshLiveData?.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = it
        }
        //今月の利用額を変更時、データの表示更新
        homeViewModel?.currentMoney?.observe(viewLifecycleOwner){
            binding.moneyEdit.setText(it)
        }
        //残り額を変更時、データの表示更新
        homeViewModel?.remainMoney?.observe(viewLifecycleOwner){
            binding.remainMoney.text = it+"円"
        }
        //通知送信テスト
        binding.notificationSend.setOnClickListener {
            findNavController().navigate(R.id.notification_test)
        }
    }

    override fun onResume() {
        super.onResume()
        //SharedPreferencesから予算編集画面で設定した予算総額を取得する
        totalBudget =
            context?.let { MainApplication.instance().getData(it,"totalBudgetValue").toString() }.toString()
        //プッシュ通知を設定表示
        notificationDataSet()

    }

    /**
     * firebaseから取得したメッセージデータを設定、
     * ホーム画面のリストItem設定
     * DBのデータを追加
     */
    private fun notificationDataSet(){

        //fireBaseのPush通知のメッセージデータを取得
        val money = arguments?.getString("money")
        val date1 = arguments?.getString("date")
        val address = arguments?.getString("address")
        val category = arguments?.getString("category")
        if(arguments != null) {
            var notificationTableData = NotificationTableData(
                shopName = address!!,
                dateTime = date1,
                category = category,
                money = money
            )
            //取得したメッセージデータをRoomデータベースに追加
            notificationDao?.insert(notificationTableData)
            //Push通知がなし場合、データを重複追加するためにargumentsがNullを設定
            arguments = null
        }
        //Roomデータベースからデータを取得
        notificationsListData = notificationDao?.getAll() as MutableList<NotificationTableData>
        //今月の利用額と残りの表示
        for(i in notificationsListData.indices) {
            //通知のリストから金額を抽出して計算する
            currentMoney = notificationsListData[i].money?.toInt()
                ?.let { currentMoney?.plus(it) }
        }
        //ViewModelの可変データ設定
        homeViewModel?.getCurrentMoney(currentMoney.toString())
        homeViewModel?.getRemainMoney(totalBudget, currentMoney.toString())
        //取得したメッセージデータがある場合、リストView表示とリスト設定
        if(notificationsListData.isNotEmpty()){
            binding.notificationList.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.GONE
            notificationsListShow()

        } else{
            //取得したメッセージデータがない場合、エラー提示メッセージを表示
            binding.notificationList.visibility = View.GONE
            binding.errorMsg.visibility = View.VISIBLE
        }
    }

    /**
     * 支払通知リスト表示
     */
    private fun notificationsListShow(){
        //リストItem表示設定
        var init: (View, NotificationTableData) -> Unit = { v: View, d: NotificationTableData ->
            var cardView = v.findViewById<MaterialCardView>(R.id.card_view)
            var dateView = v.findViewById<TextView>(R.id.date)
            var shopNameView = v.findViewById<TextView>(R.id.shop_name_txt)
            var categoryView = v.findViewById<TextView>(R.id.category_tx)
            var moneyView = v.findViewById<TextView>(R.id.money_tx)
            dateView.text = d.dateTime
            shopNameView.text = d.shopName
            categoryView.text = d.category
            moneyView.text = d.money+"円"
            //カテゴリーにより、色を設定
            // 青＝水道
            //赤＝食費
            //灰＝その他
            when(d.category){
                "水道" -> cardView.strokeColor = context?.getColor(R.color.blue)!!
                "食費" -> cardView.strokeColor = context?.getColor(R.color.red)!!
                "その他" -> cardView.strokeColor = context?.getColor(R.color.gray)!!
                else -> cardView.strokeColor = context?.getColor(R.color.gray)!!
            }

        }
        //リストのアダプター設定
        var adapter =
            NotificationListViewAdapter(
                R.layout.notification_item,
                notificationsListData as ArrayList<NotificationTableData>, init
            )
        binding.notificationList.layoutManager = LinearLayoutManager(activity)
        binding.notificationList.adapter = adapter
        //Itemの押下イベント処理
        adapter.setRecyclerItemClickListener(object :
            BaseRecycleViewAdapter.OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(view: View, Position: Int) {
                val bundle = bundleOf(
                    "money" to adapter.items[Position].money,
                    "date" to adapter.items[Position].dateTime,
                    "shopName" to adapter.items[Position].shopName,
                    "category" to adapter.items[Position].category
                )
                //該当Itemのデータを振り分け画面に伝える
                findNavController().navigate(R.id.home_to_division_action,bundle)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}