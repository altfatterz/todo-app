package com.github.altfatterz.todoapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping({"/", })
    public String list(Model model) {
        model.addAttribute("todos", repository.findAllActiveTodos());
        return "index";
    }

    @PostMapping("/todos")
    public String add(@RequestParam String task, Model model) {
        repository.save(new Todo(null, task, false));
        model.addAttribute("todos", repository.findAllActiveTodos());
        // This tells Thymeleaf to only render the 'todo-list' fragment
        //  inside the index.html file
        return "index :: todo-list";
    }

    @DeleteMapping("/todos/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/todos/{id}/complete")
    public String complete(@PathVariable Long id, Model model) {
        repository.findById(id).ifPresent(todo -> {
            repository.save(new Todo(todo.id(), todo.task(), true));
        });
        model.addAttribute("todos", repository.findAllActiveTodos());
        return "index :: todo-list";
    }
}