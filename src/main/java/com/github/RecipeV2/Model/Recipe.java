package com.github.RecipeV2.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer minutesToMake;

    @Column(nullable = false)
    private Integer difficultyRating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Step> steps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Review> reviews;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;

    @Transient
    @JsonIgnore
    private URI locationURI;

    public String getAuthor() {
        return user.getUsername();
    }

    public void setDifficultyRating(int difficultyRating) {
        if (difficultyRating < 0 || difficultyRating > 10) {
            throw new IllegalStateException("Difficulty rating must be between 0 and 10.");
        }
        this.difficultyRating = difficultyRating;
    }

    public void validate() throws IllegalStateException {
        if (ingredients.size() == 0) {
            throw new IllegalStateException("You have to have at least one ingredient for your recipe!");
        } else if (steps.size() == 0) {
            throw new IllegalStateException("You have to include at least one step for your recipe!");
        }
    }

    public void generateLocationURI() {
        try {
            locationURI = new URI(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/recipes/")
                            .path(String.valueOf(id))
                            .toUriString());
        } catch (URISyntaxException e) {
            //Exception should stop here.
        }
    }
}
