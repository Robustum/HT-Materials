package io.github.hiiragi283.material.api.material

fun interface FormulaConvertible {

    fun asFormula(): String

    companion object {

        @JvmField
        val EMPTY = FormulaConvertible { "" }

        @JvmStatic
        fun of(vararg formulas: FormulaConvertible) = of(formulas.associateWith { 1 })

        @JvmStatic
        fun of(vararg pair: Pair<FormulaConvertible, Int>) = of(pair.toMap())

        @JvmStatic
        fun of(map: Map<FormulaConvertible, Int>) =
            FormulaConvertible { format(map.mapKeys { (formula: FormulaConvertible, _) -> formula.asFormula() }) }

        @JvmStatic
        fun ofString(vararg formulas: String) = ofString(formulas.associateWith { 1 })

        @JvmStatic
        fun ofString(vararg pairs: Pair<String, Int>) = ofString(pairs.toMap())

        @JvmStatic
        fun ofString(map: Map<String, Int>) = FormulaConvertible { format(map) }

        @JvmStatic
        fun format(formulas: Iterable<String>): String = format(formulas.associateWith { 1 })

        @JvmStatic
        fun format(map: Map<String, Int>): String {
            val builder = StringBuilder()
            for ((formula: String, weight: Int) in map) {
                builder.append(formula)
                //値が1の場合はパス
                if (weight == 1) continue
                //化学式の下付き数字の桁数調整
                val subscript1: Char = '\u2080' + (weight % 10)
                val subscript10: Char = '\u2080' + (weight / 10)
                //2桁目が0でない場合，下付き数字を2桁にする
                builder.append(StringBuilder().run {
                    if (subscript10 != '\u2080') this.append(subscript10)
                    this.append(subscript1)
                })
            }
            return builder.toString()
        }

    }

}