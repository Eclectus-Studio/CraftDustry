package org.minetrio1256.craftdustry.event;

import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.minetrio1256.craftdustry.data.advancementcape.AdvancementsCapes;
import org.minetrio1256.craftdustry.data.player.UnlockedCape;

@Mod.EventBusSubscriber
public class AchievementUnlock {

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        Advancement advancement = event.getAdvancement().value();
        ResourceLocation advID = event.getAdvancement().id();

        AdvancementsCapes.INSTANCE.getAll().values().forEach(ac -> {
            if (ac.advancement.equals(advID)) {
                UnlockedCape.unlockCape(player.getUUID(), ac.cape);
            }
        });
    }
}
