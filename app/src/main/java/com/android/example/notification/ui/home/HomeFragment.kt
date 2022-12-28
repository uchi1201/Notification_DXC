package com.android.example.notification.ui.home

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
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentHomeBinding
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.dao.NotificationDao
import com.android.example.notification.room.data.NotificationTableData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.ui.notification.NotificationListViewAdapter
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var notificationDao: NotificationDao? = null
    private var dataBase: NotificationDataBase? = null
    private var notificationsListData = mutableListOf<NotificationTableData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBase = MainApplication.instance().notificationDataBase
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val title = binding.titleHome
        title.title.text = "ホーム"

        initData()
        initView()

        return root
    }

    private fun initData(){
        notificationDao = dataBase?.notificationDao()
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun initView(){

    }
    override fun onResume() {
        super.onResume()
        notificationDataSet()
    }
    private fun notificationDataSet(){
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
            //Roomデータベースに追加
            notificationDao?.insert(notificationTableData)
        }
        //Roomデータベースからデータを取得
        notificationsListData = notificationDao?.getAll() as MutableList<NotificationTableData>
        if(notificationsListData.isNotEmpty()){
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
                when(d.category){
                    "水道" -> cardView.strokeColor = context?.getColor(R.color.blue)!!
                    "食費" -> cardView.strokeColor = context?.getColor(R.color.red)!!
                    "その他" -> cardView.strokeColor = context?.getColor(R.color.gray)!!
                    else -> cardView.strokeColor = context?.getColor(R.color.gray)!!
                }

            }
            binding.notificationList.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.GONE
            var adapter =
                NotificationListViewAdapter(
                    R.layout.notification_item,
                    notificationsListData as ArrayList<NotificationTableData>, init
                )
            binding.notificationList.layoutManager = LinearLayoutManager(activity)
            binding.notificationList.adapter = adapter
            adapter.setRecyclerItemClickListener(object :
                BaseRecycleViewAdapter.OnRecyclerItemClickListener {
                override fun onRecyclerItemClick(view: View, Position: Int) {
                    val bundle = bundleOf(
                        "money" to adapter.items[Position].money,
                        "date" to adapter.items[Position].dateTime,
                        "shopName" to adapter.items[Position].shopName,
                        "category" to adapter.items[Position].category
                    )
                    findNavController().navigate(R.id.division_action,bundle)
                }
            })
        } else{
            binding.notificationList.visibility = View.GONE
            binding.errorMsg.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}