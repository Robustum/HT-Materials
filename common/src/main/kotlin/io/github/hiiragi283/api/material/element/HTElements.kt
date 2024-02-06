package io.github.hiiragi283.api.material.element

import io.github.hiiragi283.api.material.ColorConvertible
import io.github.hiiragi283.api.util.HTColor

object HTElements {
    //    Elements    //

    @JvmField
    val H = HTElement(HTColor.BLUE, "H", 1.0)

    @JvmField
    val He = HTElement(HTColor.YELLOW, "He", 4.0)

    @JvmField
    val Li = HTElement(HTColor.GRAY, "Li", 6.9)

    @JvmField
    val Be = HTElement(HTColor.DARK_GREEN, "Be", 9.0)

    @JvmField
    val C = HTElement(
        ColorConvertible.average(HTColor.BLACK, HTColor.DARK_GRAY),
        "C",
        12.0,
    )

    @JvmField
    val N = HTElement(HTColor.AQUA, "N", 14.0)

    @JvmField
    val O = HTElement(HTColor.WHITE, "O", 16.0)

    @JvmField
    val F = HTElement(HTColor.GREEN, "F", 19.0)

    @JvmField
    val Na = HTElement(
        ColorConvertible.average(HTColor.DARK_BLUE to 1, HTColor.BLUE to 4),
        "Na",
        23.0,
    )

    @JvmField
    val Mg = HTElement(HTColor.GRAY, "Mg", 24.0)

    @JvmField
    val Al = HTElement(
        ColorConvertible.average(HTColor.BLUE to 1, HTColor.WHITE to 5),
        "Al",
        27.0,
    )

    @JvmField
    val Si = HTElement(
        ColorConvertible.average(HTColor.BLACK to 2, HTColor.GRAY to 1, HTColor.BLUE to 1),
        "Si",
        28.1,
    )

    @JvmField
    val P = HTElement(HTColor.YELLOW, "P", 31.0)

    @JvmField
    val S = HTElement(
        ColorConvertible.average(HTColor.GOLD, HTColor.YELLOW),
        "S",
        32.1,
    )

    @JvmField
    val Cl = HTElement(HTColor.YELLOW, "Cl", 35.5)

    @JvmField
    val K = HTElement(
        ColorConvertible.average(HTColor.DARK_BLUE to 2, HTColor.BLUE to 3),
        "K",
        39.1,
    )

    @JvmField
    val Ca = HTElement(HTColor.GRAY, "Ca", 40.1)

    @JvmField
    val Ti = HTElement(
        ColorConvertible.average(HTColor.GOLD to 1, HTColor.WHITE to 2),
        "Ti",
        47.9,
    )

    @JvmField
    val Cr = HTElement(HTColor.GREEN, "Cr", 52.0)

    @JvmField
    val Mn = HTElement(HTColor.GRAY, "Mn", 54.9)

    @JvmField
    val Fe = HTElement(HTColor.WHITE, "Fe", 55.8)

    @JvmField
    val Co = HTElement(HTColor.BLUE, "Co", 58.9)

    @JvmField
    val Ni = HTElement(
        ColorConvertible.average(HTColor.GOLD to 2, HTColor.GREEN to 1, HTColor.WHITE to 1),
        "Ni",
        58.7,
    )

    @JvmField
    val Cu = HTElement(
        ColorConvertible.average(HTColor.GOLD, HTColor.RED),
        "Cu",
        63.5,
    )

    @JvmField
    val Zn = HTElement(
        ColorConvertible.average(HTColor.GREEN to 1, HTColor.WHITE to 2),
        "Zn",
        65.4,
    )

    //    Groups    //

    @JvmField
    val CO3 = HTElementGroup(C to 1, O to 3)

    @JvmField
    val Al2O3 = HTElementGroup(Al to 2, O to 3)

    @JvmField
    val SiO2 = HTElementGroup(Si to 1, O to 1)
}
