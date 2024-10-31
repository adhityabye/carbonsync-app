package io.apaaja.carbonsync.utils.formatter

object IntegerNumberFormatter {
    fun condense(number: Int): String {
        return if (number < 1000) "$number"
        else String.format("%.2fk", number / 1000.0)
    }
}