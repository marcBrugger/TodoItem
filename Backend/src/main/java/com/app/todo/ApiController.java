package com.app.todo;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Creates a todo item via a request parameter 'name' and sets the default priority to 2")
    //@ApiResponses(value = { @ApiResponse(code=200, message="success")})
    @ApiResponses(value = {
        //@ApiResponse(responseCode = "201", description = "Item has been created" , content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/") // Map ONLY POST Requests
    public @ResponseBody String createAndAddTodoItem (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TodoItem item = new TodoItem();
        //boolean checkAvailable = todoRepo.findById(name);
        if(todoRepo.findById(name).isPresent() == true){
            return "Already existing";
        }else{
            System.out.println(todoRepo.findById(name));
            item.setTodo(name);
            todoRepo.save(item);
            return "Created and Saved a new item";
        }
    }

    @Operation(summary = "Creates a todo item via a path variable 'name' and sets the default priority to 2")
    //@ApiResponses(value = { @ApiResponse(code=200, message="success")})
    @ApiResponses(value = {
        //@ApiResponse(responseCode = "201", description = "Item has been created" , content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/{name}") // Map ONLY POST Requests
    public @ResponseBody String createAndAddTodoItem2 (@PathVariable String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TodoItem item = new TodoItem();
        //boolean checkAvailable = todoRepo.findById(name);
        if(todoRepo.findById(name).isPresent() == true){
            return "Already existing";
        }else{
            System.out.println(todoRepo.findById(name));
            item.setTodo(name);
            todoRepo.save(item);
            return "Created and Saved a new item";
        }
    }
/* 
    @Operation(summary = "Creates a Todo Item with a request parameter name and a default priority of 2")
    @PostMapping(path="/") 
    public @ResponseBody String createTodoItem (@RequestParam TodoItem item) {
        todoRepo.save(item);
        return "Saved a new item";
    }
*/
    @Operation(summary = "Get all items")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/")
    public @ResponseBody Iterable<TodoItem> getTodoItems() {
        // This returns a JSON or XML with the users
    //    ArrayList<TodoItem> items = new ArrayList<>();
    //    todoRepo.findAll().forEach(items::add);
    //    return items;
        return todoRepo.findAll();
    }

    @Operation(summary = "Get one specific item via a path variable")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/id/{id}")
    public @ResponseBody java.util.Optional<TodoItem> getTodoItemsById(@PathVariable String id) {
        if(todoRepo.findById(id).isPresent() == true){
            return todoRepo.findById(id);
            //return todoRepo.findOne(id);
        }else{
            // in case id is not existing
            return null;
        }
    }
 /* 
    @PutMapping(path="/{id}")
    public @ResponseBody String updateTodoItem (@RequestBody TodoItem item, @PathVariable int newPrio ){
        item.setPriority(newPrio);
        todoRepo.save(item);
        return "Item updated";
    }
    */
    @Operation(summary = "Exchange the priotity of an item via a request body")
	@ApiResponses(value= 
	{
			//@ApiResponse(responseCode = "204", description = "Item has been updated", content = @Content)
	})
	@ResponseStatus(HttpStatus.OK)
    @PutMapping(path="/")
    public @ResponseBody String updateTodoItem (@RequestBody TodoItem item){
        String name = item.getTodo();
        if(todoRepo.findById(name).isPresent() == true){
            todoRepo.save(item);
            return "Item updated";
        }else{
            // in case id is not existing
            return "Can not update a non existing item";
        }
    }

    @Operation(summary = "Deletes one item via a request parameter")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path="/")
    public @ResponseBody String deleteTodoItem (@RequestParam String id){
        if(todoRepo.findById(id).isPresent() == true){
            todoRepo.deleteById(id); //name = id
            return "Item deleted";
        }else{
            // in case id is not existing
            return "Item isn't existing";
        }
    }

    @Operation(summary = "Deletes one item via a path variable")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteTodoItemById (@PathVariable String id){
        if(todoRepo.findById(id).isPresent() == true){
            todoRepo.deleteById(id); //name = id
            return "Item deleted";
        }else{
            // in case id is not existing
            return "Item isn't existing";
        }
    }    
}
