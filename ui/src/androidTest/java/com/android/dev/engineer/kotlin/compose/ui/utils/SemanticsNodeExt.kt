package com.android.dev.engineer.kotlin.compose.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsNode
import junit.framework.TestCase.assertEquals

fun SemanticsNode.assertHasModifier(modifier: Modifier) {
    val isModifierFound = layoutInfo.getModifierInfo().any { modifierInfo ->
        modifierInfo.modifier == modifier
    }
    assertEquals("$modifier not found", true, isModifierFound)
}