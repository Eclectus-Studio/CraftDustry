package org.minetrio1256.craftdustry.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.minetrio1256.craftdustry.Craftdustry;

public class ModItemTags {

    private static TagKey<Item> createTag(final String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Craftdustry.MOD_ID, name));
    }
}
