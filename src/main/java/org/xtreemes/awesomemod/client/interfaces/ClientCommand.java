package org.xtreemes.awesomemod.client.interfaces;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public interface ClientCommand {
    void register(CommandDispatcher<FabricClientCommandSource> dispatcher);
    default ClientPlayerEntity getPlayer(){
        return MinecraftClient.getInstance().player;
    }
}
