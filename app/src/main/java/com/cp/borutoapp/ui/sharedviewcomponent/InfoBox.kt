package com.cp.borutoapp.ui.sharedviewcomponent

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.ContentAlpha
import com.cp.borutoapp.R
import com.cp.borutoapp.ui.theme.INFO_BOX_ICON_SIZE
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import com.cp.borutoapp.ui.theme.titleColor

@Composable
fun InfoBox(
    icon: Painter,
    iconColor: Color,
    bigText: String,
    smallText: String,
    textColor: Color
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = SMALL_PADDING)
                .size(INFO_BOX_ICON_SIZE),
            painter = icon,
            contentDescription = stringResource(R.string.info_icon),
            tint = iconColor
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = bigText,
                color = textColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Black
            )

            Text(
                modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoBoxPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_bolt),
        iconColor = Color.Blue,
        bigText = "90",
        smallText = "Power",
        textColor = MaterialTheme.colorScheme.titleColor
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000)
@Composable
fun InfoBoxPreviewInDarkMode() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_bolt),
        iconColor = Color.Blue,
        bigText = "90",
        smallText = "Power",
        textColor = MaterialTheme.colorScheme.titleColor
    )
}
