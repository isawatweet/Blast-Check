package dev.miami.blastcheck.mixin;

import dev.miami.blastcheck.util.ChatPrinter;
import dev.miami.blastcheck.util.IMinecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin implements IMinecraft {
    @Unique private boolean pendingKitCheck = false;

    @Inject(method = "sendCommand", at = @At("RETURN"))
    private void inject$sendCommand(@NonNull String command, CallbackInfo ci) {
        if (command.length() == 2 && command.charAt(0) == 'k' && command.charAt(1) >= '1' && command.charAt(1) <= '9') {
            pendingKitCheck = true;
        }
    }

    @Inject(method = "handleSetPlayerInventory", at = @At("RETURN"))
    private void inject$handleSetPlayerInventory(ClientboundSetPlayerInventoryPacket packet, CallbackInfo ci) {
        if (!pendingKitCheck || mc.player == null) return;
        pendingKitCheck = false;
        ChatPrinter.sendMessage();
    }
}