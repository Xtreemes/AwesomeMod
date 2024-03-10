package org.xtreemes.awesomemod.client.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.xtreemes.awesomemod.client.ParamsHandler;
import org.xtreemes.awesomemod.client.keybinds.Keybinds;

import java.util.ArrayList;

@Mixin(net.minecraft.client.MinecraftClient.class)
public class OpenChatMixin {

    @Unique
    private final String[] prefixes = {"String","List","Number","Styled Text","Location","Vector",
            "Variable","Item","Any Value","Sound","Dictionary","Potion Effect","Particle","Block"};

    @Shadow @Nullable public ClientPlayerEntity player;

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
    @Inject(method = "setScreen", at = @At("HEAD"))
    private void setScreen(Screen screen, CallbackInfo ci){
        if(screen instanceof HandledScreen<?> handled_screen){
            ScreenHandler screen_handler = handled_screen.getScreenHandler();
            if(screen_handler instanceof GenericContainerScreenHandler) {
                if(screen_handler.getType() == ScreenHandlerType.GENERIC_9X3) {
                    PlayerInventory inv;
                    if((inv = player.getInventory()) != null){
                        ItemStack item = inv.getStack(17);
                        if(!item.isEmpty()){
                            NbtCompound nbt = item.getNbt();
                            if(nbt != null){
                                //nbt.contains("hypercube:item_instance") &&
                                if(item.isOf(Items.WRITTEN_BOOK)){
                                    NbtElement display = nbt.getCompound(ItemStack.DISPLAY_KEY).get(ItemStack.LORE_KEY);
                                    if(display instanceof NbtList nbt_list){

                                        ParamsHandler.clearParams();
                                        boolean params = false;
                                        ArrayList<String> param = new ArrayList<>();
                                        int iter = 0;
                                        int nbt_list_size = nbt_list.size();

                                        for (NbtElement nbt_element : nbt_list) {
                                            iter++;
                                            String nbt_string = nbt_element.asString();
                                            boolean skip = false;

                                            if(!(nbt_string.isEmpty() || nbt_string.equals("\"\""))) {
                                                JsonObject json = JsonParser.parseString(nbt_string).getAsJsonObject();
                                                JsonArray json_array = json.get("extra").getAsJsonArray();

                                                each_part:
                                                for(JsonElement element : json_array){
                                                    JsonObject json_object = element.getAsJsonObject();
                                                    String text = json_object.get("text").getAsString();
                                                    if(text.equals("Chest Parameters:") && !params){
                                                        params = true;
                                                        skip = true;
                                                    } else {
                                                        if(params){
                                                            for(String pre : prefixes){
                                                                if(text.startsWith(pre)){
                                                                    ParamsHandler.setParam(param);
                                                                    param.clear();
                                                                    break each_part;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if(params && !skip) {
                                                    param.add(nbt_string);
                                                }
                                            } else if(params){
                                                ParamsHandler.setParam(param);
                                                params = false;
                                            }
                                            if(params && !skip){
                                                param.add("NEWLINE");
                                                if(iter == nbt_list_size){
                                                    ParamsHandler.setParam(param);
                                                }
                                            }
                                        }

                                    }
                                } else {
                                    ParamsHandler.clearParams();
                                }
                            }
                        }
                    }
                } else {
                    ParamsHandler.clearParams();
                }
            }
        } else {
            ParamsHandler.clearParams();
        }
    }
}