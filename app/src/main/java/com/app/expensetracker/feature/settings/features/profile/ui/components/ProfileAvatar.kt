package com.app.expensetracker.feature.settings.features.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.expensetracker.core.utils.initials

@Composable
fun ProfileAvatar(
    name: String,
    imageUrl: String?,
    onEditClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {

        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                . background(
                   MaterialTheme.colorScheme.surface
                ),
            contentAlignment = Alignment.Center
        ) {

            if (!imageUrl.isNullOrBlank()) {

                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            } else {

                // Initials placeholder
                Text(
                    text = name.initials(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

      /*  FloatingActionButton(
            onClick = onEditClick,
            modifier = Modifier.size(42.dp),
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.White
            )
        }*/
    }
}