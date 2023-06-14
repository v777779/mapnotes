package ru.vpcb.map.notes.model

import android.os.Bundle
import ru.vpcb.map.notes.utils.NAVIGATE_BUNDLE_TYPE

data class NavArgs(
    val type: TypeFragment = TypeFragment.ADD,
)


enum class TypeFragment {
    ADD, EDIT;

    companion object {
        val map = values().associateBy { it.name }
    }

}

fun NavArgs.toBundle() = Bundle().apply {
        putString(NAVIGATE_BUNDLE_TYPE, type.name)
    }

fun TypeFragment.toBundle() = NavArgs(this).toBundle()

fun Bundle?.toNavArgs(): NavArgs {
    val type = TypeFragment.map[this?.getString(NAVIGATE_BUNDLE_TYPE)] ?: TypeFragment.ADD

    return NavArgs(type)
}