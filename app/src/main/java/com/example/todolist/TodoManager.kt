package com.example.todolist

object TodoManager {
    private val todoList = mutableListOf<Todo>()
    var itemId = 1

    fun getTodo(): List<Todo>{
        return todoList
    }

    fun addTodo(title: String) {
        todoList.add(Todo(itemId, title))
        itemId++

    }

    fun deleteTodo(id: Int) {
        todoList.removeIf {
            it.id == id
        }
        itemId--
    }

    fun moveUpTodo(id: Int){
        val index = todoList.indexOfFirst { it.id == id }
        if (index > 0 ){
            val temp = todoList[index]
            todoList[index] = todoList[index - 1]
            todoList[index - 1] = temp
        }
    }

    fun moveDownTodo(id: Int){
        val index = todoList.indexOfFirst { it.id == id }
        if (index > 0 && index < todoList.size - 1){
            val temp = todoList[index]
            todoList[index] = todoList[index + 1]
            todoList[index + 1] = temp
        }
    }
}