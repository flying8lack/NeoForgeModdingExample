package com.flying__8lack.client.event;

import com.flying__8lack.ModItem;
import com.flying__8lack.client.KeyResponse;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;
import java.util.Optional;

import static com.flying__8lack.advancedmovementmod.MODID;
import static com.flying__8lack.client.ModClientMod.FLYING_MAPPING_BTN;
import static com.flying__8lack.client.ModClientMod.JUMPKIT_ENABLED_BTN;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvent {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while(FLYING_MAPPING_BTN.get().consumeClick()) {
            KeyResponse.toggleFLYING_BTN();
        }
        while(JUMPKIT_ENABLED_BTN.get().consumeClick()){
            KeyResponse.toggleJumpkitEnabled();
        }
    }

    @SubscribeEvent
    public static void TraderMod(VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.MASON){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.IRON_INGOT, randomSource.nextInt(6,9)),
                    new ItemStack(ModItem.TITANIUM_INGOT.get(), 3), 4, 3, 0.05f)
            );
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, randomSource.nextInt(3,6)),
                    Optional.of(new ItemCost(Items.GOLD_BLOCK, randomSource.nextInt(2,3))),
                    new ItemStack(ModItem.TITANIUM_INGOT.get(), 3), 4, 3, 0.05f)
            );

        }    }
}
