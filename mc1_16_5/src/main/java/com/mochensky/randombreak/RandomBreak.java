package com.mochensky.randombreak;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.registry.Registry;

public class RandomBreak implements ModInitializer {

    private static final Item[] ITEMS = Registry.ITEM.stream()
            .filter(item -> item != null && item != Items.AIR)
            .toArray(Item[]::new);

    @Override
    public void onInitialize() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (id.toString().startsWith("minecraft:blocks/")) {
                LootPool.Builder poolBuilder = LootPool.builder();
                for (Item item : ITEMS) {
                    poolBuilder.with(ItemEntry.builder(item).weight(1));
                }
                LootTable newTable = LootTable.builder()
                        .pool(poolBuilder)
                        .build();
                setter.set(newTable);
            }
        });
    }
}