package com.example.myproject.repository;

import com.example.myproject.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

//todo teq sa za triene

//        @Query("select o from OfferEntity o where o.author.username = :author")
//        List<OfferEntity> getAllByAuthor(@Param("author") String author);
//
//        @Query("select o from OfferEntity o where UPPER(o.name) like :name")
//        List<OfferEntity> getAllByName(@Param("name") String name);

        @Query(value = "SELECT * FROM offers o WHERE o.author_id = ?1", nativeQuery = true)
        List<OfferEntity> getAllByAuthor(Long authorId);
        @Query(value = "SELECT * FROM offers o WHERE UPPER(o.name) like ?1", nativeQuery = true)
        List<OfferEntity> getAllByName(String name);

        @Query(value = "SELECT * FROM offers", nativeQuery = true)
        List<OfferEntity> findAllOffers();
}
