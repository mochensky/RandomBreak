package com.mochensky.randombreak;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;

public class RandomBreak implements ModInitializer {

    private static final Item[] ITEMS = Registries.ITEM.stream()
            .filter(item -> item != null && !item.equals(Items.AIR))
            .toArray(Item[]::new);

    @Override
    public void onInitialize() {
        LootTableEvents.REPLACE.register((id, original, source) -> {
            if (id.getValue().getPath().startsWith("blocks/")) {
                LootPool.Builder poolBuilder = LootPool.builder();

                for (Item item : ITEMS) {
                    poolBuilder.with(ItemEntry.builder(item).weight(1));
                }

                return LootTable.builder()
                        .pool(poolBuilder.build())
                        .build();
            }
            return null;
        });
    }
}