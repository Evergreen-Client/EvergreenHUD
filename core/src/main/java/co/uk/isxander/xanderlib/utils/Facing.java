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

package co.uk.isxander.xanderlib.utils;

public enum Facing {
    NORTH("North", "N"),
    NORTH_EAST("North East", "NE"),
    EAST("East", "E"),
    SOUTH_EAST("South East", "SE"),
    SOUTH("South", "S"),
    SOUTH_WEST("South West", "SW"),
    WEST("West", "W"),
    NORTH_WEST("North West", "NW");

    private final String normal;
    private final String abbreviated;
    Facing(String normal, String abbreviated) {
        this.normal = normal;
        this.abbreviated = abbreviated;
    }

    public String getNormal() {
        return this.normal;
    }

    public String getAbbreviated() {
        return this.abbreviated;
    }

    public static Facing parse(float yaw) {
        float rotationYaw = MathUtils.wrapAngle180(yaw);
        Facing facing = Facing.NORTH;
        if (rotationYaw >= 165f || rotationYaw <= -165f) {
            facing = Facing.NORTH;
        } else if (rotationYaw >= -165f && rotationYaw <= -105f) {
            facing = Facing.NORTH_EAST;
        } else if (rotationYaw >= -105f && rotationYaw <= -75f) {
            facing = Facing.EAST;
        } else if (rotationYaw >= -75f && rotationYaw <= -15f) {
            facing = Facing.SOUTH_EAST;
        } else if (rotationYaw >= -15f && rotationYaw <= 15f) {
            facing = Facing.SOUTH;
        } else if (rotationYaw >= 15f && rotationYaw <= 75f) {
            facing = Facing.SOUTH_WEST;
        } else if (rotationYaw >= 75f && rotationYaw <= 105f) {
            facing = Facing.WEST;
        } else if (rotationYaw >= 105f && rotationYaw <= 165f) {
            facing = Facing.NORTH_WEST;
        }
        return facing;
    }
}
