package com.android.example.notification.ui.category

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
import com.android.example.notification.R
import com.android.example.notification.data.DataX
import com.android.example.notification.databinding.FragmentCategoryManagementBinding
import com.android.example.notification.room.data.CategoryData
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
    private lateinit var categoryManagementViewModel: CategoryManagementViewModel

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
        categoryManagementViewModel = ViewModelProvider(this)[CategoryManagementViewModel::class.java]
        activity?.let { categoryManagementViewModel.getCategoryDataList(it.applicationContext) }
    }

    private fun initView(){
        val title = binding.titleSetting
        title.title.text = getString(R.string.category_btn)
        val categoryListView: RecyclerView = binding.categoryList
        categoryManagementViewModel.categoryData.observe(viewLifecycleOwner) {
            var init: (View, DataX) -> Unit = { v:View, d:DataX ->
                var categoryView = v.findViewById<TextView>(R.id.category_tv)
                var colorView=v.findViewById<TextView>(R.id.color_tv)
                categoryView.text = d.category
                colorView.setBackgroundColor(d.color.toColorInt())
            }
            var adapter = context?.let { it1 -> CategoryListViewAdapter(it1,R.layout.item_category_layout,
                it.data.dataList as ArrayList<DataX>,init) }
            categoryListView.layoutManager= LinearLayoutManager(activity)
            categoryListView.adapter=adapter

        }
    }



}