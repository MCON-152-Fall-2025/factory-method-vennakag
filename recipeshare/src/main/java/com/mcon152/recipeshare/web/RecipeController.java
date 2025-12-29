package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    private final List<Recipe> recipes = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();
    RecipeController() {}

    /**
     * Adds a new recipe to the list.
     *
     * @param recipe the recipe to add
     * @return the added recipe with its assigned ID
     */
    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        if (recipe == null){
            logger.warn("Recipe is null");
        }
        recipe.setId(counter.incrementAndGet());
        recipes.add(recipe);
        logger.info("Adding recipe {}", recipe);
        return recipe;
    }

    /**
     * Retrieves all recipes.
     *
     * @return a list of all recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes() {
        logger.info("Successfully fetched all recipes");
        return recipes;
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id the ID of the recipe to retrieve
     * @return the recipe with the specified ID, or null if not found
     */
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        if (id < 0 || id >= recipes.size()) {
            logger.warn("Recipe with id {} not found", id);
        }
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                logger.debug("Fetching recipe with id {}", id);
                return recipe;
            }
        }
        logger.info("Recipe with id {} fetched", id);
        return null;
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param id the ID of the recipe to delete
     * @return true if the recipe was deleted, false if not found
     */
    @DeleteMapping("/{id}")
    public boolean deleteRecipe(@PathVariable long id) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                recipes.remove(i);
                logger.debug("Deleting recipe {}", recipes.get(i));
                logger.info("Recipe {} successfully deleted", recipes.get(i));
                return true;
            }
        }
        logger.warn("Recipe with id {} not successfully deleted", id);
        return false;
    }
    /**
     * Updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param updatedRecipe the updated recipe data
     * @return the updated recipe, or null if not found
     */
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable long id, @RequestBody Recipe updatedRecipe) {
        Recipe toReturn = null;
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                updatedRecipe.setId(id);
                recipes.set(i,  updatedRecipe);
                toReturn = recipes.get(i);
                logger.debug("Updating recipe {}", updatedRecipe);
            }
        }
        logger.info("Recipe {} successfully updates", updatedRecipe);
        return toReturn;
    }

    /**
     * Partially updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param partialRecipe the partial recipe data to update
     * @return the updated recipe, or null if not found
     */
    @PatchMapping("/{id}")
    public Recipe patchRecipe(@PathVariable long id, @RequestBody Recipe partialRecipe) {
        for (int i = 0; i < recipes.size(); i++) {
            Recipe existing = recipes.get(i);
            if (existing.getId() == id) {
                if (partialRecipe.getTitle() != null)
                    existing.setTitle(partialRecipe.getTitle());
                if (partialRecipe.getDescription() != null)
                    existing.setDescription(partialRecipe.getDescription());
                logger.debug("Patching recipe {} of id {}", partialRecipe, id);
                logger.info("Recipe {} of id {} successfully updated", partialRecipe, id);
                return existing;
            }
        }
        logger.warn("Patching recipe {} of id {} not successful", partialRecipe, id);
        return null;
    }
}