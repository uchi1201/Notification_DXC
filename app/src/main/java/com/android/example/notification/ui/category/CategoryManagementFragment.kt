package com.android.example.notification.ui.category

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentCategoryManagementBinding
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.dao.CategoryDao
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.utils.CategoryAddDialog
import com.android.example.notification.utils.ColorChangeDialog
import com.android.example.notification.utils.LoadingDialogUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryManagementFragment : Fragment() {
    private var _binding: FragmentCategoryManagementBinding? = null
    private val binding get() = _binding!!
    private var categoryManagementViewModel: CategoryManagementViewModel?= null
    private var dataBase: MyDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryManagementBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initData()
        initView()
        return root
    }

    private fun initData(){
        dataBase = context?.let { Room.databaseBuilder(it, MyDataBase::class.java,"myCategory.db").allowMainThreadQueries().build() }
        categoryManagementViewModel = dataBase?.let { CategoryManagementViewModel(it) }
        if(categoryManagementViewModel?.categoryDbData?.size == 0){
            //DBのデータがない時データ追加
            activity?.let { categoryManagementViewModel?.insertDataBaseData(it.applicationContext) }
        }
        categoryManagementViewModel?.getAllCategoryData()

    }

    override fun onResume() {
        super.onResume()
        if(categoryManagementViewModel?.categoryDbData?.size == 0){
            //DBのデータがない時データ追加
            activity?.let { categoryManagementViewModel?.insertDataBaseData(it.applicationContext) }
        }
    }

    private fun initView(){
        val title = binding.titleSetting
        title.title.text = getString(R.string.category_btn)
        val categoryListView: RecyclerView = binding.categoryList

        var init: (View, CategoryData) -> Unit = { v:View, d:CategoryData ->
            var categoryView = v.findViewById<TextView>(R.id.category_tv)
            var colorView=v.findViewById<TextView>(R.id.color_tv)
            categoryView.text = d.category
            colorView.setBackgroundColor(d.color.toColorInt())
        }
        var adapter = context?.let { it1 ->
            categoryManagementViewModel?.categoryDbData?.let {
                CategoryListViewAdapter(it1,dataBase,R.layout.item_category_layout,
                    it,init)
            }
        }
        categoryListView.layoutManager= LinearLayoutManager(activity)
        categoryListView.adapter=adapter

        val addBtn = binding.addImg

        addBtn.setOnClickListener {
            val addCategoryAddDialog = CategoryAddDialog()
            var colorList: ArrayList<String> = arrayListOf()
            if (adapter != null) {
                for(item in adapter.items){
                    colorList.add(item.color.uppercase())
                }
            }
            val colors = ColorChangeDialog().getColors(colorList)
            if (!colors.isNullOrEmpty()){
                addCategoryAddDialog.createAddCategoryDialog(context,colorList)
                addCategoryAddDialog.setAddCategoryButtonClickListener(object :
                    CategoryAddDialog.OnAddCategoryButtonClickListener {
                    override fun onAddCategoryButtonClick(categoryData: CategoryData?) {
                        if (categoryData != null) {
                            adapter?.setCategoryData(categoryData)
                        }
                    }
                })
            } else {
                AlertDialog.Builder(context)
                    .setMessage("登録上限に達したように、登録できません！")
                    .setTitle("エラー発生")
                    .setNeutralButton("取消", null)
                    .create()
                    .show()
            }

        }

    }

}