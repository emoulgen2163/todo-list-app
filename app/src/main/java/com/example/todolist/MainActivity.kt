package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val todoViewModel = ViewModelProvider(this) [TodoViewModel::class.java]
        setContent {
            ToDoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoList(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = todoViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun TodoList(modifier: Modifier = Modifier, viewModel: TodoViewModel) {

    val todoList by viewModel.itemList.observeAsState()

    var text by rememberSaveable {
        mutableStateOf("")
    }

    Column(modifier
        .fillMaxHeight()
        .padding(8.dp)) {
        Row(modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it }
            )

            Spacer(modifier.weight(1f))

            FloatingActionButton(
                onClick = {
                    viewModel.addTodo(text)
                    text = ""
                },
                Modifier.width(80.dp)) {
                Text("Add")
            }
        }

        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it){
                            index: Int, item: Todo ->
                        TodoItem(
                            item,
                            viewModel
                        )
                    }
                }
            )

        } ?: Text("No items", Modifier.fillMaxWidth())


    }
}

@Composable
fun TodoItem(item: Todo, viewModel: TodoViewModel) {

    Row(Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .border(5.dp, MaterialTheme.colorScheme.primary)
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically)
        {
            Column(Modifier.weight(1f)) {
                Text(item.title)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.deleteTodo(item.id) }) {
                    Icon(painterResource(R.drawable.baseline_delete_outline_24), contentDescription = null)
                }

                Column {
                    IconButton(onClick = { viewModel.moveUpTodo(item.id) }) {
                        Icon(painterResource(R.drawable.baseline_arrow_upward_24), contentDescription = null)
                    }

                    IconButton(onClick = { viewModel.moveDownTodo(item.id) }) {
                        Icon(painterResource(R.drawable.baseline_arrow_downward_24), contentDescription = null)
                    }
                }
            }
    }

}

data class Todo(var id: Int, val title: String)

fun fakeTodo(): List<Todo>{
    return listOf(Todo(1, "cemal"))
}
