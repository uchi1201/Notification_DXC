package com.android.example.notification.ui.budget

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.data.BudgetData
import com.android.example.notification.databinding.FragmentBudgetBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null

    private val binding get() = _binding!!
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var month: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initData()
        initView()
        month = MainApplication.instance().spinnerMonth
        initSpinner()
        return root
    }

    override fun onResume() {
        super.onResume()

        if (month != MainApplication.instance().spinnerMonth){
            initData()
            initView()
            initSpinner()
        }

    }

    private fun initSpinner() {
        //ドロップダウンリストの配列アダプタを宣言する
        val monthArray: Array<String> = resources.getStringArray(R.array.month)
        var starAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.item_select_layout,monthArray)
        //配列アダプタのレイアウトスタイルを設定する
        starAdapter.setDropDownViewResource(R.layout.item_dropdown_layout)

        val sp: Spinner = binding.spinner
        //ドロップダウンボックスの配列アダプタの設定
        sp.adapter = starAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        sp.setSelection((MainApplication.instance().spinnerMonth.toInt()-1))
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                MainApplication.instance().spinnerMonth = (pos+1).toString()
                budgetViewModel.getPieData(MainApplication.instance().spinnerMonth)
                budgetViewModel.getListData((MainApplication.instance().spinnerMonth.toInt()-1).toString())
                initView()
                month = MainApplication.instance().spinnerMonth
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }
        }
    }
    private fun initData(){
         budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
        activity?.let { budgetViewModel.getPieData(MainApplication.instance().spinnerMonth) }
        activity?.let { budgetViewModel.getListData(MainApplication.instance().spinnerMonth) }
    }

    private fun initView(){
        val recycleView: RecyclerView = binding.listView
        budgetViewModel.budgetData.observe(viewLifecycleOwner){
            var init: (View, BudgetData) -> Unit = { v:View, d:BudgetData ->
                var categoryView = v.findViewById<TextView>(R.id.category)
                var budgetView=v.findViewById<TextView>(R.id.budget)
                var percentageView=v.findViewById<TextView>(R.id.percentage)

                categoryView.text = d.category
                budgetView.text = d.budget
                percentageView.text = d.percentage
            }
            var adapter = BudgetListViewAdapter(
                R.layout.budget_list_item,
                it as ArrayList<BudgetData>,init)
            recycleView.layoutManager= LinearLayoutManager(activity)
            recycleView.adapter=adapter
        }

        val pieChart: PieChart = binding.pieChart
        budgetViewModel.pieData.observe(viewLifecycleOwner) {
            //⑤PieChartにPieData格納
            pieChart.data = it
            //labelの文字カラー
            it.setValueTextColor(Color.WHITE)
            //labelの文字サイズ
            it.setValueTextSize(10f)

            //descriptionラベルを非表示
            pieChart.description.isEnabled = false
            //画面に入ると円を描くアニメーション
            pieChart.animateY(800, Easing.EaseInOutQuad)
            //タッチ操作無効
            pieChart.setTouchEnabled(false)
            val legend: Legend = pieChart.legend
            legend.isEnabled = false
            //⑦PieChart更新
            pieChart.invalidate()
        }
        refreshData()
    }

    private fun CoroutineScope.doDelayed(timeMillis: Long, block: suspend () -> Unit) = this.launch {
        delay(timeMillis)
        block.invoke()
    }

    private fun refreshData(){
        val swipeRefreshLayout: SwipeRefreshLayout = binding.refresh
        swipeRefreshLayout.setOnRefreshListener {
            initView()
            lifecycleScope.doDelayed(800L){
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
