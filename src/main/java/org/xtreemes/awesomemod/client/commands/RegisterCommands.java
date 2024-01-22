package org.xtreemes.awesomemod.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.xtreemes.awesomemod.client.commands.util.RadiusCommand;

public class RegisterCommands {
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher){
        new RadiusCommand().register(dispatcher);
    }
}
