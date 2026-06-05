package org.jonathansteele.coincanvas.data.model

import androidx.compose.runtime.Stable

@Stable
data class FavoritesSet(
    val ids: Set<String>
) {
    fun contains(id: String) = ids.contains(id)
    val count: Int get() = ids.size
}