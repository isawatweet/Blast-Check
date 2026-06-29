package dev.miami.blastcheck.util;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class EnchantmentChecker implements IMinecraft {

    public static @Nullable String getKitType() {
        boolean leggingsBP = checkProtType(mc.player.getItemBySlot(EquipmentSlot.LEGS));
        boolean bootsBP = checkProtType(mc.player.getItemBySlot(EquipmentSlot.FEET));

        if (leggingsBP && bootsBP) return "DBP";

        // noinspection ConstantValue
        if (leggingsBP && !bootsBP) return "SBP";
        return null;
    }

    public static boolean checkProtType(@NonNull ItemStack stack) {
        if (stack.isEmpty()) return false;

        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        Registry<Enchantment> registry = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        return registry.get(Enchantments.BLAST_PROTECTION)
                .map(holder -> enchantments.getLevel(holder) > 0)
                .orElse(false);
    }
}