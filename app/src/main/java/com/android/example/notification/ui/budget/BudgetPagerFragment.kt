package com.android.example.notification.ui.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.example.notification.databinding.FragmentBudgetPagerBinding
import com.android.example.notification.ui.budget.BudgetFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetPagerFragment : Fragment() {
    private var _binding: FragmentBudgetPagerBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetPagerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // ページインスタンスを用意
        val pagerAdapter = PagerAdapter(requireActivity())
        // セット
        binding.fragmentHomePager.adapter = pagerAdapter


        TabLayoutMediator(binding.fragmentHomeTabLayout, binding.fragmentHomePager) { _, _ -> }.attach()
        return root
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // ページ数を取得
        override fun getItemCount(): Int = 2

        // スワイプ位置を引数にしてFragment生成する
        override fun createFragment(position: Int): Fragment {
            var fragment = when(position){
                0->BudgetHorizontalChartFragment()
                1->BudgetFragment()
                else ->BudgetFragment()
            }
            return fragment
        }

    }
}