package com.current

object ExternalDebitCard {
    fun validateCardNumber(cleanedCardNumber: String): Boolean {
        var sum = 0
        var digit: Int
        var addend: Int
        var doubled = false
        for (i in cleanedCardNumber.length - 1 downTo 0) {
            digit = cleanedCardNumber.substring(i, i + 1)
                .toInt()
            if (doubled) {
                addend = digit * 2
                if (addend > 9) {
                    addend -= 9
                }
            } else {
                addend = digit
            }
            sum += addend
            doubled = !doubled
        }
        return sum % 10 == 0
    }
}
