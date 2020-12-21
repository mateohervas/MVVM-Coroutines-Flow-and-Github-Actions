package com.shadows.bitsodream.ui.booksdetail.charts

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MyLineChart( private val myLineChart: LineChart,private val lineEntries: ArrayList<Entry>, private val label:String ) {

    fun setupChart() {
        val data = generateLineData()
        // no description text
        myLineChart.description.isEnabled = true

        myLineChart.setDrawGridBackground(false)

        // enable touch gestures
        myLineChart.setTouchEnabled(true)

        // enable scaling and dragging
        myLineChart.isDragEnabled = true
        myLineChart.setScaleEnabled(false)
        myLineChart.setPinchZoom(true)

        // add data
        myLineChart.data = data

        val legend = myLineChart.legend
        legend.isEnabled = true
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.yOffset = 5f
        val xAxis = myLineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.granularity = 1f
        xAxis.spaceMin =1f
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = myLineChart.xChartMax +1f


        val yAxis = myLineChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.setDrawGridLines(true)
        yAxis.textColor = Color.BLACK
        yAxis.axisMaximum = myLineChart.yMax +(myLineChart.yMax*0.10f)
        myLineChart.axisRight.isEnabled = false

        myLineChart.setExtraOffsets(12f,15f,12f,12f)
        myLineChart.description.isEnabled = false
        myLineChart.fitScreen()
        myLineChart.animateX(400)
    }

    private fun generateLineData(): LineData {
        val lineData = LineData()

        val dataSet = LineDataSet(lineEntries, label)
        dataSet.isHighlightEnabled = false
        dataSet.setDrawCircles(false)
        dataSet.fillColor = Color.rgb(238, 130, 57)
        dataSet.setDrawValues(false)
        dataSet.color = Color.rgb(238, 130, 57)
        dataSet.lineWidth= 2.0f
        dataSet.mode = LineDataSet.Mode.LINEAR
        dataSet.form = Legend.LegendForm.EMPTY
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineData.addDataSet(dataSet)

        return lineData
    }
}