package com.aarya.chatapplication.presentation.chat

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    username : String?,
    viewModel: ChatViewModel = koinViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver{ _, event ->
            if(event == Lifecycle.Event.ON_START){
                viewModel.connectToChat()
            }else if (event == Lifecycle.Event.ON_STOP){
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state = viewModel.state.value

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            reverseLayout = true
        ){
            item{
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(state.messages){message ->
                val isOwnMessage = message.userName == username
                Box(
                    contentAlignment = if(isOwnMessage){
                        Alignment.CenterEnd
                    }else Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .drawBehind {
                                val cornerRadius = 10.dp.toPx()
                                val triangleHeight = 15.dp.toPx()
                                val triangleWidth = 20.dp.toPx()
                                val trianglePath = Path().apply{
                                    if(isOwnMessage){
                                        moveTo(size.width, size.height-cornerRadius)
                                        lineTo(size.width, size.height+triangleHeight)
                                        lineTo(size.width-triangleWidth, size.height-cornerRadius)
                                        close()
                                    }else{
                                        moveTo(0f, size.height-cornerRadius)
                                        lineTo(0f, size.height+triangleHeight)
                                        lineTo(triangleWidth, size.height-cornerRadius)
                                        close()
                                    }
                                }
                                drawPath(
                                    path = trianglePath,
                                    color = if(isOwnMessage) Color.Cyan else Color.Gray
                                )
                            }
                            .background(
                                color = if(isOwnMessage) Color.Cyan else Color.Gray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    ){
                        Text(
                            text = message.userName,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text (
                            text = message.text,
                            color = Color.White
                        )

                        Text(
                            text = message.formattedTime,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
          TextField(value = viewModel.messageText.value,
              onValueChange = viewModel:: onMessageChange,
              placeholder = {
                  Text ( text = "Enter A Message")
              },
              modifier = Modifier.weight(1f)
          )
            IconButton(onClick = viewModel::sendMessage,){
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "send"
                )
            }
        }
    }

}