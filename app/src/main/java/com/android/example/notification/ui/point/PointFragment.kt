package com.android.example.notification.ui.point

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentPointBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class PointFragment : Fragment() {
    private var _binding: FragmentPointBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pointViewModel: PointViewModel by lazy {
            ViewModelProvider(this)[PointViewModel::class.java]
        }

        _binding = FragmentPointBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val title = binding.titlePoint
        title.title.text = "ポイント"
        val textView: TextView = binding.textPoint
        pointViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val barChart: BarChart = binding.barChart
        pointViewModel.barData.observe(viewLifecycleOwner) {
            //⑤BarChartにBarData格納
            barChart.data = it

            //⑥Chartのフォーマット指定
            //X軸の設定
            val months = listOf<String>("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月")
            barChart.xAxis.apply {
                textColor = Color.BLACK
                valueFormatter = IndexAxisValueFormatter(months)
                position = XAxis.XAxisPosition.BOTTOM_INSIDE
                setDrawGridLines(false)
            }

            //Y軸右側の設定
            //ラベル非表示
            barChart.axisRight.isEnabled = false

            //チャート内の数字を非表示
            barChart.barData.setDrawValues(false)

            //descriptionラベルを非表示
            barChart.description.isEnabled = false

            //legend設定
            val legendEntry1 = LegendEntry()
            legendEntry1.label = "限定ポイント"
            legendEntry1.formColor = barChart.barData.colors[0]
            val legendEntry2 = LegendEntry()
            legendEntry2.label = "通常ポイント"
            legendEntry2.formColor = barChart.barData.colors[1]
            barChart.legend.setCustom(listOf<LegendEntry>(legendEntry1, legendEntry2))

            //拡大縮小ができないようにする
            barChart.setScaleEnabled(false)

            //表示の幅を決定する、3ヶ月表示、横スクロール可
            barChart.setVisibleXRangeMaximum(3f);

            //最新月まで表示を移動させる
            barChart.moveViewToX(barChart.barData.xMax);

            //⑦barchart更新
            barChart.invalidate()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
