package com.b21dccn216.smarthome.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

data class ChartData(
    val x: String,
    val y: Int
)


@Composable
fun LineChart(
    data: List<ChartData>,
    height: Dp = 340.dp,
    step: Int = 100,
    colorChart: Color = Color.Blue
){
    val xAxisPadding = 16.dp
    val yAxisPadding = 16.dp

    val xAxisData = data.map{it.x}
    val yAxisData = data.map{it.y}
    
    val offsetList = remember { mutableListOf<Offset>() }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .padding(16.dp)
    ){
        val gridHeight = height.toPx() - yAxisPadding.toPx()*3
        val gridWidth = size.width

        var absMaxYPoint = yAxisData.maxByOrNull { it.toFloat().roundToInt() } ?:0
        val yAxisLabelList = mutableListOf<String>()

        var tmp = 0
        while(tmp <= absMaxYPoint){
            yAxisLabelList.add(tmp.toString())
            tmp += step
        }
        yAxisLabelList.add(tmp.toString())

        val xAxisSpacing = (gridWidth - xAxisPadding.toPx()) / (data.size - 1)
        val yAxisSpacing = gridHeight / (yAxisLabelList.size - 1)

        offsetList.clear()
        for(i in 0 until data.size){
            val x = i * xAxisSpacing + xAxisPadding.toPx()
            val y = gridHeight - (yAxisSpacing * (yAxisData[i].toFloat() / step))
            offsetList.add(Offset(x,y))
        }
//        draw vertical grid
        for(i in 0 until data.size){
            val xOffset = (xAxisSpacing * i) + xAxisPadding.toPx()
            drawLine(
                color = if(i == 0 || i == data.size-1) Color.Black else Color.Gray.copy(alpha = 0.3f),
                start = Offset(xOffset, 0f),
                end = Offset(xOffset, gridHeight),
                strokeWidth = 2f
            )
        }

//        draw horizon grid
        for(i in 0 until yAxisLabelList.size){
            val yOffset = gridHeight - (yAxisSpacing*i)
            drawLine(
                color = if(i == 0 || i == yAxisLabelList.size-1) Color.Black else Color.Gray.copy(alpha = 0.3f),
                start = Offset(xAxisPadding.toPx(), yOffset),
                end = Offset(gridWidth , yOffset),
                strokeWidth = 2f
            )
        }

//        draw x label
        for(i in 0 until data.size){
            val xOffset = (xAxisSpacing * i) + xAxisPadding.toPx()
            drawContext.canvas.nativeCanvas.drawText(
                xAxisData[i],
                xOffset,
                size.height,
                Paint().apply {
                    color = Color.Gray.toArgb()
                    textAlign = Paint.Align.CENTER
                    textSize = 10.sp.toPx()
                }
            )
        }

//        draw y label
        for(i in 0 until yAxisLabelList.size){
            drawContext.canvas.nativeCanvas.drawText(
                yAxisLabelList[i],
                0f,
                gridHeight - yAxisSpacing * i,
                Paint().apply {
                    color = Color.Gray.toArgb()
                    textAlign = Paint.Align.CENTER
                    textSize = 10.sp.toPx()
                }
            )
        }

//        draw point for each offset
        offsetList.forEachIndexed{ index, offset ->
            drawCircle(
                color = colorChart,
                radius = 3.dp.toPx(),
                center = offset
            )
        }

//        draw line connect point
        drawPoints(
            points = offsetList,
            color = colorChart.copy(alpha = 0.8f),
            pointMode = PointMode.Polygon,
            strokeWidth = 2f
        )

//        gradient color
        val gradientPath = Path().apply {
            moveTo(xAxisPadding.toPx() , size.height)
            for(i in 0 until data.size){
                lineTo(offsetList[i].x, offsetList[i].y)
            }
            lineTo(gridWidth, gridHeight)
            close()
        }

        drawPath(
            path = gradientPath,
            brush = Brush.verticalGradient(
                colors = listOf( colorChart.copy(alpha = 0.7f), Color.Transparent)
            )
        )
    }
}

@Composable
fun LineChartComponent(
    name: String,
    chartData: List<ChartData>,
    colorChart: Color = Color(0xFFFF8343)
){
    Box(
        modifier = Modifier
//            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(15))
                .clip(RoundedCornerShape(15))
                .background(color = Color.White)
                .padding(top = 8.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name)
            LineChart(
                data = chartData,
                height = 190.dp,
                step = 10,
                colorChart = colorChart)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PrviewLineChart(){
    val chartData = listOf(
        ChartData("T", 30),
        ChartData("W", 25),
        ChartData("F", 30),
        ChartData("SA", 20),
        ChartData("SU", 22),
        ChartData("M", 13),
    )
    Box(modifier = Modifier.padding(20.dp)){
        LineChartComponent(name = "Temperature", chartData = chartData)
    }

}