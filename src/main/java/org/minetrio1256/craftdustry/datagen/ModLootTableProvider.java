package org.minetrio1256.craftdustry.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.minetrio1256.craftdustry.datagen.loot.ModBlockLootTables;

public enum ModLootTableProvider {
    ;

    public static LootTableProvider create(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> future) {
        return new LootTableProvider(packOutput, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)), future);
    }
}
