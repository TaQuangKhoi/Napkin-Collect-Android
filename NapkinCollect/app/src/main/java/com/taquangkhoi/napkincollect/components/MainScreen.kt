package com.taquangkhoi.napkincollect.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taquangkhoi.napkincollect.R

@Composable
fun MainScreen() {
    val imageModifier = Modifier
        .size(150.dp)
        .border(BorderStroke(1.dp, Color.Black))
        .background(Color.Yellow)

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = imageModifier
    )

//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.napkin_logo_analogue_black),
//            contentDescription = "Logo",
//            modifier = Modifier
//                .constrainAs(appName) {
//                    top.linkTo(parent.top, margin = 20.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .height(70.dp)
//                .width(ConstraintLayout.Dimension.wrapContent)
//        )
//
//        IconButton(
//            onClick = { /* Handle settings icon click */ },
//            modifier = Modifier
//                .constrainAs(settingsIcon) {
//                    top.linkTo(appName.top)
//                    bottom.linkTo(appName.bottom)
//                    end.linkTo(parent.end, margin = 16.dp)
//                }
//                .size(40.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_settings),
//                contentDescription = "Settings"
//            )
//        }
//
//        TextField(
//            value = "", // Set your initial value here
//            onValueChange = { /* Handle value change */ },
//            modifier = Modifier
//                .constrainAs(thoughtInput) {
//                    top.linkTo(appName.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(5.dp)
//                .heightIn(min = 200.dp)
//                .fillMaxWidth(),
//            textStyle = MaterialTheme.typography.body1,
//            placeholder = { Text("Enter your thought") },
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent
//            ),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Send
//            ),
//            keyboardActions = KeyboardActions(
//                onSend = {
//                    /* Handle send action */
//                }
//            )
//        )
//
//        TextField(
//            value = "", // Set your initial value here
//            onValueChange = { /* Handle value change */ },
//            modifier = Modifier
//                .constrainAs(sourceUrlInput) {
//                    top.linkTo(thoughtInput.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(5.dp)
//                .heightIn(min = 56.dp)
//                .fillMaxWidth(),
//            textStyle = MaterialTheme.typography.body1,
//            placeholder = { Text("Source of Thought") },
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent
//            ),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text
//            )
//        )
//
//        Button(
//            onClick = { /* Handle send button click */ },
//            modifier = Modifier
//                .constrainAs(sendButton) {
//                    top.linkTo(sourceUrlInput.bottom)
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .fillMaxWidth()
//                .height(56.dp)
//        ) {
//            Text(text = "Send", fontSize = 30.sp)
//        }
//
//        IconButton(
//            onClick = { /* Handle clear text button click */ },
//            modifier = Modifier
//                .constrainAs(clearTextButton) {
//                    top.linkTo(sourceUrlInput.bottom)
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(sendButton.end, margin = 10.dp)
//                }
//                .size(40.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_rewrite),
//                contentDescription = "Clear Text"
//            )
//        }
//    }
}

@Preview(showBackground = false)
@Composable
fun MainScreenPreview() {
    MainScreen()
}