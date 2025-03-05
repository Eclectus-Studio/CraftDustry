package org.minetrio1256.craftdustry.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.minetrio1256.craftdustry.Main;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(final PackOutput output, final ExistingFileHelper existingFileHelper) {
        super(output, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //basicItem(ModItems.AZURITE.get());
        //basicItem(ModItems.RAW_AZURITE.get());

        //basicItem(ModItems.CHAINSAW.get());
        //basicItem(ModItems.ONION.get());
        //basicItem(ModItems.AURORA_ASHES.get());
    }
}
