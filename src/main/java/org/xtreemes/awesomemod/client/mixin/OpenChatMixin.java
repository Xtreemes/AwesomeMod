package org.xtreemes.awesomemod.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.xtreemes.awesomemod.client.keybinds.Keybinds;

@Mixin(net.minecraft.client.MinecraftClient.class)
public class OpenChatMixin {

    @Inject(at = @At("TAIL"), method = "openChatScreen")
    private void openChat(String text, CallbackInfo info){
        if(Keybinds.PREFIX_ACTIVE){
            if(text.isEmpty()) {
                MinecraftClient mc = MinecraftClient.getInstance();
                ChatScreen screen = new ChatScreen("@");
                mc.setScreen(screen);
            }
        }
    }
}
