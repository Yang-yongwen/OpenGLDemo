package com.yangyongwen.common.utils

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/23
 */
data class Point(val x: Float, val y: Float, val z: Float) {

    fun translateY(distance: Float): Point {
        return Point(x, y + distance, z)
    }

}

data class Circle(val center: Point, val radius: Float) {

    fun scale(scale: Float): Circle {
        return Circle(center, scale * radius)
    }

}

data class Cylinder(val center: Point, val radius: Float, val height: Float)

data class Vector(val x: Float, val y: Float, val z: Float) {

    companion object {
        fun between(from: Point, to: Point): Vector {
            return Vector(to.x - from.x, to.y - from.y, to.z - from.z)
        }
    }

}

data class Ray(val point: Point, val vector: Vector)