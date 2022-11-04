package com.example.myapplication

//import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.material.TextField

@Composable
fun PopupCenterWindowDemo() {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            //Modifier.background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            var popup by remember { mutableStateOf(false) }
            var text by rememberSaveable { mutableStateOf("Text") }
            //var text by remember { mutableStateOf(TextFieldValue("")) }
            /*
            TextButton(onClick = { popup = true }) {
                Text("Open window popup")
            }
            */
            IconButton(
                onClick = { popup = true},
                modifier = Modifier.dashedBorder(1.dp, 5.dp, Color.DarkGray)
            )
            {
                Icon(
                    Icons.Filled.AddCircle, contentDescription="h",
                    tint=Color.DarkGray,
                    modifier=Modifier.size(height=20.dp, width=300.dp)
                )
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
            if (popup) {
                Popup(
                    popupPositionProvider = WindowCenterOffsetPositionProvider(),
                    onDismissRequest = { popup = false },
                ) {
                    Column(
                        modifier = Modifier.padding(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        /*
                        TextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            label = { Text("Label") },

                        )
                        */
                        TextField(
                            value = text,
                            label = { Text(text = "Number Input Type") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                text = it
                            }
                        )
                        TextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            label = { Text(text = "Your Label") },
                            placeholder = { Text(text = "Your Placeholder/Hint") },
                        )

                        //Text(text = "I'm popping up")
                        Spacer(modifier = Modifier.height(32.dp))
                        TextButton(onClick = { popup = false }) {
                            Text(text = "Close Popup")
                        }
                    }
                }
            }
        }
    }
}

class WindowCenterOffsetPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y
        )
    }
}


fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx(),
                size.height - width.toPx(),
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }
