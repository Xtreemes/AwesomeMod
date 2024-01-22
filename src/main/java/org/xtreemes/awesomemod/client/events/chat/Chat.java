package org.xtreemes.awesomemod.client.events.chat;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundEvents;
import org.xtreemes.awesomemod.client.util.SendFeedback;

public class Chat implements ClientSendMessageEvents.ModifyChat {
    private static boolean toggled = false;
    public static void toggleStatus(ClientPlayerEntity player){
        if(toggled){
            SendFeedback.sendMessage("Prefix disabled!", player);
            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.2f);
        } else {
            SendFeedback.sendMessage("Prefix enabled!", player);
            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.8f);
        }
        toggled = !toggled;
    }

    @Override
    public String modifySendChatMessage(String message) {
        if(toggled){message = "@"+message;}
        return message;
    }
}
