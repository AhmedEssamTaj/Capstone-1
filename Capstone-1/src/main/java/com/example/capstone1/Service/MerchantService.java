package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@AllArgsConstructor
@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    // method to get all merchant
    public ArrayList <Merchant> getMerchant () {
        return merchants;
    }

    // method to add a new Merchant
    public void addMerchant (Merchant merchant){
        merchants.add(merchant);
    }

    // method to update a Merchant if it exist
    public boolean updateMerchant (String id, Merchant merchant){

        for (Merchant m : merchants){
            if (m.getMerchantId().equals(id)){
                merchants.set(merchants.indexOf(m),merchant);
                return true;
            }
        }
        return false;
    }

    // method to delete a Merchant if exist..
    public boolean deleteMerchant (String id ){
        for (Merchant m : merchants ){
            if (m.getMerchantId().equals(id)){
                merchants.remove(merchants.indexOf(m));
                return true;
            }
        }

        return false;
    }


}
