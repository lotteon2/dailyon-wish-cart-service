package com.dailyon.wishcartservice.cart.repository;

import com.dailyon.wishcartservice.cart.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, Long>, CartCustomRepository {

}
