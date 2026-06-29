package dev.miami.blastcheck.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class ChatPrinter {
    public static void sendMessage() {
        String kitType = EnchantmentChecker.getKitType();
        if (kitType == null) return;

        String prefix = "BlastCheck » ";
        MutableComponent fullMessage = Component.empty();

        for (int i = 0; i < prefix.length(); i++) {
            float progress = (float) i / (prefix.length() - 1);
            int red = (int) (progress * 255);
            TextColor gradientColor = TextColor.fromRgb(red << 16);
            fullMessage.append(Component.literal(String.valueOf(prefix.charAt(i))).setStyle(Style.EMPTY.withColor(gradientColor).withBold(true)));
        }
        ChatFormatting kitColor = kitType.equals("DBP") ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
        fullMessage.append(Component.literal("The kit is: ").setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)));
        fullMessage.append(Component.literal(kitType).setStyle(Style.EMPTY.withColor(kitColor).withBold(true)));
        Minecraft.getInstance().gui.getChat().addServerSystemMessage(fullMessage);
    }
}
