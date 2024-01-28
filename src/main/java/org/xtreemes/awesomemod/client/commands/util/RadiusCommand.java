package org.xtreemes.awesomemod.client.commands.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundEvents;
import org.joml.Vector3f;
import org.xtreemes.awesomemod.client.interfaces.ClientCommand;
import org.xtreemes.awesomemod.client.util.SendFeedback;

public class RadiusCommand implements ClientCommand {

    public static float radius = 0f;
    private static DustParticleEffect particle;
    private static double locX;
    private static double locY;
    private static double locZ;

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        ClientPlayerEntity player = getPlayer();
        particle = new DustParticleEffect(new Vector3f(211f/255f, 1, 148f/255f), 1f);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(radius > 0) {
                ClientWorld world = player.clientWorld;
                int density = (int) (90 / radius);
                for (int theta = 0; theta < 360; theta += density) {
                    for (int phi = 0; phi < 180; phi += density) {
                        double x = locX + radius * Math.sin(Math.toRadians(phi)) * Math.cos(Math.toRadians(theta));
                        double y = locY + radius * Math.sin(Math.toRadians(phi)) * Math.sin(Math.toRadians(theta));
                        double z = locZ + radius * Math.cos(Math.toRadians(phi));

                        world.addParticle(particle, x, y, z, 0, 0, 0);
                    }
                }
            }

        });
        dispatcher.register(ClientCommandManager.literal("radius")
                .then(ClientCommandManager.argument("size", FloatArgumentType.floatArg())
                        .executes(context -> {
                            float size = FloatArgumentType.getFloat(context, "size");
                            if(size<0 || size > 15){
                                SendFeedback.sendMessage("Please use a value within 0-15", player);
                                player.playSound(SoundEvents.ENTITY_SHULKER_HURT_CLOSED,1.0f,1.8f);
                                return 1;
                            }
                            locX = player.getX();
                            locY = player.getY();
                            locZ = player.getZ();
                            radius = size;
                            SendFeedback.sendMessage("Set radius to " + size, player);
                            particle = new DustParticleEffect(new Vector3f(211f/255f, 1, 148f/255f), radius/5);
                            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.8f);

                            return 1;
                        })
                ).executes(context -> {
                    SendFeedback.sendMessage("Reset radius", player);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.8f);
                    radius = 0f;
                    return 1;
                }));
    }
}
