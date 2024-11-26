package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    // method to get all Categories
    public ArrayList <Category> getCategories () {
        return categories;
    }

    // method to add a new categories
    public void addCategories (Category category){
        categories.add(category);
    }

    // method to update a category if it exist
    public boolean updateCategory (String id, Category category){

        for (Category c : categories){
            if (c.getCategoryId().equals(id)){
                categories.set(categories.indexOf(c),category);
                return true;
            }
        }
        return false;
    }

    // method to delete a category if exist..
    public boolean deleteCategory (String id ){
        for (Category c : categories ){
            if (c.getCategoryId().equals(id)){
                categories.remove(categories.indexOf(c));
                return true;
            }
        }

        return false;
    }




}
