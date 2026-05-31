package org.jonathansteele.coincanvas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.pretty

@Composable
fun CoinStatsSection(coin: Coin) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatRowExpressive("Market Cap", coin.marketCap?.pretty() ?: "—")
        StatRowExpressive("24h Volume", coin.totalVolume?.pretty() ?: "—")
        StatRowExpressive("Circulating Supply", coin.circulatingSupply?.pretty() ?: "—")
        StatRowExpressive("Total Supply", coin.totalSupply?.pretty() ?: "—")
    }
}


@Composable
fun StatRowExpressive(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
