package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity getCategories() {
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    // endpoint to add a new category
    @PostMapping("/add")
    public ResponseEntity addCategory (@RequestBody @Valid Category category, Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        categoryService.addCategories(category);
        return ResponseEntity.status(200).body(new ApiResponse("category added successfully"));
    }

    // endpoint to update existing category
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory (@PathVariable String id, @RequestBody @Valid Category category, Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean result = categoryService.updateCategory(id,category);
        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("category updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("no category with this id was found"));
        }


    }

    // endpoint to delete a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory (@PathVariable String id){
        boolean result = categoryService.deleteCategory(id);

        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no category with this id was found"));

    }
}
