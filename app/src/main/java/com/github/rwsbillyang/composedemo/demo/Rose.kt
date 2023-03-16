package com.github.rwsbillyang.composedemo.demo


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.rwsbillyang.composedemo.R
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme


@Composable
fun Rose(
    angle: Float,
    rotation: Int,
    modifier: Modifier = Modifier
)
{
    Image(
        modifier = modifier
            .padding(COMPASS_PADDING)
            .fillMaxSize()
            .rotate(angle),
        painter = painterResource(id = R.drawable.ic_rose),
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(
            color = MaterialTheme.colorScheme.onBackground
        )
    )

    Text(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        text = stringResource(id = R.string.degree_format, rotation),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineSmall
    )
}


// region previews

@Preview(showBackground = true)
@Composable
fun PreviewRoss()
{
    ComposeDemoTheme {
        Rose(angle = 30f, rotation = -45)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRossDark()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        Rose(angle = 30f, rotation = -45)
    }
}


// endregion