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

package co.uk.isxander.evergreenhud.compatability.fabric11605.providers;

import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UEntity;
import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class UEntityProvider {

    public static UEntity getEntity(Entity entity) {
        if (entity == null)
            return null;

        return new UEntity() {
            @Override
            public double getX() {
                return entity.getX();
            }

            @Override
            public double getY() {
                return entity.getY();
            }

            @Override
            public double getZ() {
                return entity.getZ();
            }

            @Override
            public void setX(double x) {
                entity.setPos(x, getY(), getZ());
            }

            @Override
            public void setY(double y) {
                entity.setPos(getX(), y, getZ());
            }

            @Override
            public void setZ(double z) {
                entity.setPos(getX(), getY(), z);
            }

            @Override
            public double getPrevX() {
                return entity.prevX;
            }

            @Override
            public double getPrevY() {
                return entity.prevY;
            }

            @Override
            public double getPrevZ() {
                return entity.prevZ;
            }

            @Override
            public int id() {
                return entity.getEntityId();
            }
        };
    }

    public static UPlayer getPlayer(PlayerEntity player) {
        if (player == null) {
            return null;
        }

        return new UPlayer() {
            @Override
            public double getX() {
                return player.getX();
            }

            @Override
            public double getY() {
                return player.getY();
            }

            @Override
            public double getZ() {
                return player.getZ();
            }

            @Override
            public void setX(double x) {
                player.setPos(x, getY(), getZ());
            }

            @Override
            public void setY(double y) {
                player.setPos(getX(), y, getZ());
            }

            @Override
            public void setZ(double z) {
                player.setPos(getX(), getY(), z);
            }

            @Override
            public double getPrevX() {
                return player.prevX;
            }

            @Override
            public double getPrevY() {
                return player.prevY;
            }

            @Override
            public double getPrevZ() {
                return player.prevZ;
            }

            @Override
            public float getYaw() {
                return player.yaw;
            }

            @Override
            public float getPitch() {
                return player.pitch;
            }

            @Override
            public void setYaw(float yaw) {
                player.yaw = yaw;
            }

            @Override
            public void setPitch(float pitch) {
                player.pitch = pitch;
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
