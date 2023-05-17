package com.github.RecipeV2.Repository;

import com.github.RecipeV2.Model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<CustomUserDetails, Long> {

    CustomUserDetails findByUsername(String username);
}