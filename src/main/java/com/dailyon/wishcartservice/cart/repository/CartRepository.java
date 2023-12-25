package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.document.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>, CartCustomRepository {

}
