package org.xtreemes.awesomemod.client.keybinds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.xtreemes.awesomemod.client.commands.RegisterCommands;
import org.xtreemes.awesomemod.client.events.chat.Chat;

public class Keybinds implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinding togglePrefix = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.awesomemod.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "category.awesomemod"));

        ClientSendMessageEvents.MODIFY_CHAT.register(new Chat());
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            RegisterCommands.registerCommands(dispatcher);
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(togglePrefix.wasPressed()){
                Chat.toggleStatus(client.player);
            }
        });
    }
}
