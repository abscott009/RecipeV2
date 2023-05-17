package com.github.RecipeV2.Repository;

import com.github.RecipeV2.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findByUsername(String username);
}
