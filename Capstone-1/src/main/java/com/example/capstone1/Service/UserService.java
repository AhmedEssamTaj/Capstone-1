package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final CategoryService categoryService;



    // method to get all users
    public ArrayList <User> getUsers () {
        return users;
    }

    // method to add a new user
    public void addUser (User user){
        if (user.getCart() == null) {
            user.setCart(new ArrayList<>()) ;
            user.setHistory(new ArrayList<>()) ;
        }
        users.add(user);
    }

    // method to update a user if it exist
    public boolean updateUser (String id, User user){

        for (User u : users){
            if (u.getUserId().equals(id)){
                users.set(users.indexOf(u),user);
                return true;
            }
        }
        return false;
    }

    // method to delete a user if exist..
    public boolean deleteUser (String id ){
        for (User u : users ){
            if (u.getUserId().equals(id)){
                users.remove(users.indexOf(u));
                return true;
            }
        }

        return false;
    }


    // method to buy a product
    public int buyProduct (String userId ,String productId, String merchantId){

        boolean userIdExists = false;
        boolean productExists = false;
        boolean merchantIdExists = false;
        int userIndex =-1;
        int productIndex =-1;
        int merchantStockIndex =-1;

        for (User u : users){
            if (u.getUserId().equals(userId)){
                userIdExists = true;
                userIndex = users.indexOf(u);
            }
        }
        if (!userIdExists){
            return 0; // 0 --> user does not exist
        }

        for (MerchantStock m : merchantStockService.merchantStocks){
            if (m.getMerchantId().equals(merchantId) && m.getProductId().equals(productId)){
                merchantIdExists = true;
                merchantStockIndex= merchantStockService.merchantStocks.indexOf(m);
                if(m.getStock() <1){
                    return -3; // product is out of stock
                }
            }
        }
        if (!merchantIdExists){
            return -1; // merchant does not exist
        }

        for (Product p : productService.products){
            if (p.getProductId().equals(productId)){
                productExists = true;
                productIndex = productService.products.indexOf(p);
            }
        }
        if (!productExists){
            return -2; // product does not exist
        }

        if (users.get(userIndex).getBalance() < productService.products.get(productIndex).getProductPrice()){
            return -4; // user does not have enough balance
        }

        users.get(userIndex).setBalance(users.get(userIndex).getBalance() - productService.products.get(productIndex).getProductPrice());
        merchantStockService.merchantStocks.get(merchantStockIndex).setStock(
                merchantStockService.merchantStocks.get(merchantStockIndex).getStock() - 1);
        users.get(userIndex).getHistory().add(productService.products.get(productIndex));
        return 1; // product bought successfully

    }


    // ========================= EXTRA METHOD 1 =========================\\
    // method to add product to cart
    public int addToCart(String userId ,String productId, String merchantId){
        boolean userIdExists = false;
        boolean productExists = false;
        boolean merchantIdExists = false;
        int userIndex =-1;
        int productIndex =-1;
        int merchantStockIndex =-1;
        for (User u : users){
            if (u.getUserId().equals(userId)){
                userIdExists = true;
                userIndex = users.indexOf(u);
            }
        }
        if (!userIdExists){
            return 0;// user does not exist
        }
        for (MerchantStock m : merchantStockService.merchantStocks){
            if (m.getMerchantId().equals(merchantId) && m.getProductId().equals(productId)){
                merchantIdExists = true;
                merchantStockIndex= merchantStockService.merchantStocks.indexOf(m);
            }
        }
        if (!merchantIdExists){
            return -1; // merchent does not have stock for this product
        }
        for (Product p : productService.products){
            if (p.getProductId().equals(productId)){
                productExists = true;
                productIndex = productService.products.indexOf(p);
            }
        }
        if (!productExists){
            return -2;// product does not exist
        }
        if (merchantStockService.merchantStocks.get(merchantStockIndex).getStock() == 0){
            return -3;
        }
        users.get(userIndex).getCart().add(productService.products.get(productIndex));
        return 1;
    }


    // ========================= EXTRA METHOD 2 =========================\\
    // method to checkout cart
    public int checkout(String userId) {
        boolean userIdExists = false;
        int userIndex = -1;

        // Check if user exists
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                userIdExists = true;
                userIndex = users.indexOf(u);
                break;
            }
        }
        if (!userIdExists) {
            return 0; // User does not exist
        }


        if (users.get(userIndex).getCart().isEmpty()) {
            return -6; // Cart is empty
        }

        boolean anySuccessful = false;
        ArrayList<Product> failedProducts = new ArrayList<>();

        // Process the cart
        for (Product product : users.get(userIndex).getCart()) {
            boolean purchaseSuccess = false;

            for (MerchantStock stock : merchantStockService.merchantStocks) {
                if (stock.getProductId().equals(product.getProductId()) && stock.getStock() > 0) {
                    // buy the product
                    int result = buyProduct(userId, product.getProductId(), stock.getMerchantId());

                    if (result == 1) {
                        purchaseSuccess = true; // Purchase successful
                        anySuccessful = true;
                        break;
                    }
                }
            }

            if (!purchaseSuccess) { // failed buy
                failedProducts.add(product); // save failed purchases
            }
        }

        users.get(userIndex).getCart().clear();

        if (!anySuccessful) {
            return -7;// no items are bought sucessfully
        }
        if (failedProducts.isEmpty()) {

            System.out.println("Failed to buy the following products:");
            for (Product p : failedProducts) {
                System.out.println(" * " + p.getProductName());

                return -5; // success but some products failed to buy
            }
        }
        return 1; // all items purchased successfully
    }



    // ========================= EXTRA METHOD 3 =========================\\
    // method for user to add rating to a product ..
    public int addRating (String userId ,String productId,double rating){
        if(rating<1 || rating>5){
            return -3;// rating must be between 1 and 5
        }
        boolean userIdExists = false;
        int userIndex = -1;
        boolean productExists = false;
        int productIndex = -1;
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                userIdExists = true;
                userIndex = users.indexOf(u);
                break;
            }
        }
        if (!userIdExists) {
            return 0; // user does not exist
        }

        for (Product p : productService.products) {
            if (p.getProductId().equals(productId)) {
                productExists = true;
                productIndex = productService.products.indexOf(p);
                break;
            }
        }
        if (!productExists) {
            return -1; // product does not exist
        }
        if (users.get(userIndex).getRole().equals("Admin")) {
            return -2; // user is an admin and should not be able to rate
        }
        // check if user bought this product or no
        boolean productPurchesed = false;
        for (Product p : users.get(userIndex).getHistory()) {
            if (p.getProductId().equals(productId)) {
                productPurchesed = true;
            }
        }
        if (!productPurchesed) {
            return -4;// user did not purchase this product
        }
        double totalOfRatings =    productService.products.get(productIndex).getRatingCount() *   productService.products.get(productIndex).getAverageRating();
        productService.products.get(productIndex).setRatingCount(  productService.products.get(productIndex).getRatingCount()+1);
        double newAvg = (totalOfRatings + rating)/   productService.products.get(productIndex).getRatingCount();
        newAvg = (double) Math.round(newAvg * 10) /10;
        productService.products.get(productIndex).setAverageRating(newAvg);
        return 1; // rating added successfully

    }


    // ========================= EXTRA METHOD 5 =========================\\

    public ArrayList<Product> generateSimilarProducts(String userId) {

        User targetUser = null;
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                targetUser = u;
                break;
            }
        }

        // If user doesn't exist or has no history, return null
        if (targetUser == null || targetUser.getHistory().isEmpty()) {
            return null;
        }


        Map<String, Integer> categoryCount = new HashMap<>();
        for (Product p : targetUser.getHistory()) {
            String categoryId = p.getCategoryId();
            categoryCount.put(categoryId, categoryCount.getOrDefault(categoryId, 0) + 1);
        }

        //find the category with the highest purchases
        String mostPurchasedCategory = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPurchasedCategory = entry.getKey();
            }
        }

        // get all product from the most purchased category
        ArrayList<Product> similarProducts = new ArrayList<>();
        for (Product p : productService.products) {
            if (p.getCategoryId().equals(mostPurchasedCategory)) {
                similarProducts.add(p);
            }
        }

        return similarProducts; // Return the list of similar products
    }
}
