package com.android.example.notification.ui.budget


import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentBudgetHorizontalChartBinding
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [BudgetHorizontalChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetHorizontalChartFragment :  Fragment()  {

    private var _binding: FragmentBudgetHorizontalChartBinding? = null

    private val binding get() = _binding!!

    private lateinit var totalCharView: HorizontalBarChart
    private lateinit var charView: HorizontalBarChart
    private lateinit var horizontalViewModel: BudgetHorizontalChartViewModel
    private lateinit var month: String
    private val dataBase =  MainApplication.instance().budgetDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetHorizontalChartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initData()
        initView()
        initSpinner()
        month = MainApplication.instance().spinnerMonth
        return root
    }
    override fun onResume() {
        super.onResume()
        if (month != MainApplication.instance().spinnerMonth || MainApplication.instance().isEditBudget){
            initData()
            initView()
            initSpinner()
            MainApplication.instance().isEditBudget = false
        }
    }


    private fun initData() {
        horizontalViewModel = dataBase?.let { BudgetHorizontalChartViewModel(it) }!!
        activity?.let { horizontalViewModel.getBarData(MainApplication.instance().spinnerMonth) }
    }

    private fun initView() {
        initTotalChartView()
        initChartView()
        setChartViewData()
        setTotalChartViewData()
        refreshData()
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
                dataBase?.budgetDao()?.deleteAll()
                MainApplication.instance().spinnerMonth = (pos+1).toString()
                horizontalViewModel.getBarData(MainApplication.instance().spinnerMonth)
                initTotalChartView()
                initChartView()
                setChartViewData()
                setTotalChartViewData()
                month = MainApplication.instance().spinnerMonth
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }
        }
        binding.editBtn.setOnClickListener {
            val bundle = bundleOf("month" to MainApplication.instance().spinnerMonth)
            findNavController().navigate(R.id.edit_action,bundle)
        }

    }

    private fun CoroutineScope.doDelayed(timeMillis: Long, block: suspend () -> Unit) = this.launch {
        delay(timeMillis)
        block.invoke()
    }

    private fun refreshData(){
        val swipeRefreshLayout: SwipeRefreshLayout = binding.refresh
        swipeRefreshLayout.setOnRefreshListener {
            horizontalViewModel.getBarData(MainApplication.instance().spinnerMonth)
            initTotalChartView()
            initChartView()
            setChartViewData()
            setTotalChartViewData()
            totalCharView.notifyDataSetChanged()
            charView.notifyDataSetChanged()
            lifecycleScope.doDelayed(800L){
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initTotalChartView() {
        totalCharView = binding.totalChar
        totalCharView.setDrawBarShadow(true)
        totalCharView.renderer = HorizontalBarChartCustomRenderer(totalCharView, totalCharView.animator, totalCharView.viewPortHandler)
        totalCharView.setTouchEnabled(false)
        totalCharView.isDragEnabled = false
        totalCharView.setScaleEnabled(false)
        totalCharView.isDoubleTapToZoomEnabled = false
        totalCharView.isHighlightPerDragEnabled = false
        totalCharView.description.isEnabled = false
        totalCharView.animateY(800)
        val xl: XAxis = totalCharView.xAxis
        xl.position = XAxisPosition.BOTTOM
        xl.setDrawAxisLine(false)
        xl.setDrawGridLines(false)
        xl.textSize = 15f
        xl.granularity = 1f
        xl.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(v: Float): String? {
                return if (v == 0f) {
                    "予算総額"
                } else "予算総額"

            }
        }
        val yl: YAxis = totalCharView.axisLeft
        yl.setDrawAxisLine(false)
        yl.setDrawGridLines(false)
        yl.granularity = 1f
        yl.axisMaximum = 20000f
        yl.axisMinimum = 0f
        yl.isEnabled = false

        val yr: YAxis = totalCharView.axisRight
        yr.setDrawAxisLine(false)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f
        yr.isEnabled = false

        val l: Legend = totalCharView.legend
        l.isEnabled = false
        totalCharView.invalidate()
    }
    private fun initChartView() {
        charView = binding.categoryChar
        charView.setDrawBarShadow(true)
        charView.renderer = HorizontalBarChartCustomRenderer(charView, charView.animator, charView.viewPortHandler)
        charView.setTouchEnabled(false)
        charView.isDragEnabled = true
        charView.setScaleEnabled(false)
        charView.isDoubleTapToZoomEnabled = false
        charView.isHighlightPerDragEnabled = false
        charView.description.isEnabled = false
        charView.animateY(800)
        charView.setPinchZoom(false)
        charView.setFitBars(true)


        val xl: XAxis = charView.xAxis
        xl.position = XAxisPosition.BOTTOM
        xl.setDrawAxisLine(false)
        xl.setDrawGridLines(false)
        xl.labelCount = horizontalViewModel.xLabelCategory.size
        xl.textSize = 15f
        xl.granularity = 1f
        //横棒のｘ表示
        xl.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(v: Float): String? {
                return try {
                    horizontalViewModel.xLabelCategory[horizontalViewModel.xLabelCategory.size-v.toInt()-1]
                }catch ( e:Exception) {
                    ""
                }
            }
        }

        val yl: YAxis = charView.axisLeft
        yl.setDrawAxisLine(false)
        yl.setDrawGridLines(false)
        yl.granularity = 10f
        yl.axisMaximum = 10000f
        yl.axisMinimum = 0f // this replaces setStartAtZero(true)
        yl.isEnabled = false

        val yr: YAxis = charView.axisRight
        yr.setDrawAxisLine(false)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f // this replaces setStartAtZero(true)
        yr.isEnabled = false

        val l: Legend = charView.legend
        l.isEnabled = false

        charView.invalidate()
    }
    private fun setChartViewData() {
        val barWidth =0.6f
        horizontalViewModel.barData.observe(viewLifecycleOwner) {
            it.setValueTextSize(15f)
            it.barWidth = barWidth
            charView.data = it
        }

    }
    private fun setTotalChartViewData() {
        horizontalViewModel.totalBarData.observe(viewLifecycleOwner){
            it.setValueTextSize(15f)
            totalCharView.data = it
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class HorizontalBarChartCustomRenderer(
        chart: BarDataProvider, animator: ChartAnimator, viewPortHandler: ViewPortHandler
    ) : HorizontalBarChartRenderer(chart, animator, viewPortHandler) {
        override fun drawValue(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
            mValuePaint.color = color
            // calculate the correct offset depending on the draw position of the value
            val valueTextWidth = Utils.calcTextWidth(mValuePaint, valueText).toFloat()
            val budget = valueText.substring(0,valueText.indexOf("/")).toFloat()
            val total = 10000f

            if((budget/total)>=0.25f){
                c.drawText(valueText,x-valueTextWidth-40f, y, mValuePaint)
            } else {
                c.drawText(valueText,350f, y, mValuePaint)
            }

        }
    }
}