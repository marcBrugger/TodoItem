package com.app.todo;

import org.springframework.data.repository.CrudRepository;

import com.app.todo.TodoItem;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TodoRepositiory extends CrudRepository<TodoItem, String>{
    
}
