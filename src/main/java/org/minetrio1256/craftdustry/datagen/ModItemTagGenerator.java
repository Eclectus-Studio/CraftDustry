package org.minetrio1256.craftdustry.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.Craftdustry;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> completableFuture,
                               final CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable final ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, Craftdustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.Provider provider) {
        //tag(Modtags.Items.TRANSFORMABLE_ITEMS)
               // .add(ModItems.AZURITE.get())
                //.add(ModItems.RAW_AZURITE.get());

    }
}
