package com.android.example.notification.ui.budget.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
    private var categorySpList: ArrayList<String> = ArrayList()
    private var budgetEditViewModel:BudgetEditViewModel? = null
    private var isBudgetInput = false
    private var isSpinnerSelect = false
    private var mInputBudget = 0
    private var mSpinnerCategory = ""
    private var categoryDelAdapter: BudgetEditListViewAdapter? = null
    private var budgetTableData: BudgetTableData? = null
    var  totalBudget =0
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
        initListView()

        //戻るボタン
        binding.returnBtn.setOnClickListener {
            MainApplication.instance().isEditBudget = true
            findNavController().navigateUp()

        }
        //登録ボタン
        binding.addBtn.setOnClickListener {
            budgetTableData?.let { it1 -> budgetDao?.insert(it1) }
            findNavController().navigateUp()
        }

        //予算額を入力
        budgetInput()

        //カテゴリ名を選択するSpinner
        spinnerInit()

        //[+]カテゴリーAdd
        categoryAddInit()

    }

    private fun initListView(){
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
            categoryDelAdapter = context?.let { it1 ->
                BudgetEditListViewAdapter(it1,R.layout.item_category_delete_layout,
                    budgetListData as ArrayList<BudgetTableData>,init,binding.budgetTotal)
            }
            categoryListView.layoutManager= LinearLayoutManager(activity)
            categoryListView.adapter=categoryDelAdapter


        }
    }

    private fun budgetInput(){
        val budgetInputEdt = binding.categoryEdt
        budgetInputEdt.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Todo
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(s)){
                    isBudgetInput = false
                    binding.addImg.isEnabled = false
                    binding.addImg.isClickable = false
                    binding.addImg.setImageDrawable(resources.getDrawable(R.mipmap.icons_add_enable, null))
                } else {
                    isBudgetInput = true
                    if(isSpinnerSelect){
                        binding.addImg.isEnabled = true
                        binding.addImg.isClickable = true
                        binding.addImg.setImageResource(R.drawable.category_add_bg)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                mInputBudget = budgetInputEdt.text.toString().toInt()
            }

        })
    }

    private fun spinnerInit(){
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
        categorySp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mSpinnerCategory = categorySp.getItemAtPosition(pos).toString()
                isSpinnerSelect = true
                if(isBudgetInput){
                    binding.addImg.isEnabled = true
                    binding.addImg.isClickable = true
                    binding.addImg.setImageResource(R.drawable.category_add_bg)
                }else{
                    binding.addImg.isEnabled = false
                    binding.addImg.isClickable = false
                    binding.addImg.setImageDrawable(resources.getDrawable(R.mipmap.icons_add_enable, null))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }

        }
    }

    private fun categoryAddInit() {
        val plusImg = binding.addImg
        //どちらか一方が 未入力の場合、 ＋ボタンは活性化しない
        if(isBudgetInput && isSpinnerSelect) {
            plusImg.isEnabled = true
            plusImg.isClickable = true
            plusImg.setImageDrawable(resources.getDrawable(R.mipmap.icons_add, null))
        } else {
            plusImg.isEnabled = false
            plusImg.isClickable = false
            plusImg.setImageDrawable(resources.getDrawable(R.mipmap.icons_add_enable, null))
        }
        plusImg.setOnClickListener {
            var category = mSpinnerCategory
            var budgetTotal = mInputBudget
            var budget = 0.0f
            budgetTableData = BudgetTableData(category,budget,budgetTotal)
            categoryDelAdapter?.addCategoryData(budgetTableData!!)
            categoryDelAdapter?.notifyDataSetChanged()
        }
    }

}