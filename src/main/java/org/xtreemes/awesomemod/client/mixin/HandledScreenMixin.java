package org.xtreemes.awesomemod.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.xtreemes.awesomemod.client.ParamsHandler;

import java.util.ArrayList;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow protected abstract boolean isPointOverSlot(Slot slot, double pointX, double pointY);


    @Shadow public abstract ScreenHandler getScreenHandler();


    @Shadow protected int x;

    @Shadow protected int y;

    @Inject(method = "render", at = @At("RETURN"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        ScreenHandler screen_handler = this.getScreenHandler();

        for(int i = 0; i < screen_handler.slots.size(); i++){
            if(isPointOverSlot(screen_handler.getSlot(i), mouseX, mouseY)){
                ArrayList<String> param = ParamsHandler.getParam(i);
                if(!param.isEmpty()){
                    int y = this.y;
                    int index = param.size()-1;
                    for(; index >= 0; index--){
                        String s = param.get(index);
                        if(s.equals("NEWLINE")){
                            y -= 8;
                        } else {
                            Text text = Text.Serialization.fromJson(s);
                            context.drawTextWithShadow(client.textRenderer, text, this.x + 1, y, 0xFFFFFF);
                        }
                    }
                    //Text text = Text.Serialization.fromJsonTree(json_array);
                    //context.drawTextWithShadow(client.textRenderer, text, this.x + 1, this.y - 8, 0xFFFFFF);
                }
                break;
            }
        }
    }
}
