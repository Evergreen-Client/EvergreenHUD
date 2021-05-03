/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/EvergreenHUD
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.evergreenhud.compatability.fabric11605.mixin;

import co.uk.isxander.evergreenhud.compatability.fabric11605.callback.ModInitCallback;
import co.uk.isxander.evergreenhud.compatability.fabric11605.callback.ModPostInitCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraft {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;registerListener(Lnet/minecraft/resource/ResourceReloadListener;)V", shift = At.Shift.AFTER))
    public void modInit(RunArgs args, CallbackInfo ci) {
        ModInitCallback.EVENT.invoker().init();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void modPostInit(RunArgs args, CallbackInfo ci) {
        ModPostInitCallback.EVENT.invoker().postInit();
    }

}
