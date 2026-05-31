package org.jonathansteele.coincanvas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jonathansteele.coincanvas.data.model.Coin
import org.jonathansteele.coincanvas.toFixed

@Composable
fun CoinCardExpressive(
    coin: Coin,
    onClick: (Coin) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(coin) }
            .padding(vertical = 4.dp), // breathing room inside ExpressiveListCard
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                model = coin.image,
                contentDescription = coin.name,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Fit
            )

            Column {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = coin.symbol.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${coin.currentPrice.toFixed(2)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            val change = coin.priceChangeWith24h ?: 0.0
            val changeColor =
                if (change >= 0.0) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error

            Text(
                text = "${change.toFixed(2)}%",
                style = MaterialTheme.typography.bodyMedium,
                color = changeColor
            )
        }
    }
}
