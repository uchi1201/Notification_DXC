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
import com.android.example.notification.MainApplication
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
    private var dataBase:MyDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryManagementBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //DBを取得
        dataBase = MainApplication.instance().categoryDataBase
        initData()
        initView()
        return root
    }

    private fun initData(){
        categoryManagementViewModel = dataBase?.let { CategoryManagementViewModel(it) }
        if(categoryManagementViewModel?.categoryDbData?.size == 0){
            //DBのデータがない時データ追加
            activity?.let { categoryManagementViewModel?.insertDataBaseData(it.applicationContext) }
        }
        //DBから全部データを取得
        categoryManagementViewModel?.getAllCategoryData()
    }


    private fun initView(){
        val title = binding.titleSetting
        title.title.text = getString(R.string.category_btn)
        val categoryListView: RecyclerView = binding.categoryList
        //カテゴリーのリストItemのレイアウト項目表示とデータ設定
        var init: (View, CategoryData) -> Unit = { v:View, d:CategoryData ->
            var categoryView = v.findViewById<TextView>(R.id.category_tv)
            var colorView=v.findViewById<TextView>(R.id.color_tv)
            categoryView.text = d.category
            colorView.setBackgroundColor(d.color.toColorInt())
        }
        //アダプターを設定（アイテムのitem_category_layout、とinitのレイアウト項目表示とデータを伝える）
        var adapter = context?.let { it1 ->
            categoryManagementViewModel?.categoryDbData?.let {
                CategoryListViewAdapter(it1,dataBase,R.layout.item_category_layout,
                    it,init)
            }
        }
        categoryListView.layoutManager= LinearLayoutManager(activity)
        categoryListView.adapter=adapter

        val addBtn = binding.addImg
        //カテゴリー管理画面のAddボタンイベント処理
        //新規登録していくと、選択できる色が減っていき（ポップアップに表示する色が動的に変化し）
        addBtn.setOnClickListener {
            //色登録で出るポップアップ
            val addCategoryAddDialog = CategoryAddDialog()
            var colorList: ArrayList<String> = arrayListOf()
            if (adapter != null) {
                for(item in adapter.items){
                    //リスト既に存在の色を抽出
                    colorList.add(item.color.uppercase())
                }
            }
            //リスト存在以外の色を取得
            val colors = ColorChangeDialog().getColors(colorList)
            if (!colors.isNullOrEmpty()){
                //カテゴリー登録ダイアログを表示
                addCategoryAddDialog.createAddCategoryDialog(context,colorList)
                //カテゴリー登録ダイアログの登録ボタンのイベント処理
                addCategoryAddDialog.setAddCategoryButtonClickListener(object :
                    CategoryAddDialog.OnAddCategoryButtonClickListener {
                    override fun onAddCategoryButtonClick(categoryData: CategoryData?) {
                        if (categoryData != null) {
                            //登録した後リストの表示とDBデータの更新
                            adapter?.setCategoryData(categoryData)
                        }
                    }
                })
            } else {
                //登録上限に達した、エラー発生ダイアログを表示
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