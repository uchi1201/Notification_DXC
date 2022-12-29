package com.android.example.notification.ui.notification.division

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.example.notification.MainApplication
import com.android.example.notification.databinding.FragmentNotificationDivisionBinding
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.data.NotificationTableData
import com.android.example.notification.ui.budget.BudgetHorizontalChartViewModel
import com.android.example.notification.ui.notification.NotificationManageViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationDivisionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationDivisionFragment : Fragment() {
    private var _binding: FragmentNotificationDivisionBinding? = null
    private val binding get() = _binding!!
    private  var notificationsViewModel: NotificationDivisionViewModel? = null
    private var mCategory:String = ""
    private var dataBase: NotificationDataBase? = null
    private var categorySpList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationDivisionBinding.inflate(inflater, container, false)
        binding.titleSetting.title.text = "振り分け"
        dataBase = MainApplication.instance().notificationDataBase
        initData()
        initView()
        return binding.root
    }

    private fun initData(){
        notificationsViewModel = ViewModelProvider(this)[NotificationDivisionViewModel::class.java]
        categorySpList = notificationsViewModel?.getCategoryList()!!
    }

    private fun initView() {
        val money = arguments?.getString("money")
        val date1 = arguments?.getString("date")
        val shopName = arguments?.getString("shopName")
        val category = arguments?.getString("category")
        binding.date.text = date1
        binding.moneyEdit.setText(money)
        binding.shopName.text = shopName
        val categorySp = binding.categorySp
        //Spinnerのデータ取得
        var categoryAdapter: ArrayAdapter<String>? =
            context?.let {
                ArrayAdapter(
                    it,android.R.layout.simple_list_item_1,
                    categorySpList)
            }
        //配列アダプタのレイアウトスタイルを設定する
        categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        categorySp.adapter = categoryAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        if(categorySpList.contains(category)) {
            for (index in categorySpList.indices) {
                if (categorySpList[index] == category) {
                    categorySp.setSelection(index)
                    break
                }
            }
        } else {
            if (category != null) {
                categorySpList.add(category)
                 categorySp.setSelection(categorySpList.size-1)
            }

        }
        categorySp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mCategory = categorySp.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }

        }

        binding.addBtn.setOnClickListener {
            val moneyEdit = binding.moneyEdit.text.toString()

            var notificationTableData = NotificationTableData(
                shopName = shopName!!,
                dateTime = date1,
                category = mCategory,
                money = moneyEdit
            )
            dataBase?.notificationDao()?.update(notificationTableData)
            view?.let { it1 -> Navigation.findNavController(it1).navigateUp() }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}