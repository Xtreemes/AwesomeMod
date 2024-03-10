package org.xtreemes.awesomemod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.xtreemes.awesomemod.client.commands.RegisterCommands;



public class AwesomeModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> RegisterCommands.registerCommands(dispatcher)));

    }
}
