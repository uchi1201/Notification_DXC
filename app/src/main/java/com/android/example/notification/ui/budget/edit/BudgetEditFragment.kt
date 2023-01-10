package com.android.example.notification.ui.budget.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentBudgetEditBinding
import com.android.example.notification.room.BudgetDataBase
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.dao.BudgetDao
import com.android.example.notification.room.dao.NotificationDao
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.room.data.NotificationTableData
import com.android.example.notification.ui.category.CategoryListViewAdapter
import com.android.example.notification.ui.notification.division.NotificationDivisionViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [BudgetEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetEditFragment : Fragment() {
    private var _binding: FragmentBudgetEditBinding? = null
    private val binding get() = _binding!!
    private var budgetDao: BudgetDao? = null
    private var dataBase: BudgetDataBase? = null
    private var budgetListData = mutableListOf<BudgetTableData>()
    private var budgetData : BudgetTableData? = null
    private var totalBudget = 0
    private var categorySpList: ArrayList<String> = ArrayList()
    private var budgetEditViewModel:BudgetEditViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetEditBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initData()
        initView()

        return root
    }

    private fun initData(){
        dataBase =  MainApplication.instance().budgetDataBase
        budgetDao = dataBase?.budgetDao()
        budgetListData = budgetDao?.getAll() as MutableList<BudgetTableData>
        budgetEditViewModel = ViewModelProvider(this)[BudgetEditViewModel::class.java]
        categorySpList = budgetEditViewModel?.getCategoryList()!!
    }

    private fun initView(){
        val monthTile = arguments?.getString("month")
        binding.titleEdit.title.text = monthTile + "月"
        //カテゴリー、予算額のリスト表示
        if(budgetListData.isEmpty()){
            binding.categoryRv.visibility=View.GONE
            binding.errorMsg.visibility=View.VISIBLE

        } else {
            binding.categoryRv.visibility=View.VISIBLE
            binding.errorMsg.visibility=View.GONE
            val categoryListView: RecyclerView = binding.categoryRv
            //カテゴリーのリスト表示
            var init: (View, BudgetTableData) -> Unit = { v:View, d: BudgetTableData ->
                var categoryTv = v.findViewById<TextView>(R.id.category_tv)
                categoryTv.text = d.category
                var budgetEdt=v.findViewById<EditText>(R.id.budget_edt)
                budgetEdt.setText(d.budgetTotal.toString())

            }
            var adapter = context?.let { it1 ->
                BudgetEditListViewAdapter(it1,R.layout.item_category_delete_layout,
                    budgetListData as ArrayList<BudgetTableData>,init)
            }
            categoryListView.layoutManager= LinearLayoutManager(activity)
            categoryListView.adapter=adapter

            binding.budgetTotal.text = totalBudget.toString()
        }
        //戻るボタン
        binding.returnBtn.setOnClickListener {
            MainApplication.instance().isEditBudget = true
            findNavController().navigateUp()

        }
        //登録ボタン
        binding.addBtn.setOnClickListener {
            budgetData?.let { it1 -> budgetDao?.insert(it1) }
        }
        //予算額を入力
        val budgetInput = binding.categoryEdt

        //カテゴリ名を選択するSpinner
        val categorySp = binding.categorySp
        //Spinnerのデータ取得
        var categoryAdapter: BudgetCategorySpArrayAdapter<String>? =
            context?.let {
                BudgetCategorySpArrayAdapter(
                    it,android.R.layout.simple_list_item_1,
                    categorySpList)
            }
        //配列アダプタのレイアウトスタイルを設定する
        categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //ドロップダウンボックスの配列アダプタの設定
        categorySp.adapter = categoryAdapter
        categorySp.setSelection(categorySpList.size-1,true)
        //[+]カテゴリーAdd
        val plusImg = binding.addImg
        if (categoryAdapter != null) {
            if(budgetInput.text.isNotBlank() && categoryAdapter.isInput) {
                plusImg.isEnabled = true
                plusImg.setImageDrawable(resources.getDrawable(R.mipmap.icons_add, null))
            }
        }


    }


}