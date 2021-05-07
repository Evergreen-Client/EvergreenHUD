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

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.fabric11605.providers.UEntityProvider;
import co.uk.isxander.evergreenhud.event.impl.AttackEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
    public void attack(Entity entity, CallbackInfo ci) {
        EvergreenHUD.EVENT_BUS.post(new AttackEntityEvent(UEntityProvider.getEntity(entity), UEntityProvider.getPlayer(((PlayerEntity) (Object) this))));
    }

}
