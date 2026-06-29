package dev.miami.blastcheck.mixin;

import dev.miami.blastcheck.util.ChatPrinter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Unique private boolean pendingKitCheck = false;

    @Inject(method = "sendCommand", at = @At("RETURN"))
    private void inject$sendCommand(String command, CallbackInfo ci) {
        if (Minecraft.getInstance().player == null) return;
        if (command.length() == 2 && command.charAt(0) == 'k' && command.charAt(1) >= '1' && command.charAt(1) <= '9') {
            pendingKitCheck = true;
        }
    }

    @Inject(method = "handleSetPlayerInventory", at = @At("RETURN"))
    private void inject$handleSetPlayerInventory(ClientboundSetPlayerInventoryPacket packet, CallbackInfo ci) {
        if (!pendingKitCheck || Minecraft.getInstance().player == null) return;
        pendingKitCheck = false;
        ChatPrinter.sendMessage();
    }
}