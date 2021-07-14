package dev.isxander.evergreenhud.utils

import java.math.BigDecimal
import java.util.*
import kotlin.math.ceil
import kotlin.math.roundToInt

object MathUtils {

    /**
     * Clamps value between min & max
     *
     * @param _num value to clamp
     * @param min min value
     * @param max max value
     * @return clamped value
     * @author isXander
     */
    fun clamp(_num: Float, min: Float = 0f, max: Float = 1f): Float {
        var num = _num
        if (num > max) num = max else if (num < min) num = min
        return num
    }

    /**
     * Linearly interpolates between a and b by t.
     *
     * @param start Start value
     * @param end End value
     * @param interpolation Interpolation between two floats
     * @return interpolated value between a - b
     * @author isXander
     */
    fun lerp(start: Float, end: Float, interpolation: Float): Float {
        return start + (end - start) * clamp(interpolation)
    }

    /**
     * Returns number between 0 - 1 depending on the range and value given
     *
     * @param num the value
     * @param min minimum of what the value can be
     * @param max maximum of what the value can be
     * @return converted percentage
     * @author isXander
     */
    fun getPercent(num: Float, min: Float = 0f, max: Float = 100f): Float {
        return (num - min) / (max - min)
    }

    /**
     * Returns the percentile of list of longs
     *
     * @param nums the list on which to calculate the percentile
     * @param percentile what percentile the calculation will output
     * @return the percentile of the nums
     * @author isXander
     */
    fun percentile(nums: MutableList<Long>, percentile: Double): Long {
        nums.sort()
        val index = ceil(percentile / 100.0 * nums.size).toInt()
        return nums[index - 1]
    }

    /**
     * @param num value to change
     * @param places how many decimal places the number should have
     * @return x amount of places of precision of a value
     */
    fun precision(num: Float, places: Int): Float {
        val mod = places.coerceAtLeast(0)
        if (places(num) <= mod) return num
        return if (mod == 0) num.roundToInt().toFloat() else (num * mod).roundToInt().toFloat() / mod
    }

    /**
     * @param num value to check
     * @return the scale of a number
     */
    fun places(num: Float): Int {
        return BigDecimal.valueOf(num.toDouble()).scale()
    }

    /**
     * @param num value to round
     * @param places how many decimal places number should have
     * @return a value with a certain amount of decimal places
     */
    fun round(num: Float, places: Int): Float {
        var bd = BigDecimal(num.toString())
        bd = bd.setScale(places, BigDecimal.ROUND_DOWN)
        return bd.toFloat()
    }
    
}