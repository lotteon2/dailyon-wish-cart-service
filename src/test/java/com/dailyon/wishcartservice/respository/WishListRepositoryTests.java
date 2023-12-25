package com.dailyon.wishcartservice.respository;

import com.dailyon.wishcartservice.wishlist.document.WishList;
import com.dailyon.wishcartservice.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class WishListRepositoryTests {
    @Autowired
    WishListRepository wishListRepository;

    @BeforeEach
    void beforeEach() {
        wishListRepository.deleteAll();
    }

    @Test
    @DisplayName("찜 목록 페이지네이션 조회")
    void readPagesTest() {
        wishListRepository.saveAll(List.of(
                WishList.create(1L, 1L, 1L),
                WishList.create(1L, 1L, 2L),
                WishList.create(1L, 2L, 1L),
                WishList.create(1L, 2L, 2L)
        ));

        Page<WishList> wishLists =
                wishListRepository.readWishListPages(1L, Pageable.ofSize(5));

        Assertions.assertEquals(1, wishLists.getTotalPages());
        Assertions.assertEquals(4,wishLists.getTotalElements());
    }

    @Test
    @DisplayName("기존 찜 삭제")
    void toggleWishListTest1() {
        wishListRepository.save(WishList.create(1L, 1L, 1L));

        wishListRepository.toggleWishList(1L, 1L, 1L);

        Assertions.assertEquals(0, wishListRepository.findAll().size());
    }

    @Test
    @DisplayName("찜 생성")
    void toggleWishListTest2() {
        wishListRepository.toggleWishList(1L, 1L, 1L);

        Assertions.assertEquals(1, wishListRepository.findAll().size());
    }

    @Test
    @DisplayName("상품 페이지에서 찜 목록 조회")
    void readWishListFromProductTest() {
        // given
        wishListRepository.save(WishList.create(1L, 1L, 1L));
        wishListRepository.save(WishList.create(1L, 1L, 2L));
        wishListRepository.save(WishList.create(1L, 1L, 3L));

        // when
        List<WishList> wishLists = wishListRepository.findByMemberIdAndProductId(1L, 1L);

        // then
        Assertions.assertEquals(3, wishLists.size());
    }
}
