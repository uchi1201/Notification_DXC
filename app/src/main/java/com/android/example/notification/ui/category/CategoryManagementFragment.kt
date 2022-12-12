package com.android.example.notification.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentCategoryManagementBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryManagementFragment : Fragment() {
    private var _binding: FragmentCategoryManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryManagementBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        return root
    }

    private fun initView(){
        val title = binding.titleSetting
        title.title.text = getString(R.string.category_btn)

    }

    private fun setupItemTouchHelper() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, selected: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
//                val from = selected.adapterPosition
//                val to = target.adapterPosition
//                Collections.swap(list, from, to)
//                recyclerView.adapter?.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                list.removeAt(viewHolder.adapterPosition)
//                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
//        helper.attachToRecyclerView(recyclerView)
    }

}