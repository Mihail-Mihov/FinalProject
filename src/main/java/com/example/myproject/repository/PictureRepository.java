package com.example.myproject.repository;

import com.example.myproject.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

  //  void deleteAllByPublicId(String publicId);

    @Query("SELECT p.imageUrl FROM  PictureEntity p")
    List<String> findAllUrls();
}
