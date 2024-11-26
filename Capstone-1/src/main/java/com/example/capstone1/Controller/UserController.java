package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity getUsers () {
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    // endpoint to add a new user
    @PostMapping("/add")
    public ResponseEntity addUser (@RequestBody @Valid User user, Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user added successfully"));
    }

    // endpoint to update existing user
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser (@PathVariable String id, @RequestBody @Valid User user, Errors errors){

        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean result = userService.updateUser(id,user);
        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("user updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("no user with this id was found"));
        }


    }

    // endpoint to delete a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser (@PathVariable String id){
        boolean result = userService.deleteUser(id);

        if (result){
            return ResponseEntity.status(200).body(new ApiResponse("user deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no user with this id was found"));

    }

    // endpoint for user to buy products
    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct (@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId){
        int result = userService.buyProduct(userId,productId,merchantId);
        if (result == 1){
            return ResponseEntity.status(200).body(new ApiResponse("user buy product successfully"));
        }
        if (result == 0){
            return ResponseEntity.status(400).body(new ApiResponse("no user with this id was found"));
        }
        if (result == -1){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not have stock"));
        }
        if (result == -2){
            return ResponseEntity.status(400).body(new ApiResponse("product does not exist"));
        }
        if (result == -3){
            return ResponseEntity.status(400).body(new ApiResponse("product is out of stock"));
        }
        if (result == -4){
            return ResponseEntity.status(400).body(new ApiResponse("user does not have enough balance"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("something went wrong!"));
    }

    // ========================= EXTRA Endpoint 1 =========================\\
    // endpoint to add products to the cart
    @PutMapping("/add-to-cart/{userId}/{productId}/{merchantId}")
    public ResponseEntity addToCart (@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId){
        int result = userService.addToCart(userId,productId,merchantId);
        if (result == 1){
            return ResponseEntity.status(200).body(new ApiResponse("product added to cart successfully"));
        }
        if (result == 0){
            return ResponseEntity.status(400).body(new ApiResponse("no user with this id was found"));
        }
        if (result == -1){
            return ResponseEntity.status(400).body(new ApiResponse("merchant does not have stock for this product"));
        }
        if (result == -2){
            return ResponseEntity.status(400).body(new ApiResponse("product does not exist"));
        }
        if (result == -3){
            return ResponseEntity.status(400).body(new ApiResponse("product is out of stock"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("something went wrong!"));
    }

    // ========================= EXTRA Endpoint 2 =========================\\
    // Endpoint to checkout the cart
    @PutMapping("/checkout/{userId}")
    public ResponseEntity checkout(@PathVariable String userId) {
        int result = userService.checkout(userId);

        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Checkout successful! All items purchased."));
        }
        if (result == -5) { // Partial failure
            return ResponseEntity.status(200).body(new ApiResponse("Checkout partially successful. Some items could not be purchased."));
        }
        if (result == -6) {
            return ResponseEntity.status(400).body(new ApiResponse("cart is empty. Please add items to your cart before checkout."));
        }
        if (result == -7) {
            return ResponseEntity.status(400).body(new ApiResponse("Checkout failed. No items in your cart could be purchased."));
        }
        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("No user with this ID was found."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Something went wrong during checkout!"));
    }

    // ========================= EXTRA Endpoint 3 =========================\\
    //endpoint to for user to add rating to a product
    @PostMapping("/add-rating/{userId}/{productId}/{rating}")
    public ResponseEntity addRating (@PathVariable String userId, @PathVariable String productId, @PathVariable double rating){
        int result = userService.addRating(userId,productId,rating);
        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Rating added successfully"));
        }
        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("no user with this id was found"));
        }
        if (result == -1) {
            return ResponseEntity.status(400).body(new ApiResponse("product does not exist"));
        }
        if (result == -2) {
            return ResponseEntity.status(400).body(new ApiResponse("user is not customer"));
        }
        if (result == -3) {
            return ResponseEntity.status(400).body(new ApiResponse("rating must be between 1 and 5"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("something went wrong!"));

    }

    // ========================= EXTRA Endpoint 5 =========================\\
    @GetMapping("/get-similar/{userId}")
    public ResponseEntity generateSimilarProducts(@PathVariable String userId){
        ArrayList<Product> similarProducts = userService.generateSimilarProducts(userId);
        if (similarProducts == null) {
            return ResponseEntity.status(400).body(new ApiResponse("history not found"));
        }
        return ResponseEntity.status(200).body(similarProducts);
    }

}
