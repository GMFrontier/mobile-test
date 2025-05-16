package com.frommetoyou.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun QRScannerGuideWithPath(
    modifier: Modifier = Modifier,
    cornerColor: Color = Color(0xFF00BFFF),
    cornerLength: Dp = 40.dp,
    cornerRadius: Dp = 10.dp,
    strokeWidth: Dp = 6.dp
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val cornerPx = cornerLength.toPx()
        val radiusPx = cornerRadius.toPx()
        val strokePx = strokeWidth.toPx()

        val left = size.width / 2 - size.minDimension / 3
        val top = size.height / 2 - size.minDimension / 3
        val right = size.width / 2 + size.minDimension / 3
        val bottom = size.height / 2 + size.minDimension / 3

        val path = Path().apply {
            moveTo(left, top + cornerPx)
            lineTo(left, top + radiusPx)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left,
                    top,
                    left + radiusPx * 2,
                    top + radiusPx * 2
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(left + cornerPx, top)

            moveTo(right - cornerPx, top)
            lineTo(right - radiusPx, top)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    right - radiusPx * 2,
                    top,
                    right,
                    top + radiusPx * 2
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(right, top + cornerPx)

            moveTo(right, bottom - cornerPx)
            lineTo(right, bottom - radiusPx)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    right - radiusPx * 2,
                    bottom - radiusPx * 2,
                    right,
                    bottom
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(right - cornerPx, bottom)

            moveTo(left + cornerPx, bottom)
            lineTo(left + radiusPx, bottom)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left,
                    bottom - radiusPx * 2,
                    left + radiusPx * 2,
                    bottom
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(left, bottom - cornerPx)
        }

        drawPath(
            path = path,
            color = cornerColor,
            style = Stroke(width = strokePx)
        )
    }
}
