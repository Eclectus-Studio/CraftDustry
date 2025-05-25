package org.minetrio1256.craftdustry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Craftdustry;
import org.minetrio1256.craftdustry.item.custom.*;

public class modItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Craftdustry.MOD_ID);

    public static final RegistryObject<Item> COAL = ITEMS.register("coal",
            () -> new Coal(new Item.Properties()));

    public static final RegistryObject<Item> WOOD = ITEMS.register("wood",
            () -> new Wood(new Item.Properties()));

    public static final RegistryObject<Item> SOLID_FUEL = ITEMS.register("solid_fuel",
            () -> new SolidFuel(new Item.Properties()));

    public static final RegistryObject<Item> ROCKET_FUEL = ITEMS.register("rocket_fuel",
            () -> new RocketFuel(new Item.Properties()));

    public static final RegistryObject<Item> NUCLEAR_FUEL = ITEMS.register("nuclear_fuel",
            () -> new NuclearFuel(new Item.Properties()));

    public static final RegistryObject<Item> ADVANCE_CIRCUIT = ITEMS.register("advance_circuit",
            () -> new AdvanceCircuit(new Item.Properties()));

    public static final RegistryObject<Item> BARREL = ITEMS.register("barrel",
            () -> new Barrel(new Item.Properties()));

    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery",
            () -> new Battery(new Item.Properties()));

    public static final RegistryObject<Item> CARBON = ITEMS.register("carbon",
            () -> new Carbon(new Item.Properties()));

    public static final RegistryObject<Item> COLD_FLUOROKETONE_BARREL = ITEMS.register("cold_fluoroketone_barrel",
            () -> new ColdFluoroketoneBarrel(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate",
            () -> new CopperPlate(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new CopperWire(new Item.Properties()));

    public static final RegistryObject<Item> CRUDE_OIL_BARREL = ITEMS.register("crude_oil_barrel",
            () -> new CrudeOilBarrel(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_ENGINE_UNIT = ITEMS.register("electric_engine_unit",
            () -> new ElectricEngineUnit(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRONIC_CIRCUIT = ITEMS.register("electronic_circuit",
            () -> new ElectronicCircuit(new Item.Properties()));

    public static final RegistryObject<Item> ENGINE_UNIT = ITEMS.register("engine_unit",
            () -> new EngineUnit(new Item.Properties()));

    public static final RegistryObject<Item> FLYING_ROBOT_FRAME = ITEMS.register("flying_robot_frame",
            () -> new FlyingRobotFrame(new Item.Properties()));

    public static final RegistryObject<Item> GREEN_WIRE = ITEMS.register("green_wire",
            () -> new GreenWire(new Item.Properties()));

    public static final RegistryObject<Item> HEAVY_OIL_BARREL = ITEMS.register("heavy_oil_barrel",
            () -> new HeavyOilBarrel(new Item.Properties()));

    public static final RegistryObject<Item> HOT_FLUOROKETONE_BARREL = ITEMS.register("hot_fluoroketone_barrel",
            () -> new HotFluoroketoneBarrel(new Item.Properties()));

    public static final RegistryObject<Item> ICE = ITEMS.register("ice",
            () -> new Ice(new Item.Properties()));

    public static final RegistryObject<Item> IRON_BAR = ITEMS.register("iron_bar",
            () -> new IronBar(new Item.Properties()));

    public static final RegistryObject<Item> IRON_GEAR = ITEMS.register("iron_gear",
            () -> new IronGear(new Item.Properties()));

    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new IronPlate(new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_OIL_BARREL = ITEMS.register("light_oil_barrel",
            () -> new AdvanceCircuit(new Item.Properties()));

    public static final RegistryObject<Item> LOW_DENSITY_STRUCTURE = ITEMS.register("low_density_structure",
            () -> new LowDensityStructure(new Item.Properties()));

    public static final RegistryObject<Item> LUBRICANT_BARREL = ITEMS.register("lubricant_barrel",
            () -> new LubricantBarrel(new Item.Properties()));

    public static final RegistryObject<Item> PETROLEUM_BARREL = ITEMS.register("petroleum_barrel",
            () -> new PetroleumBarrel(new Item.Properties()));

    public static final RegistryObject<Item> PLASTIC = ITEMS.register("plastic",
            () -> new Plastic(new Item.Properties()));

    public static final RegistryObject<Item> PROCESSING_UNIT = ITEMS.register("processing_unit",
            () -> new ProcessingUnit(new Item.Properties()));

    public static final RegistryObject<Item> RED_WIRE = ITEMS.register("red_wire",
            () -> new RedWire(new Item.Properties()));

    public static final RegistryObject<Item> REPAIR_PACK = ITEMS.register("repair_pack",
            () -> new RepairPack(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate",
            () -> new SteelPlate(new Item.Properties()));

    public static final RegistryObject<Item> SULPHUR = ITEMS.register("sulphur",
            () -> new Sulphur(new Item.Properties()));

    public static final RegistryObject<Item> SULPHURIC_ACID_BARREL = ITEMS.register("sulphuric_acid_barrel",
            () -> new SulphuricAcidBarrel(new Item.Properties()));

    public static final RegistryObject<Item> URANIUM_B_238 = ITEMS.register("uranium_b_238",
            () -> new UraniumB238(new Item.Properties()));

    public static final RegistryObject<Item> URANIUM_B_235 = ITEMS.register("uranium_b_235",
            () -> new UraniumB235(new Item.Properties()));

    public static final RegistryObject<Item> WATER_BARREL = ITEMS.register("water_barrel",
            () -> new WaterBarrel(new Item.Properties()));
    public static void register(final IEventBus eventBus) {
        modItems.ITEMS.register(eventBus);
    }
}
