package org.minetrio1256.craftdustry.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Main;


public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(final PackOutput output, final ExistingFileHelper exFileHelper) {
        super(output, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.AZURITE_BLOCK);
    }

    private void blockWithItem(final RegistryObject<Block> blockRegistryObject) {
        this.simpleBlockWithItem(blockRegistryObject.get(), this.cubeAll(blockRegistryObject.get()));
    }
}
