package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {

    private val _itemList = MutableLiveData<List<Todo>>()
    val itemList: LiveData<List<Todo>> = _itemList

    fun getTodo() {
        _itemList.value = TodoManager.getTodo()
    }

    fun addTodo(title: String) {
        TodoManager.addTodo(title)
        getTodo()
    }

    fun deleteTodo(id: Int) {
        TodoManager.deleteTodo(id)
        getTodo()
    }

    fun moveUpTodo(id: Int){
        TodoManager.moveUpTodo(id)
        getTodo()
    }

    fun moveDownTodo(id: Int){
        TodoManager.moveDownTodo(id)
        getTodo()
    }
}