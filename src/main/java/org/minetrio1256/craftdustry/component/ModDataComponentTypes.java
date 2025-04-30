package org.minetrio1256.craftdustry.component;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Craftdustry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Craftdustry.MOD_ID);

    public static final RegistryObject<DataComponentType<BlockPos>> COORDINATES = ModDataComponentTypes.register("coordinates",
            builder -> builder.persistent(BlockPos.CODEC));


    private static <T>RegistryObject<DataComponentType<T>> register(final String name, final UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return ModDataComponentTypes.DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(final IEventBus eventBus) {
        ModDataComponentTypes.DATA_COMPONENT_TYPES.register(eventBus);
    }
}
