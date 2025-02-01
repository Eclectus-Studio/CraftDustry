package org.minetrio1256.craftdustry.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.minetrio1256.craftdustry.Main;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //List<ItemLike> AZURITE_SMELTABLES = List.of(ModItems.RAW_AZURITE.get(),
                //ModBlocks.AZURITE_ORE.get(), ModBlocks.DEEPSLATE_AZURITE_ORE.get(), ModBlocks.AZURITE_END_ORE.get(), ModBlocks.AZURITE_NETHER_ORE.get());

        //ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AZURITE_BLOCK.get())
                //.pattern("AAA")
                //.pattern("AAA")
                //.pattern("AAA")
                //.define('A', ModItems.AZURITE.get())
                //.unlockedBy("has_azurite", has(ModItems.AZURITE.get())).save(recipeOutput);

        //ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AZURITE.get(), 9)
                //.requires(ModBlocks.AZURITE_BLOCK.get())
                //.unlockedBy("has_azurite_block", has(ModBlocks.AZURITE_BLOCK.get())).save(recipeOutput);

        //oreSmelting(recipeOutput, AZURITE_SMELTABLES, RecipeCategory.MISC, ModItems.AZURITE.get(), 0.25f, 200, "azurite");
        //oreBlasting(recipeOutput, AZURITE_SMELTABLES, RecipeCategory.MISC, ModItems.AZURITE.get(), 0.25f, 100, "azurite");

    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Main.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
