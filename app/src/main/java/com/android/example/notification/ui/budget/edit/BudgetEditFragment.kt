package com.android.example.notification.ui.budget.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentBudgetBinding
import com.android.example.notification.databinding.FragmentBudgetEditBinding


/**
 * A simple [Fragment] subclass.
 * Use the [BudgetEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetEditFragment : Fragment() {
    private var _binding: FragmentBudgetEditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

}