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
// This file has been improved upon the original found here:
// https://github.com/Ic0vid/apollo/blob/master/src/main/java/net/apolloclient/utils/GLRenderer.java

package net.apolloclient.utils;

import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.UGL11;
import co.uk.isxander.evergreenhud.compatability.universal.impl.UResolution;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.UBufferBuilder;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.UTessellator;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.vertex.VertexFormats;
import co.uk.isxander.evergreenhud.utils.Constants;

import java.awt.*;

/**
 * Various functions to draw gui objects using gl11 and WorldRenderer.
 *
 * @author Icovid | Icovid#3888
 */
public final class GLRenderer implements Constants {

    /**
     * @param xPosition X start location
     * @param yPosition Y start location
     * @param width scissor width
     * @param height scissor height
     */
    public static void glScissor(double xPosition, double yPosition, double width, double height) {
        UResolution res = resolution;
        gl11.glScissor(
                (int)
                        ((xPosition * res.displayWidth())
                                / res.scaledWidth()),
                (int)
                        (((res.scaledHeight() - (yPosition + height))
                                * res.displayHeight())
                                / res.scaledHeight()),
                (int) (width * res.displayWidth() / res.scaledWidth()),
                (int)
                        (height * res.displayHeight() / res.scaledHeight()));
    }

    /**
     * Draw line on screen.
     *
     * @param x x start location
     * @param y y start location
     * @param x1 x end location
     * @param y1 y end location
     * @param color color of line
     */
    public static void drawLine(float x, float y, float x1, float y1, float width, Color color) {
        gl11.enableBlend();
        gl11.disableTexture();
        gl11.glLineWidth(width);
        gl11.blendFuncSeparate(770, 771, 1, 0);
        UBufferBuilder buf = tessellator.buffer();
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F
        );
        buf.begin(UGL11.GL_LINES, VertexFormats.POSITION);
        buf.vertex(x, y, 0.0D).next();
        buf.vertex(x1, y1, 0.0D).next();
        tessellator.draw();
        gl11.enableTexture();
        gl11.disableBlend();
        gl11.glLineWidth(2.0F);
        gl11.bindTexture(0);
        gl11.color(1f, 1f, 1f, 1f);
    }

    /**
     * Draw filled circle on screen.
     *
     * @param xPosition x start location
     * @param yPosition y start location
     * @param radius radius of circle
     * @param color color of circle
     */
    public static void drawCircle(float xPosition, float yPosition, int radius, Color color) {
        gl11.push();
        gl11.glEnable(3042);
        gl11.glDisable(3553);
        gl11.blendFunc(770, 771);
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        UBufferBuilder worldRenderer = tessellator.buffer();
        worldRenderer.begin(UGL11.GL_TRIANGLE_FAN, VertexFormats.POSITION);
        worldRenderer.vertex(xPosition, yPosition, 0).next();
        for (int i = 0; i <= 100; i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer
                    .vertex(xPosition + Math.sin(angle) * radius, yPosition + Math.cos(angle) * radius, 0)
                    .next();
        }
        tessellator.draw();
        gl11.glEnable(3553);
        gl11.glDisable(3042);
        gl11.pop();
        gl11.color(1.0f, 1.0f, 1.0f, 1.0f);
        gl11.bindTexture(0);
    }

    /**
     * Draw hollow circle on screen.
     *
     * @param xPosition x start location
     * @param yPosition y start location
     * @param radius radius of circle
     * @param color color of circle
     * @param thickness width of circle outline
     */
    public static void drawHollowCircle(float xPosition, float yPosition, int radius, float thickness, Color color) {
        gl11.push();
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        gl11.glEnable(2848);
        gl11.glDisable(3553);
        gl11.glEnable(2848);
        gl11.glEnable(3042);
        gl11.glLineWidth(thickness);
        gl11.glBegin(2);
        for (int i = 0; i < 70; i++) {
            int x = (int) (radius * Math.cos((int) (i * 0.08975979010256552D)));
            int y = (int) (radius * Math.sin((int) (i * 0.08975979010256552D)));
            gl11.glVertex2f(xPosition + x, yPosition + y);
        }
        gl11.glEnd();
        gl11.glDisable(2848);
        gl11.glEnable(3553);
        gl11.pop();
        gl11.glLineWidth(2.0F);
        gl11.color(1.0f, 1.0f, 1.0f, 1.0f);
        gl11.bindTexture(0);
    }

    /**
     * @param xPosition x start location
     * @param yPosition y start location
     * @param radius radius of angle
     * @param startAngle Start orientation of angle
     * @param endAngle end orientation of angle
     * @param color color of angle
     * @implNote Strait Angles - 90|180|270|360
     */
    public static void drawPartialCircle(float xPosition, float yPosition, int radius, int startAngle, int endAngle, Color color) {
        gl11.push();
        gl11.glEnable(3042);
        gl11.glDisable(3553);
        gl11.blendFunc(770, 771);
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        UBufferBuilder worldRenderer = tessellator.buffer();
        worldRenderer.begin(UGL11.GL_TRIANGLE_FAN, VertexFormats.POSITION);
        worldRenderer.vertex(xPosition, yPosition, 0).next();
        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer
                    .vertex(xPosition + Math.sin(angle) * radius, yPosition + Math.cos(angle) * radius, 0)
                    .next();
        }
        tessellator.draw();
        gl11.glEnable(3553);
        gl11.glDisable(3042);
        gl11.pop();
        gl11.enableTexture();
        gl11.disableBlend();
        gl11.bindTexture(0);
        gl11.color(1f, 1f, 1f, 1f);
    }

    /**
     * @param xPosition x start location
     * @param yPosition y start location
     * @param radius radius of angle
     * @param startAngle Start orientation of angle
     * @param endAngle end orientation of angle
     * @param color color of angle
     * @param thickness width of circle outline
     * @implNote Strait Angles - 90|180|270|360
     */
    public static void drawHollowPartialCircle(float xPosition, float yPosition, int radius, int startAngle, int endAngle, float thickness, Color color) {
        gl11.push();
        gl11.glDisable(3553);
        gl11.blendFunc(770, 771);
        gl11.glEnable(2848);
        gl11.glLineWidth(thickness);
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        gl11.glBegin(3);
        float f1 = 0.017453292F;
        for (int j = startAngle; j <= endAngle; ++j) {
            int f = (int) ((j - 90) * f1);
            gl11.glVertex2f(
                    xPosition + (int) Math.cos(f) * radius, yPosition + (int) Math.sin(f) * radius);
        }
        gl11.glEnd();
        gl11.glEnable(3553);
        gl11.color(1.0F, 1.0F, 1.0F, 1.0F);
        gl11.glLineWidth(2.0F);
        gl11.pop();
        gl11.color(1.0f, 1.0f, 1.0f, 1.0f);
        gl11.bindTexture(0);
    }

    /**
     * @param xPosition x start location
     * @param yPosition y start location
     * @param width width of rectangle
     * @param height height of rectangle
     * @param color color of rectangle
     */
    public static void drawRectangle(float xPosition, float yPosition, float width, float height, Color color) {
        gl11.enableBlend();
        gl11.disableTexture();
        gl11.blendFuncSeparate(770, 771, 1, 0);
        UBufferBuilder buf = tessellator.buffer();
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        buf.begin(7, VertexFormats.POSITION);
        buf.vertex(xPosition, yPosition + height, 0.0D).next();
        buf.vertex(xPosition + width, yPosition + height, 0.0D).next();
        buf.vertex(xPosition + width, yPosition, 0.0D).next();
        buf.vertex(xPosition, yPosition, 0.0D).next();
        tessellator.draw();
        gl11.enableTexture();
        gl11.disableBlend();
        gl11.bindTexture(0);
        gl11.color(1f, 1f, 1f, 1f);
    }

    /**
     * @param xPosition x start location
     * @param yPosition y start location
     * @param width width of rectangle
     * @param height height of rectangle
     * @param thickness width of the border
     * @param color color of rectangle
     */
    public static void drawHollowRectangle(float xPosition, float yPosition, float width, float height, float thickness, Color color) {
        drawRectangle(xPosition, yPosition, thickness, height, color);
        drawRectangle(xPosition + thickness, yPosition, width - thickness - thickness, thickness, color);
        drawRectangle(xPosition + thickness, yPosition + height - thickness, width - thickness - thickness, thickness, color);
        drawRectangle(xPosition + width - thickness, yPosition, thickness, height, color);
    }

    /**
     * @param xPosition x location
     * @param yPosition y location
     * @param width width of rectangle
     * @param height height of rectangle
     * @param angle angle of rectangle corners
     * @param color color of rectangle
     */
    public static void drawRoundedRectangle(float xPosition, float yPosition, float width, float height, int angle, Color color) {
        drawPartialCircle(xPosition + angle, yPosition + angle, angle, 0, 90, color);
        drawPartialCircle(xPosition + width - angle, yPosition + angle, angle, 270, 360, color);
        drawPartialCircle(
                xPosition + width - angle, yPosition + height - angle, angle, 180, 270, color);
        drawPartialCircle(xPosition + angle, yPosition + height - angle, angle, 90, 180, color);
        drawRectangle(xPosition + angle, yPosition + height - angle, width - (angle * 2), angle, color);
        drawRectangle(xPosition + angle, yPosition, width - (angle * 2), angle, color);
        drawRectangle(xPosition, yPosition + angle, width, height - (angle * 2), color);
    }

    /**
     * @param xPosition x location
     * @param yPosition y location
     * @param width width of rectangle
     * @param height height of rectangle
     * @param angle angle of rectangle corners
     * @param thickness with of the border
     * @param color color of rectangle
     */
    public static void drawHollowRoundedRectangle(int xPosition, int yPosition, int width, int height, int angle, int thickness, Color color) {
        drawHollowPartialCircle(xPosition + angle, yPosition + angle, angle, 0, 90, thickness, color);
        drawHollowPartialCircle(xPosition + width - angle, yPosition + angle, angle, 270, 360, thickness, color);
        drawHollowPartialCircle(
                xPosition + width - angle, yPosition + height - angle, angle, 180, 270, thickness, color);
        drawHollowPartialCircle(xPosition + angle, yPosition + height - angle, angle, 90, 180, thickness, color);
        drawHollowRectangle(xPosition + angle, yPosition + height - angle, width - (angle * 2), angle, thickness, color);
        drawHollowRectangle(xPosition + angle, yPosition, width - (angle * 2), angle, thickness, color);
        drawHollowRectangle(xPosition, yPosition + angle, width, height - (angle * 2), thickness, color);
    }

    /**
     * @param xPosition x start location
     * @param yPosition y start location
     * @param width width of rectangle
     * @param height height of rectangle
     * @param borderWidth width of rectangle border
     * @param color color of rectangle
     * @param borderColor color of rectangle border
     * @deprecated border overlaps - doesn't work with transparency
     */
    public static void drawBorderedRectangle(int xPosition, int yPosition, int width, int height, int borderWidth, Color color, Color borderColor) {
        drawRectangle(xPosition, yPosition, width, height, color);
        gl11.glEnable(UGL11.GL_BLEND);
        gl11.glDisable(UGL11.GL_TEXTURE_2D);
        gl11.blendFunc(UGL11.GL_SRC_ALPHA, UGL11.GL_ONE_MINUS_SRC_ALPHA);
        gl11.glEnable(UGL11.GL_LINE_SMOOTH);
        gl11.push();
        gl11.color(
                borderColor.getRed() / 255.0F,
                borderColor.getGreen() / 255.0F,
                borderColor.getBlue() / 255.0F,
                borderColor.getAlpha() / 255.0F);
        gl11.glLineWidth(borderWidth);
        gl11.glBegin(1);
        gl11.glVertex2d(xPosition, yPosition);
        gl11.glVertex2d(xPosition, (yPosition + height));
        gl11.glVertex2d((xPosition + width), (yPosition + height));
        gl11.glVertex2d((xPosition + width), yPosition);
        gl11.glVertex2d(xPosition, yPosition);
        gl11.glVertex2d((xPosition + width), yPosition);
        gl11.glVertex2d(xPosition, (yPosition + height));
        gl11.glVertex2d((xPosition + width), (yPosition + height));
        gl11.glEnd();
        gl11.pop();
        gl11.glEnable(UGL11.GL_TEXTURE_2D);
        gl11.glDisable(UGL11.GL_BLEND);
        gl11.glDisable(UGL11.GL_LINE_SMOOTH);
    }

    /**
     * Draw textured rectangle on screen.
     *
     * @param resourceLocation ResourceLocation of texture
     * @param xPosition x start location
     * @param yPosition y start location
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public static void drawTexturedRectangle(ResourceLocation resourceLocation, int xPosition, int yPosition, int width, int height) {
        gl11.glEnable(UGL11.GL_BLEND);
        mc.getTextureManager().bindTexture(resourceLocation);
        gl11.glBegin(UGL11.GL_QUADS);
        gl11.glTexCoord2d(1, 1);
        gl11.glVertex2d(xPosition, yPosition);
        gl11.glTexCoord2d(1, 1);
        gl11.glVertex2d(xPosition, yPosition + height);
        gl11.glTexCoord2d(1, 1);
        gl11.glVertex2d(xPosition + width, yPosition + height);
        gl11.glTexCoord2d(1, 1);
        gl11.glVertex2d(xPosition + width, yPosition);
        gl11.glEnd();
        gl11.glDisable(UGL11.GL_BLEND);
        gl11.color(1.0f, 1.0f, 1.0f, 1.0f);
        gl11.bindTexture(0);
    }

    /**
     * Draw polygon based on number of sides.
     *
     * @param xPosition x start location
     * @param yPosition y start location
     * @param radius radius of polygon
     * @param sides number of sides in polygon
     * @param color color of polygon
     */
    public static void drawRegularPolygon(int xPosition, int yPosition, int radius, int sides, Color color) {
        gl11.glEnable(UGL11.GL_BLEND);
        gl11.glDisable(UGL11.GL_TEXTURE_2D);
        gl11.blendFunc(UGL11.GL_SRC_ALPHA, UGL11.GL_ONE_MINUS_SRC_ALPHA);
        UBufferBuilder buf = tessellator.buffer();
        gl11.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
        buf.begin(UGL11.GL_TRIANGLE_FAN, VertexFormats.POSITION);
        buf.vertex(xPosition, yPosition, 0).next();
        for (int i = 0; i <= sides; i++) {
            double angle = ((Math.PI * 2) * i / sides) + Math.toRadians(180);
            buf
                    .vertex(xPosition + Math.sin(angle) * radius, yPosition + Math.cos(angle) * radius, 0)
                    .next();
        }
        tessellator.draw();
        gl11.glEnable(UGL11.GL_TEXTURE_2D);
        gl11.glDisable(UGL11.GL_BLEND);
        gl11.bindTexture(0);
        gl11.color(1f, 1f, 1f, 1f);
    }

    public static void drawModalRect(double x, double y, double u, double v, double uWidth, double vHeight, double width, double height, double tileWidth, double tileHeight) {
        double f = 1.0D / tileWidth;
        double f1 = 1.0D / tileHeight;
        UBufferBuilder buf = tessellator.buffer();
        buf.begin(7, VertexFormats.POSITION_TEXTURE);
        buf.vertex(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).next();
        buf.vertex(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).next();
        buf.vertex(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).next();
        buf.vertex(x, y, 0.0D).tex(u * f, v * f1).next();
        tessellator.draw();
    }
}
