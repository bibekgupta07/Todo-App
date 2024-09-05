//package com.example.todoapp
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.getValue // This import is needed
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.setValue // This import is needed
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//@Composable
//fun TodoListPage(viewModel: TodoViewModel) {
//
//    val todolist by viewModel.todoList.observeAsState()
//    var inputText by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxHeight()
//            .padding(8.dp)
//    ){
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(9.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            OutlinedTextField(
//                value = inputText ,
//                onValueChange = {
//                    inputText = it
//                } )
//            Button(onClick = { }) {
//                Text(text = "Add")
//            }
//        }
//
//        todoList?. let { list ->
//            LazyColumn(
//                content = {
//                    itemsIndexed(it){ index: Int, item: Todo ->
//                        TodoItem(item = item )
//
//                    }
//                }
//            )
//        }
//    }
//        }
//
//@Composable
//fun TodoItem(item: Todo){
//   Row(
//       modifier = Modifier
//           .fillMaxWidth()
//           .padding(8.dp)
//           .clip(RoundedCornerShape(16.dp))
//           .background(MaterialTheme.colorScheme.primary)
//           .padding(16.dp),
//       verticalAlignment = Alignment.CenterVertically
//   ){
//       Column(
//           modifier = Modifier.weight(1f)
//       ) {
//           Text(
//               text = SimpleDateFormat("HH:mm:aa, dd/mm",Locale.ENGLISH).format(item.createdAt),
//               fontSize = 12.sp,
//               color = Color.White
//               )
//           Text(
//               text = item.title.toString(),
//               fontSize = 20.sp,
//               color = Color.White
//               )
//       }
//       IconButton(onClick = { }) {
//           Icon(
//               painter = painterResource(id = R.drawable.baseline_auto_delete_24),
//               contentDescription = "Delete")
//       }
//   }
//
//}


package com.example.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue // This import is needed
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue // This import is needed
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel) {

    // Observing the live data for the todo list
    val todolist by viewModel.todoList.observeAsState(emptyList()) // Use an empty list as the default value
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                }
            )
            Button(onClick = {
                if (inputText.isNotEmpty()) {
                    viewModel.addTodo(inputText)
                    inputText = ""
                }
            }) {
                Text(text = "Add")
            }
        }

        // Check if the list is empty or null and display a message if so
        if (todolist.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No items yet",
                fontSize = 16.sp
            )
        } else {
            LazyColumn(
                content = {
                    itemsIndexed(todolist) { index: Int, item: Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                    }
                }
            )
        }
    }
}

@Composable
fun TodoItem(
    item: Todo,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm aa, dd/MM", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_auto_delete_24),
                contentDescription = "Delete"
            )
        }
    }
}
