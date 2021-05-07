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

package co.uk.isxander.evergreenhud.compatability.forge10809.providers;

import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UEntity;
import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class UEntityProvider {

    public static UEntity getEntity(Entity entity) {
        if (entity == null)
            return null;

        return new UEntity() {
            @Override
            public double getX() {
                return entity.posX;
            }

            @Override
            public double getY() {
                return entity.posY;
            }

            @Override
            public double getZ() {
                return entity.posZ;
            }

            @Override
            public void setX(double x) {
                entity.posX = x;
            }

            @Override
            public void setY(double y) {
                entity.posY = y;
            }

            @Override
            public void setZ(double z) {
                entity.posZ = z;
            }

            @Override
            public double getPrevX() {
                return entity.prevPosX;
            }

            @Override
            public double getPrevY() {
                return entity.prevPosY;
            }

            @Override
            public double getPrevZ() {
                return entity.prevPosZ;
            }

            @Override
            public int id() {
                return entity.getEntityId();
            }
        };
    }

    public static UPlayer getPlayer(EntityPlayer player) {
        if (player == null) {
            return null;
        }

        return new UPlayer() {
            @Override
            public double getX() {
                return player.posX;
            }

            @Override
            public double getY() {
                return player.posY;
            }

            @Override
            public double getZ() {
                return player.posZ;
            }

            @Override
            public void setX(double x) {
                player.posX = x;
            }

            @Override
            public void setY(double y) {
                player.posY = y;
            }

            @Override
            public void setZ(double z) {
                player.posZ = z;
            }

            @Override
            public double getPrevX() {
                return player.prevPosX;
            }

            @Override
            public double getPrevY() {
                return player.prevPosY;
            }

            @Override
            public double getPrevZ() {
                return player.prevPosZ;
            }

            @Override
            public float getYaw() {
                return player.rotationYaw;
            }

            @Override
            public float getPitch() {
                return player.rotationPitch;
            }

            @Override
            public void setYaw(float yaw) {
                player.rotationYaw = yaw;
            }

            @Override
            public void setPitch(float pitch) {
                player.rotationPitch = pitch;
            }

            @Override
            public double calculateReachDistFromEntity(UEntity entity) {
                // TODO: 03/05/2021 find modern code for calculating reach dist
                return -1;
            }

            @Override
            public int id() {
                return player.getEntityId();
            }
        };
    }

}
