package com.yangyongwen.chapter12

import android.opengl.Matrix
import com.yangyongwen.common.utils.Point
import com.yangyongwen.common.utils.Vector
import kotlin.random.Random

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/28
 */

class ParticleShooter(
    private val position: Point,
    private val direction: Vector,
    private val color: Int,
    private val angleVarianceInDegrees: Float,
    private val speedVariance: Float
) {

    private val random = Random.Default

    private val rotationMatrix = FloatArray(16)
    private val directionVector = FloatArray(4).apply {
        this[0] = direction.x
        this[1] = direction.y
        this[2] = direction.z
    }
    private val resultVector = FloatArray(4)

    fun addParticles(particleSystem: ParticleSystem, currentTime: Float, count: Int) {
        repeat(count) {
            Matrix.setRotateEulerM(
                rotationMatrix, 0,
                (random.nextFloat() - 0.5f) * angleVarianceInDegrees,
                (random.nextFloat() - 0.5f) * angleVarianceInDegrees,
                (random.nextFloat() - 0.5f) * angleVarianceInDegrees
            )
            Matrix.multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0)
            val speedAdjustment = 1f + random.nextFloat() * speedVariance
            val thisDirection = Vector(
                resultVector[0] * speedAdjustment,
                resultVector[1] * speedAdjustment,
                resultVector[2] * speedAdjustment
            )
            particleSystem.addParticle(position, color, thisDirection, currentTime)
        }
    }

}