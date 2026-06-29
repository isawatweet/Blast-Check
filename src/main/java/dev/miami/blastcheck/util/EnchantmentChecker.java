package dev.miami.blastcheck.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class EnchantmentChecker {

    public static @Nullable String getKitType() {
        if (Minecraft.getInstance().player == null) return null;

        boolean leggingsBP = hasEnchantment(Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.LEGS), Enchantments.BLAST_PROTECTION);
        boolean bootsBP = hasEnchantment(Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.FEET), Enchantments.BLAST_PROTECTION);

        if (leggingsBP && bootsBP) return "DBP";

        // noinspection ConstantValue
        if (leggingsBP && !bootsBP) return "SBP";
        return null;
    }

    public static boolean hasEnchantment(@NonNull ItemStack stack, ResourceKey<Enchantment> enchantmentKey) {
        if (stack.isEmpty() || Minecraft.getInstance().level == null) return false;

        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        Registry<Enchantment> registry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        return registry.get(enchantmentKey)
                .map(holder -> enchantments.getLevel(holder) > 0)
                .orElse(false);
    }
}