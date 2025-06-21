package com.mochensky.randomitems.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import java.util.Random;

public class RandomitemsClient implements ClientModInitializer {

    private static final Random RANDOM = new Random();

    @Override
    public void onInitializeClient() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source) -> {
            if (id.getValue().getPath().startsWith("blocks/")) {
                Item randomItem = getRandomItem();

                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(randomItem));

                tableBuilder.pools(java.util.Collections.singletonList(poolBuilder.build()));
            }
        });
    }

    private static final Item[] ITEMS = Registries.ITEM.stream()
            .filter(item -> item != null && !item.equals(Items.AIR))
            .toArray(Item[]::new);

    private Item getRandomItem() {
        return ITEMS.length > 0 ? ITEMS[RANDOM.nextInt(ITEMS.length)] : Items.STONE;
    }
}