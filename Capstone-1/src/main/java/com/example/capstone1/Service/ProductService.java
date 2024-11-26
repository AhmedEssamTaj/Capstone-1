package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@AllArgsConstructor
@Service
public class ProductService {

    private final CategoryService categoryService;
    ArrayList<Product> products = new ArrayList<>();

    // method to get all products
    public ArrayList <Product> getProducts () {
        return products;
    }

    // method to add a new product
    public int addProduct (Product product){
        // make sure category already exist
        for (Category c : categoryService.categories){
            if (c.getCategoryId().equals(product.getCategoryId())){
                products.add(product);
                return 1;
            }
        }
        return -1;
    }

    // method to update a product if it exist
    public int updateProduct (String id, Product product) {
        boolean categoryExists = false;
        boolean productExists = checkIfProductExist(id);
        if (!productExists) {// product not exist
            return 0;
        }
        // check if the category exist
        for (Category c : categoryService.categories) {
            if (c.getCategoryId().equals(product.getCategoryId())) {
                categoryExists = true;
            }
        }

            if (!categoryExists) {
                return -1;
            }
            for (Product p : products) {
                if (p.getProductId().equals(id)) {
                    products.set(products.indexOf(p), product);
                    return 1;
                }
            }
          return 5;
        }

        // method to delete a product if exist..
        public boolean deleteProduct (String id){
            for (Product p : products) {
                if (p.getProductId().equals(id)) {
                    products.remove(products.indexOf(p));
                    return true;
                }
            }

            return false;
        }


public boolean checkIfProductExist (String id){
            for (Product p : products) {
                if (p.getProductId().equals(id)) {
                    return true;
                }
            }
            return false;
        }


}
