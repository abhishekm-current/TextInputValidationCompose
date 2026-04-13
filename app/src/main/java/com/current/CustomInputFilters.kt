package com.current

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.then
import androidx.core.text.isDigitsOnly

fun InputTransformation.digitsOnly(): InputTransformation =
    this.then(DigitsOnlyFilter)

internal object DigitsOnlyFilter : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly()) {
            revertAllChanges()
        }
    }
}