package com.android.example.notification.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentCategoryManagementBinding

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

}