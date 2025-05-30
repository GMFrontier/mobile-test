package com.frommetoyou.core_ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.superformulachallenge.common.R

@Composable
fun SuperFab(
    navigateToQrGenerator: () -> Unit,
    navigateToQrScanner: () -> Unit
) {
    var expandedFAB by remember { mutableStateOf(false) }
    val items = listOf(
        FabItem(
            icon = Icons.Filled.Search,
            title = UiText.StringResource(R.string.scan).asString(),
            navigation = navigateToQrScanner,
            description = UiText.StringResource(R.string.scan).asString()
        ),
        FabItem(
            icon = Icons.Filled.Share,
            title = UiText.StringResource(R.string.qr_code_option).asString(),
            navigation = navigateToQrGenerator,
            description = UiText.StringResource(R.string.qr_code_option).asString()
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        var transition =
            updateTransition(targetState = expandedFAB, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 360f else 0f
        }

        AnimatedVisibility(
            visible = expandedFAB,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(items.size) {
                    Fab(
                        items[it].icon,
                        items[it].title,
                        navigation = {
                            expandedFAB = !expandedFAB
                            items[it].navigation.invoke()
                        },
                        description = items[it].description
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier.rotate(rotation)
                .testTag(UiText.StringResource(R.string.fab).asString()),
            onClick = { expandedFAB = !expandedFAB },
        ) {
            Icon(Icons.Filled.Add, UiText.StringResource(R.string.fab).asString())
        }
    }
}

@Composable
fun Fab(
    icon: ImageVector,
    title: String,
    navigation: () -> Unit,
    description: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Card(
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = title, modifier = Modifier.padding(4.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = navigation,
            modifier = Modifier.size(42.dp)
                .testTag(description)
        ) {
            Icon(imageVector = icon, contentDescription = UiText.StringResource(R.string.item).asString())
        }
    }
}

data class FabItem(
    val icon: ImageVector,
    val title: String,
    val navigation: () -> Unit,
    val description: String
)