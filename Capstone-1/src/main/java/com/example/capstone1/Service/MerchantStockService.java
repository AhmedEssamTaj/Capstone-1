package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@AllArgsConstructor
@Service
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;

    // method to get all merchantStock
    public ArrayList <MerchantStock> getMerchantStock () {
        return merchantStocks;
    }

    // method to add a new MerchantStock
    public int addMerchantStock (MerchantStock merchantStock){
        boolean productExists = false;
        boolean merchantExists = false;
        // check if product id exist
        for (Product p : productService.products){
            if(p.getProductId().equals(merchantStock.getProductId())){
                productExists = true;
            }
        }

        // check if merchant exist
        for (Merchant m : merchantService.merchants){
            if(m.getMerchantId().equals(merchantStock.getMerchantId())){
                merchantExists = true;
            }
        }
if (!productExists){
    return -1;
}else if (!merchantExists){
    return -2;
}else {
    merchantStocks.add(merchantStock);
    return 1;
}

    }

    // method to update a MerchantStock if it exist
    public int updateMerchantStock (String id, MerchantStock merchantStock){

        boolean productExists = false;
        boolean merchantExists = false;
        boolean merchantStockExists = checkIfMerchantStockExists(id);
        if (! merchantStockExists) { // if merchant stock does not exist return 0
            return 0;
        }

        for (Product p : productService.products){
            if(p.getProductId().equals(merchantStock.getProductId())){
                productExists = true;
            }
        }
        for (Merchant m : merchantService.merchants){
            if(m.getMerchantId().equals(merchantStock.getMerchantId())){
                merchantExists = true;
            }
        }

        if (!productExists){
            return -1;
        }
        if (!merchantExists){
            return -2;
        }
        for (MerchantStock m : merchantStocks){
            if (m.getMerchantStockId().equals(id)){
                merchantStocks.set(merchantStocks.indexOf(m),merchantStock);
                return 1;
            }
        }

        return 5 ;
    }

    // method to delete a MerchantStock if exist..
    public boolean deleteMerchantStock (String id ){
        for (MerchantStock m : merchantStocks ){
            if (m.getMerchantStockId().equals(id)){
                merchantStocks.remove(merchantStocks.indexOf(m));
                return true;
            }
        }

        return false;
    }

    // method to check if this merchant stock exist or not
    public boolean checkIfMerchantStockExists (String id){
        for (MerchantStock m : merchantStocks){
            if (m.getMerchantStockId().equals(id)){
                return true;
            }
        }
        return false;
    }

    // method to add more stocks
    public int addMoreStocks (String merchantId, String productId, int stock){
        boolean merchantExists = merchantExists(merchantId);
        boolean merchantStockExist = false;
        boolean productExists = false;
        if (!merchantExists){
            return 0; // 0 --> the merchant does not exist
        }
        for (MerchantStock m : merchantStocks){
            if (m.getMerchantId().equals(merchantId)){
                merchantStockExist = true; // change it to true becuse this merchant has stock
                if (m.getProductId().equals(productId)){
                    productExists = true;
                }
            }
        }
        if (!merchantStockExist){
            return -1; // -1  --> no stock for this merchant
        }
        if (!productExists){
            return -2; // -2 --> this merchant does not have  stock for this product
        }
        for (MerchantStock m : merchantStocks){
            if (m.getProductId().equals(productId) && m.getMerchantId().equals(merchantId) ){
                merchantStocks.get(merchantStocks.indexOf(m)).setStock(stock);
                return 1; // 1 --> stock is updated successfully
            }
        }
        return 5;

    }

    // method to check if merchant exist by id
    public boolean merchantExists (String merchantId){
        for (Merchant m : merchantService.merchants){
            if (m.getMerchantId().equals(merchantId)){
                return true;
            }
        }
        return false;
    }

    // ========================= EXTRA METHOD 4 =========================\\
    // discount all the products under this merchant
    public int discountProductsByMerch (String merchantId,int percentage){
        if (percentage <=0 || percentage > 100){
            return-1;// percentage must be between 1 and 100
        }
        boolean merchantExists = merchantExists(merchantId);
        if (!merchantExists){
            return 0; // merchant does not have any product in stock
        }
        for (MerchantStock m : merchantStocks){
            if (m.getMerchantId().equals(merchantId)){
                for (Product p : productService.products){
                    if (p.getProductId().equals(m.getProductId())){
                        double discountedPrice = productService.products.get(productService.products.indexOf(p)).getProductPrice()-(productService.products.get(productService.products.indexOf(p)).getProductPrice() * ((double) percentage /100));
                        productService.products.get(productService.products.indexOf(p)).setProductPrice(discountedPrice);
                        break;
                    }
                }
            }

        }
        return 1;
    }

}
