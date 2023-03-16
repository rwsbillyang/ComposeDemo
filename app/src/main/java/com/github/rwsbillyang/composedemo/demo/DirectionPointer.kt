package com.github.rwsbillyang.composedemo.demo


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.rwsbillyang.composedemo.R
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme


@Composable
fun DirectionPointer(
    @DrawableRes pointerIcon: Int,
    @StringRes contentDsc: Int,
    modifier: Modifier = Modifier,
    angle: Float = 0f,
)
{
    Image(
        modifier = modifier
            .padding(COMPASS_PADDING)
            .rotate(angle)
            .fillMaxSize(),
        painter = painterResource(id = pointerIcon),
        contentDescription = stringResource(id = contentDsc),
        contentScale = ContentScale.Fit,
    )
}

// region previews


// endregion

@Preview(showBackground = true)
@Composable
fun PreviewPointerArrow()
{
    ComposeDemoTheme {
        DirectionPointer(
            pointerIcon = R.drawable.ic_pointerdot,
            contentDsc = R.string.destination_direction,
            angle = 45f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerArrowDark()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        DirectionPointer(
            pointerIcon = R.drawable.ic_pointerdot,
            contentDsc = R.string.destination_direction,
            angle = 45f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerLine()
{
    ComposeDemoTheme {
        DirectionPointer(
            pointerIcon = R.drawable.ic_line,
            contentDsc = R.string.destination_direction,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPointerLineDark()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        DirectionPointer(
            pointerIcon = R.drawable.ic_line,
            contentDsc = R.string.destination_direction,
        )
    }
}