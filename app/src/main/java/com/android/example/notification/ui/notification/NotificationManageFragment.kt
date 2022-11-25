package com.android.example.notification.ui.notification

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.notification.R
import com.android.example.notification.data.BudgetData
import com.android.example.notification.data.NotificationData
import com.android.example.notification.databinding.FragmentBudgetBinding
import com.android.example.notification.databinding.FragmentNotificationManageBinding
import com.android.example.notification.databinding.FragmentSettingBinding
import com.android.example.notification.ui.budget.BudgetListViewAdapter
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

        return root
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
    private fun dialogShow(context:Context){
        CustomDialog(context,R.style.CustomDialog).createDialog(R.style.CustomDialog,frequencylist,frequencylistSub)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}