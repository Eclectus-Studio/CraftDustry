package org.minetrio1256.craftdustry.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.Main;
import org.minetrio1256.craftdustry.block.modBlocks;
import org.minetrio1256.craftdustry.util.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.Provider provider) {
        tag(ModBlockTags.BELT_IGNORE)
                .add(modBlocks.IRON_CHEST.get())
                .add(modBlocks.WOODEN_CHEST.get())
                .add(Blocks.CHEST)
                .add(Blocks.TRAPPED_CHEST);
        tag(ModBlockTags.FAST_BELT_IGNORE)
                .add(modBlocks.IRON_CHEST.get())
                .add(modBlocks.WOODEN_CHEST.get())
                .add(Blocks.CHEST)
                .add(Blocks.TRAPPED_CHEST);
        tag(ModBlockTags.EXPRESS_BELT_IGNORE)
                .add(modBlocks.IRON_CHEST.get())
                .add(modBlocks.WOODEN_CHEST.get())
                .add(Blocks.CHEST)
                .add(Blocks.TRAPPED_CHEST);
    }
}
