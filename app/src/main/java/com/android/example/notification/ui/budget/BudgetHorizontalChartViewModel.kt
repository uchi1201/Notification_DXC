package com.android.example.notification.ui.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.notification.data.BudgetGraphData
import com.android.example.notification.data.BudgetValueBean
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class BudgetHorizontalChartViewModel : ViewModel() {

    //予算総額設定
    private val _totalBarData= MutableLiveData<BarData>().apply {
        value = createTotalBarData()
    }
    val totalBarData: LiveData<BarData> = _totalBarData
    //予算設定
    val barData= MutableLiveData<BarData>()
    //XのLabel
    private val _xLabel = MutableLiveData<MutableList<String>>().apply {
        value = barChartXLabel()
    }
    val xLabel:LiveData<MutableList<String>> = _xLabel

   //仮データ
    fun createBudgetDataList(month:String):MutableList<BudgetValueBean>{
        val results = mutableListOf<BudgetValueBean>()
        if(month == "1"){
           val data  =BudgetGraphData (4000f,6000f)
           results.add( BudgetValueBean(data))
           val data1  =BudgetGraphData (10000f,10000f)
           results.add( BudgetValueBean(data1))
           val data2  =BudgetGraphData (1000f,8800f)
           results.add( BudgetValueBean(data2))
           val data3  =BudgetGraphData (2800f,9200f)
           results.add( BudgetValueBean(data3))
       }
        else if(month == "2"){
            val data  =BudgetGraphData (3000f,6000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (2800f,6000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5000f,8000f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9000f)
            results.add( BudgetValueBean(data3))
        }
       else if(month == "3"){
            val data  =BudgetGraphData (1000f,7000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (1800f,2000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5600f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9200f)
            results.add( BudgetValueBean(data3))
        }
        else if(month == "4"){
            val data  =BudgetGraphData (4000f,6000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (7800f,10000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (1000f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (2800f,9200f)
            results.add( BudgetValueBean(data3))
        }
        else if(month == "5"){
            val data  =BudgetGraphData (1000f,7000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (1800f,2000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5600f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9200f)
            results.add( BudgetValueBean(data3))
        }
      else if(month == "6"){
           val data  =BudgetGraphData (3000f,6000f)
           results.add( BudgetValueBean(data))
           val data1  =BudgetGraphData (2800f,6000f)
           results.add( BudgetValueBean(data1))
           val data2  =BudgetGraphData (5000f,8000f)
           results.add( BudgetValueBean(data2))
           val data3  =BudgetGraphData (3800f,9000f)
           results.add( BudgetValueBean(data3))
       }
       else if(month == "7"){
           val data  =BudgetGraphData (1000f,7000f)
           results.add( BudgetValueBean(data))
           val data1  =BudgetGraphData (1800f,2000f)
           results.add( BudgetValueBean(data1))
           val data2  =BudgetGraphData (5600f,8800f)
           results.add( BudgetValueBean(data2))
           val data3  =BudgetGraphData (3800f,9200f)
           results.add( BudgetValueBean(data3))
       }
       else if(month == "8"){
           val data  =BudgetGraphData (4000f,6000f)
           results.add( BudgetValueBean(data))
           val data1  =BudgetGraphData (7800f,10000f)
           results.add( BudgetValueBean(data1))
           val data2  =BudgetGraphData (1000f,8800f)
           results.add( BudgetValueBean(data2))
           val data3  =BudgetGraphData (2800f,9200f)
           results.add( BudgetValueBean(data3))
       }
        else if(month == "9"){
            val data  =BudgetGraphData (3000f,6000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (2800f,6000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5000f,8000f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9000f)
            results.add( BudgetValueBean(data3))
        }
        else if(month == "10"){
            val data  =BudgetGraphData (1000f,7000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (1800f,2000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5600f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9200f)
            results.add( BudgetValueBean(data3))
        }
        else if(month == "11"){
            val data  =BudgetGraphData (4000f,6000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (7800f,10000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (1000f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (2800f,9200f)
            results.add( BudgetValueBean(data3))
        }
        else if(month == "12"){
            val data  =BudgetGraphData (1000f,7000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (1800f,2000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (5600f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (3800f,9200f)
            results.add( BudgetValueBean(data3))
        }
       else{
            val data  =BudgetGraphData (4000f,6000f)
            results.add( BudgetValueBean(data))
            val data1  =BudgetGraphData (7800f,10000f)
            results.add( BudgetValueBean(data1))
            val data2  =BudgetGraphData (1000f,8800f)
            results.add( BudgetValueBean(data2))
            val data3  =BudgetGraphData (2800f,9200f)
            results.add( BudgetValueBean(data3))
       }

        return results
    }

    private fun insertData(month: String) {

    }

    private fun barChartXLabel(): MutableList<String> {
        return mutableListOf("服飾費", "食費", "交際", "定期支払い")
    }
    private fun createTotalBarData(): BarData {
        //表示用サンプルデータの予算総額作成
        val total = 20000f
        val actual = 9700f

        //①Entryにデータ格納
        var entryList = mutableListOf<BarEntry>()
        entryList.add(
            BarEntry(1f, actual)
        )
        //BarDataSetにデータ格納
        val barDataSet = BarDataSet(entryList, "totalBudget")
        barDataSet.setDrawIcons(false)
        //BarDataSetのカラー設定
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.valueFormatter =object : ValueFormatter() {
            override fun getFormattedValue(v: Float): String? {
                return "${v.toInt()}/${total.toInt()}"
            }
        }
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)
        //BarDataにBarDataSet格納して返却
        return BarData(dataSets)
    }
    fun getBarData(month:String){
        barData.value = createBarData(month)
    }
   private  fun createBarData(month:String): BarData {
         val  budgetList = createBudgetDataList(month)

        var entryList = mutableListOf<BarEntry>()
        for(i in budgetList.indices){
            entryList.add(
                BarEntry(i.toFloat(), budgetList[i].data.budget)
            )
        }
        val barDataSet = BarDataSet(entryList, "budget")
        barDataSet.setDrawIcons(false)
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.valueFormatter =object : ValueFormatter() {
            var index =0
            override fun getFormattedValue(v: Float): String? {
                if(index==budgetList.count()){
                    index=0
                }
                val budget = budgetList[index].data.budget
                val budgetTotal = budgetList[index].data.budgetTotal
                index++
                return "${budget.toInt()}/${budgetTotal.toInt()}"
            }
        }
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)
        //BarDataにBarDataSet格納して返却
        return BarData(dataSets)
    }

}
