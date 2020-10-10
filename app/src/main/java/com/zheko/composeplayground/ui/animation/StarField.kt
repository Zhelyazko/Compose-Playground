package com.zheko.composeplayground.ui.animation

import android.util.Log
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.core.math.MathUtils.clamp
import androidx.ui.tooling.preview.Preview
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

private const val NUM_STARS = 1000
private const val SPEED = 4.1f

@Composable
fun StarField(modifier: Modifier = Modifier) {

    val animatedProgress = animatedFloat(0f)
    onActive {
        animatedProgress.animateTo(
            targetValue = 1f,
            anim = repeatable(
                repeatMode = RepeatMode.Reverse,
                iterations = AnimationConstants.Infinite,
                animation = tween(durationMillis = 20_000),
            )
        )
    }

    val starColor = if (!isSystemInDarkTheme()) Color.White else Color.Black
    val stars = remember {
        (0 until NUM_STARS)
            .map {
                Star(
                    x = Math.random().toFloat() * 1000,
                    y = Math.random().toFloat() * 1000,
                    radius = Math.random().toFloat() * 10
                )
            }
            .toTypedArray()
    }

    var previousAnimationValue = animatedProgress.value

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val animatedFraction = 1 + abs(animatedProgress.value - previousAnimationValue)
        previousAnimationValue = animatedProgress.value

        val halfDiagonal = calculateDistance(0f, 0f, size.width, size.height) / 2f

        stars.forEachIndexed { index, star ->
            val vector = calculateVector(star.x, star.y, centerX, centerY)
            val newX = star.x + SPEED * vector.x * animatedFraction
            val newY = star.y + SPEED * vector.y * animatedFraction
            val distanceFromCenter = calculateDistance(newX, newY, centerX, centerY)
            val interpolatedRadius = clamp(
                lerp(star.radius, star.radius * 5, distanceFromCenter / halfDiagonal), 1f, 50f
            )

            val angle = angle(Vector(1f, 0f), vector)
            withTransform({
                translate(newX, newY)
                rotate(angle, Offset(0f, 0f))
            }
            ) {
                drawOval(
                    brush = RadialGradient(
                        listOf(Color.White, Color.Blue),
                        300f, interpolatedRadius/2, 600f
                    ),
                    topLeft = Offset(0f - interpolatedRadius, 0f - interpolatedRadius),
                    size = Size(600f, interpolatedRadius * 2)
                )
            }

//            drawCircle(
//                center = Offset(newX, newY),
//                radius = interpolatedRadius,
//                color = Color.Green
//            )

            if (isOutsideStarField(star, size.width, size.height)) {
                stars[index] = respawn(star, size.width, size.height)
            } else {
                stars[index] = star.copy(x = newX, y = newY)
            }
        }
    }
}

private fun isOutsideStarField(star: Star, w: Float, h: Float): Boolean {
    return star.x < 0 || star.x > w || star.y < 0 || star.y > h
}

private fun respawn(star: Star, w: Float, h: Float): Star {
    val minX = w * 0.4f
    val minY = h * 0.4f
    return star.copy(
        x = minX + Math.random().toFloat() * (w / 5),
        y = minY + Math.random().toFloat() * (h / 5),
        radius = Math.random().toFloat() * 5
    )
}

private fun calculateVector(starX: Float, starY: Float, centerX: Float, centerY: Float): Vector {
    val vectorX = starX - centerX
    val vectorY = starY - centerY
    val magnitude = sqrt(vectorX * vectorX + vectorY * vectorY)
    if (magnitude > 0) {
        return Vector(vectorX / magnitude, vectorY / magnitude)
    } else {
        return Vector(vectorX, vectorY)
    }
}

private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float =
    sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))

/**
 * Calculates a number between two numbers at a specific increment.
 */
fun lerp(a: Float, b: Float, t: Float): Float {
    return a + (b - a) * t
}

fun dotProduct(v1: Vector, v2: Vector) = v1.x * v2.x + v1.y * v2.y

fun determinant(v1: Vector, v2: Vector) = v1.x * v2.y - v1.y * v2.x

fun angle(v1: Vector, v2: Vector): Float {
    val angle = Math.toDegrees(atan2(determinant(v1, v2), dotProduct(v1, v2)).toDouble()).toFloat()
    if (angle >= 0) {
        return angle
    } else {
        return 360 + angle
    }
}

data class Star(
    val x: Float,
    val y: Float,
    val radius: Float
)

data class Vector(
    val x: Float,
    val y: Float
)
