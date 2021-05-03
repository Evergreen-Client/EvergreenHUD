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

package co.uk.isxander.evergreenhud.compatability.fabric11605;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.fabric11605.callback.*;
import co.uk.isxander.evergreenhud.compatability.universal.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.*;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.toast.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class FabricMod implements ClientModInitializer {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    @Override
    public void onInitializeClient() {

        UniversalManager.tessellator = new UTessellator() {
            private final Tessellator tes = Tessellator.getInstance();
            private final BufferBuilder buf = tes.getBuffer();
            @Override
            public UBufferBuilder buffer() {
                return new UBufferBuilder() {
                    @Override
                    public UBufferBuilder vertex(double x, double y, double z) {
                        buf.vertex(x, y, z);
                        return this;
                    }

                    @Override
                    public UBufferBuilder color(float r, float g, float b, float a) {
                        buf.color(r, g, b, a);
                        return this;
                    }

                    @Override
                    public UBufferBuilder tex(double u, double v) {
                        buf.texture((float) u, (float) v);
                        return this;
                    }

                    @Override
                    public void next() {
                        buf.next();
                    }

                    @Override
                    public void end() {
                        buf.end();
                    }

                    @Override
                    public void begin(int mode, co.uk.isxander.evergreenhud.compatability.universal.impl.render.vertex.VertexFormats format) {
                        VertexFormat parsedFormat = null;
                        switch (format) {
                            case POSITION:
                                parsedFormat = VertexFormats.POSITION;
                                break;
                            case POSITION_COLOR:
                                parsedFormat = VertexFormats.POSITION_COLOR;
                                break;
                            case POSITION_TEXTURE:
                                parsedFormat = VertexFormats.POSITION_TEXTURE;
                                break;
                            case POSITION_COLOR_TEXTURE:
                                parsedFormat = VertexFormats.POSITION_COLOR_TEXTURE;
                                break;
                        }
                        buf.begin(mode, parsedFormat);
                    }
                };
            }

            @Override
            public void draw() {
                tes.draw();
            }
        };
        UniversalManager.gl11 = new UGL11() {
            @Override
            public void push() {
                GlStateManager.pushMatrix();
            }

            @Override
            public void pop() {
                GlStateManager.popMatrix();
            }

            @Override
            public void scale(float x, float y, float z) {
                GlStateManager.scalef(z, y, z);
            }

            @Override
            public void translate(float x, float y, float z) {
                GlStateManager.translatef(z, y, z);
            }

            @Override
            public void color(float r, float g, float b, float a) {
                GlStateManager.color4f(r, g, b, a);
            }

            @Override
            public void bindTexture(int texture) {
                GlStateManager.bindTexture(texture);
            }

            @Override
            public void glScissor(int x, int y, int width, int height) {
                GL11.glScissor(x, y, width, height);
            }

            @Override
            public void glLineWidth(float width) {
                GL11.glLineWidth(width);
            }

            @Override
            public void glVertex2f(float x, float y) {
                GL11.glVertex2f(x, y);
            }

            @Override
            public void glVertex2d(double x, double y) {
                GL11.glVertex2d(x, y);
            }

            @Override
            public void glTexCoord2d(double s, double t) {
                GL11.glTexCoord2d(s, t);
            }

            @Override
            public void glTexCoord2f(float s, float t) {
                GL11.glTexCoord2f(s, t);
            }

            @Override
            public void glEnable(int target) {
                GL11.glEnable(target);
            }

            @Override
            public void glDisable(int target) {
                GL11.glDisable(target);
            }

            @Override
            public void glBegin(int mode) {
                GL11.glBegin(mode);
            }

            @Override
            public void glEnd() {
                GL11.glEnd();
            }

            @Override
            public void enableBlend() {
                GlStateManager.enableBlend();
            }

            @Override
            public void disableBlend() {
                GlStateManager.disableBlend();
            }

            @Override
            public void enableTexture() {
                GlStateManager.enableTexture();
            }

            @Override
            public void disableTexture() {
                GlStateManager.disableTexture();
            }

            @Override
            public void enableAlpha() {
                GlStateManager.enableAlphaTest();
            }

            @Override
            public void disableAlpha() {
                GlStateManager.disableAlphaTest();
            }

            @Override
            public void enableDepth() {
                GlStateManager.enableDepthTest();
            }

            @Override
            public void disableDepth() {
                GlStateManager.disableDepthTest();
            }

            @Override
            public void blendFuncSeparate(int srcFactorRGB, int dstFactorRGB, int srcFactorAlpha, int dstFactorAlpha) {
                GlStateManager.blendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
            }

            @Override
            public void blendFunc(int srcFactor, int dstFactor) {
                GlStateManager.blendFunc(srcFactor, dstFactor);
            }
        };
        UniversalManager.mouseUtils = new UMouseUtils() {
            @Override
            public int getX() {
                return (int) mc.mouse.getX();
            }

            @Override
            public int getY() {
                return (int) mc.mouse.getY();
            }

            @Override
            public boolean isMouseDown() {
                return mc.mouse.wasLeftButtonClicked();
            }
        };
        UniversalManager.resolution = new UResolution() {
            @Override
            public int scaledWidth() {
                return mc.getWindow().getScaledWidth();
            }

            @Override
            public int scaledHeight() {
                return mc.getWindow().getScaledHeight();
            }

            @Override
            public int displayWidth() {
                return mc.getWindow().getWidth();
            }

            @Override
            public int displayHeight() {
                return mc.getWindow().getHeight();
            }

            @Override
            public double getScaleFactor() {
                return mc.getWindow().getScaleFactor();
            }
        };
        UniversalManager.mcInstance = new UMinecraft() {

            @Override
            public File dataDir() {
                return mc.runDirectory;
            }

            @Override
            public UPlayer player() {
                if (mc.player == null)
                    return null;

                return new UPlayer() {
                    @Override
                    public double getX() {
                        return mc.player.getX();
                    }

                    @Override
                    public double getY() {
                        return mc.player.getY();
                    }

                    @Override
                    public double getZ() {
                        return mc.player.getZ();
                    }

                    @Override
                    public void setX(double x) {
                        mc.player.setPos(x, getY(), getZ());
                    }

                    @Override
                    public void setY(double y) {
                        mc.player.setPos(getX(), y, getZ());
                    }

                    @Override
                    public void setZ(double z) {
                        mc.player.setPos(getX(), getY(), z);
                    }

                    @Override
                    public float getYaw() {
                        return mc.player.yaw;
                    }

                    @Override
                    public float getPitch() {
                        return mc.player.pitch;
                    }

                    @Override
                    public void setYaw(float yaw) {
                        mc.player.yaw = yaw;
                    }

                    @Override
                    public void setPitch(float pitch) {
                        mc.player.pitch = pitch;
                    }
                };
            }

            @Override
            public UFontRenderer fontRenderer() {
                return new UFontRenderer() {
                    @Override
                    public void drawString(String text, float x, float y, int color, boolean shadow) {
                        // FIXME: 03/05/2021 get instance of matrix stack
                        if (shadow)
                            mc.textRenderer.drawWithShadow(new MatrixStack(), text, x, y, color);
                        else
                            mc.textRenderer.draw(new MatrixStack(), text, x, y, color);
                    }

                    @Override
                    public int fontHeight() {
                        return mc.textRenderer.fontHeight;
                    }

                    @Override
                    public int stringWidth(String text) {
                        return mc.textRenderer.getWidth(text);
                    }
                };
            }

            @Override
            public boolean inGameHasFocus() {
                // test if this is right
                return !mc.isPaused();
            }

            @Override
            public void openGui(UGuiScreenImp gui) {

            }
        };

        EvergreenHUD.instance = new EvergreenHUD(MCVersion.FABRIC_1_16_5);

        ModInitCallback.EVENT.register(() -> {
            EvergreenHUD.instance.init();

            return ActionResult.PASS;
        });

        ModPostInitCallback.EVENT.register(() -> {
            EvergreenHUD.instance.postInit();
            mc.getToastManager().add(new SystemToast(SystemToast.Type.TUTORIAL_HINT, new LiteralText("EvergreenHUD"), new LiteralText("This is a test!")));
            return ActionResult.PASS;
        });
    }

}
