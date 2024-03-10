package org.xtreemes.awesomemod.client.keybinds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.glfw.GLFW;


public class Keybinds implements ClientModInitializer {
    public static boolean PREFIX_ACTIVE = false;

    @Override
    public void onInitializeClient() {
        KeyBinding togglePrefix = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.awesomemod.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "category.awesomemod"));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(togglePrefix.wasPressed()){
                ClientPlayerEntity p = client.player;
                if(PREFIX_ACTIVE){
                    p.sendMessage(Text.literal("»").setStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(0x52c91a)))
                            .append(Text.literal(" Prefix disabled!").setStyle(Style.EMPTY.withBold(false).withColor(TextColor.fromRgb(0xecffe3))))
                    );
                    p.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.3f);
                } else {
                    p.sendMessage(Text.literal("»").setStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(0x52c91a)))
                            .append(Text.literal(" Prefix enabled!").setStyle(Style.EMPTY.withBold(false).withColor(TextColor.fromRgb(0xecffe3))))
                    );
                    p.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.7f);
                }
                PREFIX_ACTIVE = !PREFIX_ACTIVE;
            }
        });
    }
}
