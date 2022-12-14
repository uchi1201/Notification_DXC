package com.android.example.notification.ui.budget

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.notification.data.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class BudgetViewModel : ViewModel() {

    val budgetData = MutableLiveData<MutableList<BudgetData>>()
    val pieData = MutableLiveData<PieData>()

    fun getListData(month: String){
        var budgetDataBean =  getBudgetBean(month)
        for (i in budgetDataBean.indices){
            budgetData.value = budgetDataBean[i].data
        }
    }

    private fun getBudgetBean(month: String): MutableList<BudgetUsedBean> {
        return getBudgetData(month)
    }

    private fun getBudgetData(month: String):MutableList<BudgetUsedBean> {
        var results = mutableListOf<BudgetUsedBean>()
        var dataBean = BudgetUsedBean(data =  mutableListOf<BudgetData>())
        var budgetData = mutableListOf<BudgetData>()
        if (month=="1") {
            val value1 = BudgetData(category = "服飾費", budget = "3000", percentage = "30.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "2800", percentage = "28.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "100", percentage = "1.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "3800", percentage = "39.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else if(month=="2")
        {
            val value1 = BudgetData(category = "服飾費", budget = "1000", percentage = "10.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "3800", percentage = "30.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "1000", percentage = "10.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "2800", percentage = "19.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else if(month=="3")
        {
            val value1 = BudgetData(category = "服飾費", budget = "1000", percentage = "10.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "1800", percentage = "18.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "3100", percentage = "31.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "3800", percentage = "39.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else if(month=="4")
        {
            val value1 = BudgetData(category = "服飾費", budget = "35000", percentage = "50.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "1800", percentage = "18.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "2100", percentage = "21.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "3000", percentage = "30.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else if(month=="5")
        {
            val value1 = BudgetData(category = "服飾費", budget = "5200", percentage = "53.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "3000", percentage = "30.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "2100", percentage = "11.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "4800", percentage = "46.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else if(month=="6")
        {
            val value1 = BudgetData(category = "服飾費", budget = "1000", percentage = "10.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "5800", percentage = "58.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "100", percentage = "1.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "3800", percentage = "39.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        else{
            val value1 = BudgetData(category = "服飾費", budget = "1000", percentage = "10.9%")
            budgetData.add(value1)
            val value2 = BudgetData(category = "食費", budget = "5800", percentage = "58.9%")
            budgetData.add(value2)
            val value3 = BudgetData(category = "交際", budget = "100", percentage = "1.0%")
            budgetData.add(value3)
            val value4 = BudgetData(category = "定期支払い", budget = "3800", percentage = "39.2%")
            budgetData.add(value4)
            dataBean = BudgetUsedBean(data = budgetData)
            results.add(dataBean)
        }
        return results
    }

    fun getPieData(month:String){
        pieData.value = createPieData(month)
    }
    //仮データ
    private fun getPieDataList(month:String):MutableList<PieDataValueBean>{
        val results = mutableListOf<PieDataValueBean>()
        if(month == "1"){
            val data  = PieGraphData ("服飾費",3800f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",1000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",3000f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",4200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "2"){
            val data  = PieGraphData ("服飾費",1000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",3000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",1800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",5200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "3"){
            val data  = PieGraphData ("服飾費",3000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",2000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",2800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",5200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "4"){
            val data  = PieGraphData ("服飾費",4000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",1200f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",5800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",3200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "5"){
            val data  = PieGraphData ("服飾費",1000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",2000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",3000f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",3200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "6"){
            val data  = PieGraphData ("服飾費",2000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",1000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",3800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",2200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "7"){
            val data  = PieGraphData ("服飾費",3000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",2000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",1800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",2200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "8"){
            val data  = PieGraphData ("服飾費",4000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",2000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",100f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",4000f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "9"){
            val data  = PieGraphData ("服飾費",3000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",1000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",8800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",9200f)
            results.add( PieDataValueBean(data3))
        }
        else if(month == "10"){
            val data  = PieGraphData ("服飾費",1000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",4000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",1800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",2200f)
            results.add( PieDataValueBean(data3))
        }
        else{
            val data  = PieGraphData ("服飾費",5000f)
            results.add( PieDataValueBean(data))
            val data1  = PieGraphData ("食費",3000f)
            results.add( PieDataValueBean(data1))
            val data2  = PieGraphData ("交際",1800f)
            results.add( PieDataValueBean(data2))
            val data3  = PieGraphData ("定期支払い",5200f)
            results.add( PieDataValueBean(data3))
        }

        return results
    }
    private fun createPieData(month:String): PieData {
        //表示用サンプルデータの作成
        val pieDataList = getPieDataList(month)
        //①Entryにデータ格納
        var entryList = mutableListOf<PieEntry>()
        for(i in pieDataList.indices){
            entryList.add(
                PieEntry(pieDataList[i].data.budget, pieDataList[i].data.name)
            )
        }

        //②PieDataSetにデータ格納
        val pieDataSet = PieDataSet(entryList, "")
        //③DataSetのフォーマット指定
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        //④PieDataにPieDataSet格納して返却
        return PieData(pieDataSet)
    }
}
