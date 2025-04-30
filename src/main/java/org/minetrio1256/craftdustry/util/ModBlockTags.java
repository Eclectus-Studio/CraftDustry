package org.minetrio1256.craftdustry.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.minetrio1256.craftdustry.Craftdustry;

public class ModBlockTags {
    public static final TagKey<Block> BELT_IGNORE = ModBlockTags.createTag("belt_ignore");
    public static final TagKey<Block> FAST_BELT_IGNORE = ModBlockTags.createTag("fast_belt_ignore");
    public static final TagKey<Block> EXPRESS_BELT_IGNORE = ModBlockTags.createTag("express_belt_ignore");


    private static TagKey<Block> createTag(final String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Craftdustry.MOD_ID, name));
    }
}
