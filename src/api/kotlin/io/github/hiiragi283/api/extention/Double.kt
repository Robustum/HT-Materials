package io.github.hiiragi283.api.extention

fun calculateMolar(molars: Iterable<Double>) = calculateMolar(molars.associateWith { 1 })

fun calculateMolar(map: Map<Double, Int>): Double = map.map { (molar: Double, weight: Int) -> molar * weight }.sum()
