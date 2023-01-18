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
        //タイトル設定
        binding.titleSetting.title.text = "振り分け"
        //DBを取得
        dataBase = MainApplication.instance().notificationDataBase
        initData()
        initView()
        return binding.root
    }

    /**
     * データを初期化
     */
    private fun initData(){
        notificationsViewModel = ViewModelProvider(this)[NotificationDivisionViewModel::class.java]
        //DB中の全部データを検索
        categorySpList = notificationsViewModel?.getCategoryList()!!
    }

    private fun initView() {
        //遷移元画面からデータを取得設定
        val money = arguments?.getString("money")
        val date1 = arguments?.getString("date")
        val shopName = arguments?.getString("shopName")
        val category = arguments?.getString("category")
        binding.date.text = date1
        binding.moneyEdit.setText(money)
        binding.shopName.text = shopName
        //カテゴリー選択Spinner
        val categorySp = binding.categorySp
        //Spinnerのアダプター設定（Itemのデータとレイアウト設定）
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
        //DB中データのカテゴリーに遷移元画面からもらうカテゴリーを含めているかを判断
        if(categorySpList.contains(category)) {
            //含めている場合、Spinnerの表示設定
            for (index in categorySpList.indices) {
                if (categorySpList[index] == category) {
                    categorySp.setSelection(index)
                    break
                }
            }
        } else {
            //含めていない場合、遷移元画面からもらうカテゴリーを追加表示
            if (category != null) {
                categorySpList.add(category)
                 categorySp.setSelection(categorySpList.size-1)
            }

        }
        //カテゴリーSpinnerを選択イベント処理
        categorySp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                //選択したカテゴリーを保存して登録用
                mCategory = categorySp.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }

        }
        //データを登録
        if (shopName != null) {
            if (date1 != null) {
                categoryAdd(shopName,date1)
            }
        }

    }

    /**
     * 選択したカテゴリーを登録
     */
    private fun categoryAdd(shopName: String,date:String){
        //登録ボタンを押下イベント処理
        binding.addBtn.setOnClickListener {
            val moneyEdit = binding.moneyEdit.text.toString()
            //画面のデータを取得設定
            var notificationTableData = NotificationTableData(
                shopName = shopName!!,
                dateTime = date,
                category = mCategory,
                money = moneyEdit
            )
            //DBのデータを更新
            dataBase?.notificationDao()?.update(notificationTableData)
            //遷移元画面へ遷移
            view?.let { it1 -> Navigation.findNavController(it1).navigateUp() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}