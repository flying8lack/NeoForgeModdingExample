package com.flying__8lack;

import com.flying__8lack.entity.ModEntity;
import com.flying__8lack.items.ItemPureCoal;
import com.flying__8lack.items.ItemUnstableCreative;
import com.flying__8lack.items.armor.ArmorTitaniumJumpkit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModItem {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    //block items

    public static final DeferredItem<BlockItem> PAIN_SAPLING_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "pain_sapling_block", ModBlock.PAIN_SAPLING);

    public static final DeferredItem<BlockItem> COAL_POWER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "coal_power_block", ModBlock.COAL_POWER_BLOCK);

    public static final DeferredItem<BlockItem> THERMITE_DIGGER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "thermite_digger_block", ModBlock.THERMITE_DIGGER_BLOCK);

    public static final DeferredItem<BlockItem> TITANIUM_ORE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "titanium_ore", ModBlock.TITANIUM_ORE);

    public static final DeferredItem<BlockItem> WARDEN_CALLER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "warden_caller_block", ModBlock.WARDEN_CALLER_BLOCK);
    public static final DeferredItem<BlockItem> BROKEN_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "broken_block", ModBlock.BROKEN_BLOCK);

    //items
    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.registerSimpleItem("titanium_ingot",
            new Item.Properties().stacksTo(64));

    public static final DeferredItem<Item> THRUSTER = ITEMS.registerSimpleItem("thruster",
            new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> PURE_COAL = ITEMS.register("pure_coal",
            () -> new ItemPureCoal(new Item.Properties().stacksTo(64)));

    public static final DeferredItem<Item> UNSTABLE_CREATIVE_ITEM = ITEMS.register("unstable_item",
            () -> new ItemUnstableCreative(new Item.Properties().stacksTo(1)));

    //spawn eggs
    public static final DeferredItem<Item> GRASS_WALKER_EGG = ITEMS.register("grass_walker_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntity.GRASS_WALKER_MONSTER_ENTITY,
                    0x00001F, 0x007274, new Item.Properties()));

    //armor
    public static final DeferredItem<Item> TITANIUM_JUMPKIT = ITEMS.register("titanium_jumpkit",
            () -> new ArmorTitaniumJumpkit(new Item.Properties().durability(670)));

    public static void register(IEventBus event){
        ITEMS.register(event);
    }





}
