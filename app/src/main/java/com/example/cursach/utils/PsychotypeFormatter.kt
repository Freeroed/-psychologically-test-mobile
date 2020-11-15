package com.example.cursach.utils

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

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