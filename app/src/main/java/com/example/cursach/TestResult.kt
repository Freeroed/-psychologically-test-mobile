package com.example.cursach

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cursach.rest.response.ResultTestDto
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_test_result.*


class TestResult : AppCompatActivity() {


    var previousPage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)
        val resultTest : ResultTestDto = intent.getSerializableExtra("result") as ResultTestDto
        previousPage = intent.getStringExtra("previousPage")!!

        goBack.setOnClickListener {
            onBackPressed()
        }
        setBarChart(resultTest)
    }

    private fun setBarChart(result: ResultTestDto) {

        var xAxis = testResultChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = PsychotypeFormatter()

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, result.demonstrativeType.toFloat()))
        entries.add(BarEntry(2f, result.stuckType.toFloat()))
        entries.add(BarEntry(3f, result.pedanticType.toFloat()))
        entries.add(BarEntry(4f, result.excitableType.toFloat()))
        entries.add(BarEntry(5f, result.hyperthymicType.toFloat()))
        entries.add(BarEntry(6f, result.dysthymicType.toFloat()))
        entries.add(BarEntry(7f, result.anxiouslyFearfulType.toFloat()))
        entries.add(BarEntry(8f, result.emotionallyExaltedType.toFloat()))
        entries.add(BarEntry(9f, result.emotiveType.toFloat()))
        entries.add(BarEntry(10f, result.cyclothymicType.toFloat()))

        val dataSet = BarDataSet(entries, "Результат")

        val data = BarData(dataSet)
        testResultChart.data = data
        testResultChart.setFitBars(false)
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 10
        testResultChart.invalidate()
        testResultChart.animate()
    }
    class PsychotypeFormatter : ValueFormatter() {

        final val xLabel  = listOf("Демонстративный тип",
            "Застенчивый тип",
            "Педантичный тип",
            "Возбудимый тип",
            "Гипертимный тип",
            "Дистимический тип",
            "Тревожно-боязливый тип",
            "Аффективно-экзальтированный тип",
            "Эмотивный тип",
            "Циклотимный тип")

        override fun getFormattedValue(value: Float): String {
            return xLabel[value.toInt()-1]
        }


    }

    override fun onBackPressed() {
        if (previousPage == "test_desc") {
            val intent = Intent(this, TestDescriptionActivity::class.java)
            startActivity(intent)
        }
        super.onBackPressed()
    }
}