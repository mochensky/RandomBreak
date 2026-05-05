package com.mochensky.randombreak;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class RandomBreak implements ModInitializer {

    private static final Item[] ITEMS = BuiltInRegistries.ITEM.stream()
            .filter(item -> item != Items.AIR)
            .toArray(Item[]::new);

    @Override
    public void onInitialize() {

        LootTableEvents.REPLACE.register((key, original, source, registries) -> {

            if (source != LootTableSource.VANILLA) {
                return null;
            }

            String id = key.toString();

            if (!id.contains("blocks/")) {
                return null;
            }

            LootPool.Builder poolBuilder = LootPool.lootPool();

            for (Item item : ITEMS) {
                poolBuilder.add(LootItem.lootTableItem(item));
            }

            return LootTable.lootTable()
                    .withPool(poolBuilder)
                    .build();
        });
    }
}