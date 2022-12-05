package com.app.todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// http://localhost:8080/swagger-ui/index.html
// http://localhost:8080/v3/api-docs/

//CRUD Controller class
//Create Read Update Delete
/*
@RestController
@Controller
@RequestMapping("/todos")
public class ApiController {

    // internal list of todo items
    @Autowired
    private ArrayList<TodoItem> items = new ArrayList<TodoItem>();

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String createAndAddTodoItem (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TodoItem item = new TodoItem(name);
        items.add(item);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody List<TodoItem> getTodoItems() {
        // This returns a JSON or XML with the users
        return items;
    }*/
    
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
    //@CrossOrigin(origins = "http://localhost:*")
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

    /*
    @RestController
    @Controller
    @RequestMapping("/todos")
    public class ApiController {

    // internal list of todo items
    @Autowired
    private ArrayList<TodoItem> items = new ArrayList<TodoItem>();
    // Add new item to list
    // version 1: using path variables
    @Operation(summary = "Creates a Todo Item with path variable name and default priority of 2")
    @ApiResponses(value = 
    {
        @ApiResponse(responseCode = "201", description = "Item has been created" , content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{name}")
    public TodoItem createAndAddTodoItem(@PathVariable String name){

        TodoItem item = new TodoItem(name);
        items.add(item);

        return item;
    }

    // Add new item to list
    // version 2: using a JSON object as input
    // TODO: Allow only one item with certain name in list.

    // Be careful when importing RequestBody
    @Operation(summary = "Creates a Todo Item with a JSON object as request paramter")
    @ApiResponses(value = 
    {
        @ApiResponse(responseCode = "201", description = "Item has been created" , content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public TodoItem addTodoItem(@RequestBody TodoItem item){

        items.add(item);
        return item;
    }

    // List all elements in ArrayList
    @Operation(summary = "Returns a list of ToDo items")
    @GetMapping(value = "/", produces = "application/json")
    @ApiResponses(value = 
                    {
                        @ApiResponse(responseCode = "200", description = "List all items" , content = @Content)
                    })
    @ResponseStatus(HttpStatus.OK)
    public List<TodoItem> getTodoItems(){

        return items;
    }

    @Operation(summary = "Find a ToDo item by its itemId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TodoItem.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid itemId supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content) })
    @GetMapping(produces = "application/json", path = "/{itemId}")
    Optional<TodoItem> getTodoItem(@PathVariable String itemId) {

        TodoItem tempItem = new TodoItem(itemId);
        Optional<TodoItem> returnItem = Optional.empty();

        for(TodoItem item : items){

            if (item.equals(tempItem)) returnItem = Optional.of(item);

        }
        // TODO: Return 404 in case of item not found
        return returnItem;

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = "application/json", path = "/{itemId}")
    TodoItem deleteTodoItem(@PathVariable String itemId){

        TodoItem tempItem = new TodoItem(itemId);
        Iterator<TodoItem> iterator = items.iterator();

        while(iterator.hasNext()){

            if(iterator.next().equals(tempItem))
                iterator.remove();

        }

        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(consumes = "application/json", produces = "application/json", path = "/")
    TodoItem deleteTodo(@RequestBody TodoItem item){

        items.remove(item);

        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = "application/json", produces = "application/json", path = "/")
    TodoItem updateTodo(@RequestBody TodoItem todoItem){

        for(TodoItem item : items){

            if (item.equals(todoItem)) item.setPriority(todoItem.getPriority());
            return item;

        }

        items.add(todoItem);
        return todoItem;

    }
    */
    
}
