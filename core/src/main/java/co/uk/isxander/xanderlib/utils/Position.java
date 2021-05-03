/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.utils;

import co.uk.isxander.evergreenhud.compatability.universal.impl.UResolution;

public final class Position {

    private float x, y, scale;

    Position(float x, float y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public static Position getPositionWithRawPositioning(int x, int y, float scale, UResolution resolution) {
        return new Position(MathUtils.getPercent(x, 0, resolution.scaledWidth()), MathUtils.getPercent(y, 0, resolution.scaledHeight()), scale);
    }

    public static Position getPositionWithScaledPositioning(float x, float y, float scale) {
        return new Position(x, y, scale);
    }

    public float getRawX(UResolution resolution) {
        return ((float)resolution.scaledWidth() * x);
    }

    public float getRawY(UResolution resolution) {
        return ((float)resolution.scaledHeight() * y);
    }

    public float getXScaled() {
        return x;
    }

    public float getYScaled() {
        return y;
    }

    public void setRawX(float x, UResolution resolution) {
        this.x = MathUtils.getPercent(x, 0, resolution.scaledWidth());
    }

    public void setRawY(float y, UResolution resolution) {
        this.y = MathUtils.getPercent(y, 0, resolution.scaledHeight());
    }

    public void setScaledX(float x) {
        this.x = x;
    }

    public void setScaledY(float y) {
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void add(Position b) {
        x += b.x;
        y += b.y;
        scale += b.scale;
    }

    public void subtract(Position b) {
        x -= b.x;
        y -= b.y;
        scale -= b.scale;
    }

    public static Position center() {
        return new Position(0, 0, 1);
    }

}
