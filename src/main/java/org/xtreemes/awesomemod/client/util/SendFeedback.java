package org.xtreemes.awesomemod.client.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class SendFeedback {
    public static void sendMessage(String string, ClientPlayerEntity player){
        player.sendMessage(
                Text.literal("» ").setStyle(Style.EMPTY.withColor(0x52c91a).withBold(true)).append(
                        Text.literal(string).setStyle(Style.EMPTY.withColor(0xe3fcd7).withBold(false))
                ));
    }
}
