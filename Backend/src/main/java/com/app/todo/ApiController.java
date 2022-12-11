package com.app.todo;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// http://localhost:8080/swagger-ui/index.html
// http://localhost:8080/v3/api-docs/

//CRUD Controller class
//Create Read Update Delete
    
@RestController
@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600)
@RequestMapping("/todos")
public class ApiController {

    // internal list of todo items
    @Autowired
    private TodoRepositiory todoRepo;

    /*@Operation(summary = "Creates a Todo Item with path variable name and default priority of 2")
    @ApiResponses(value = 
    {
        @ApiResponse(responseCode = "201", description = "Item has been created" , content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)*/
    @PostMapping(path="/") // Map ONLY POST Requests
    public @ResponseBody String createAndAddTodoItem (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TodoItem item = new TodoItem();
        item.setTodo(name);
        todoRepo.save(item);
        return "Created and Saved a new item";
    }
/* 
    @PostMapping(path="/") 
    public @ResponseBody String createTodoItem (@RequestParam TodoItem item) {
        todoRepo.save(item);
        return "Saved a new item";
    }
*/
    @GetMapping(path="/")
    public @ResponseBody Iterable<TodoItem> getTodoItems() {
        // This returns a JSON or XML with the users
    //    ArrayList<TodoItem> items = new ArrayList<>();
    //    todoRepo.findAll().forEach(items::add);
    //    return items;
        return todoRepo.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody java.util.Optional<TodoItem> getTodoItemsById(@PathVariable String name) {

        return todoRepo.findById(name);
        //return todoRepo.findOne(id);
    }
 /* 
    @PutMapping(path="/{id}")
    public @ResponseBody String updateTodoItem (@RequestBody TodoItem item, @PathVariable int newPrio ){
        item.setPriority(newPrio);
        todoRepo.save(item);
        return "Item updated";
    }
    */
    @PutMapping(path="/")
    public @ResponseBody String updateTodoItem (@RequestBody TodoItem item){
        todoRepo.save(item);
        return "Item updated";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path="/")
    public @ResponseBody String deleteTodoItem (@RequestParam String name){
        todoRepo.deleteById(name); //name = id
        return "Item deleted";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteTodoItemById (@PathVariable String name){
        todoRepo.deleteById(name);
        return "Item deleted";
    }    
}
