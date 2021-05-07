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

package co.uk.isxander.evergreenhud.compatability.forge10809;

import club.sk1er.mods.core.ModCoreInstaller;
import club.sk1er.mods.core.gui.notification.Notifications;
import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.forge10809.listeners.EventListener;
import co.uk.isxander.evergreenhud.compatability.forge10809.providers.UEntityProvider;
import co.uk.isxander.evergreenhud.compatability.forge10809.reflection.ReflectionStore;
import co.uk.isxander.evergreenhud.compatability.universal.MCVersion;
import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UPlayer;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.screen.UGuiScreenImp;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.UBufferBuilder;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.UTessellator;
import co.uk.isxander.evergreenhud.event.impl.ModInit;
import co.uk.isxander.evergreenhud.event.impl.ModPostInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.File;

@Mod(modid = EvergreenHUD.MOD_ID, name = EvergreenHUD.MOD_NAME, version = EvergreenHUD.MOD_VERSION, clientSideOnly = true)
public class Main {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventListener());

        EvergreenHUD.instance = new EvergreenHUD(MCVersion.FORGE_1_8_9);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);

        UniversalManager.mcInstance = new UMinecraft() {
            @Override
            public UPlayer player() {
                return UEntityProvider.getPlayer(mc.thePlayer);
            }

            @Override
            public ServerInfo getServerInfo() {
                if (mc.getCurrentServerData() == null || mc.getNetHandler() == null)
                    return null;

                return new ServerInfo(mc.getCurrentServerData().serverIP, mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime());
            }

            @Override
            public UFontRenderer fontRenderer() {
                return new UFontRenderer() {
                    @Override
                    public void drawString(String text, float x, float y, int color, boolean shadow) {
                        mc.fontRendererObj.drawString(text, x, y, color, shadow);
                    }

                    @Override
                    public int fontHeight() {
                        return mc.fontRendererObj.FONT_HEIGHT;
                    }

                    @Override
                    public int stringWidth(String text) {
                        return mc.fontRendererObj.getStringWidth(text);
                    }
                };
            }

            @Override
            public File dataDir() {
                return mc.mcDataDir;
            }

            @Override
            public boolean inGameHasFocus() {
                return mc.inGameHasFocus;
            }

            @Override
            public void openGui(UGuiScreenImp gui) {
                // TODO: 03/05/2021 guiscreens...
                mc.displayGuiScreen(null);
            }

            @Override
            public int getFps() {
                return Minecraft.getDebugFPS();
            }

            @Override
            public int renderedEntityCount() {
                try {
                   return ReflectionStore.countEntitiesRendered.getInt(mc.renderGlobal);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        };
        UniversalManager.notificationManager = Notifications.INSTANCE::pushNotification;
        UniversalManager.commandManager = command -> ClientCommandHandler.instance.registerCommand(new CommandBase() {
            @Override
            public String getCommandName() {
                return command.name();
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return "/" + command.name();
            }

            @Override
            public int getRequiredPermissionLevel() {
                return -1;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args) {
                command.execute(args);
            }
        });
        UniversalManager.resolution = new UResolution() {
            @Override
            public int displayWidth() {
                return mc.displayWidth;
            }

            @Override
            public int displayHeight() {
                return mc.displayHeight;
            }

            @Override
            public int scaledWidth() {
                return new ScaledResolution(mc).getScaledWidth();
            }

            @Override
            public int scaledHeight() {
                return new ScaledResolution(mc).getScaledHeight();
            }

            @Override
            public double getScaleFactor() {
                return new ScaledResolution(mc).getScaleFactor();
            }
        };
        UniversalManager.mouseUtils = new UMouseUtils() {
            @Override
            public int getX() {
                ScaledResolution res = new ScaledResolution(mc);
                return Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
            }

            @Override
            public int getY() {
                ScaledResolution res = new ScaledResolution(mc);
                return res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight - 1;
            }

            @Override
            public boolean isMouseDown() {
                return Mouse.getEventButtonState();
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
                GlStateManager.scale(x, y, z);
            }

            @Override
            public void translate(float x, float y, float z) {
                GlStateManager.translate(x, y, z);
            }

            @Override
            public void color(float r, float g, float b, float a) {
                GlStateManager.color(r, g, b, a);
            }

            @Override
            public void rotate(float angle, float x, float y, float z) {
                GlStateManager.rotate(angle, x, y, z);
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
                GlStateManager.enableTexture2D();
            }

            @Override
            public void disableTexture() {
                GlStateManager.disableTexture2D();
            }

            @Override
            public void enableAlpha() {
                GlStateManager.enableAlpha();
            }

            @Override
            public void disableAlpha() {
                GlStateManager.disableAlpha();
            }

            @Override
            public void enableDepth() {
                GlStateManager.enableDepth();
            }

            @Override
            public void disableDepth() {
                GlStateManager.disableDepth();
            }

            @Override
            public void blendFuncSeparate(int srcFactorRGB, int dstFactorRGB, int srcFactorAlpha, int dstFactorAlpha) {
                GlStateManager.tryBlendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
            }

            @Override
            public void blendFunc(int srcFactor, int dstFactor) {
                GlStateManager.blendFunc(srcFactor, dstFactor);
            }
        };
        UniversalManager.tessellator = new UTessellator() {
            @Override
            public UBufferBuilder buffer() {
                WorldRenderer wr = Tessellator.getInstance().getWorldRenderer();
                return new UBufferBuilder() {
                    @Override
                    public UBufferBuilder vertex(double x, double y, double z) {
                        wr.pos(x, y, z);
                        return this;
                    }

                    @Override
                    public UBufferBuilder color(float r, float g, float b, float a) {
                        wr.color(r, g, b, a);
                        return this;
                    }

                    @Override
                    public UBufferBuilder tex(double u, double v) {
                        wr.tex(u, v);
                        return this;
                    }

                    @Override
                    public void next() {
                        end();
                    }

                    @Override
                    public void end() {
                        wr.endVertex();
                    }

                    @Override
                    public void begin(int mode, co.uk.isxander.evergreenhud.compatability.universal.impl.render.vertex.VertexFormats format) {
                        VertexFormat parsedFormat = null;
                        switch (format) {
                            case POSITION:
                                parsedFormat = DefaultVertexFormats.POSITION;
                                break;
                            case POSITION_COLOR:
                                parsedFormat = DefaultVertexFormats.POSITION_COLOR;
                                break;
                            case POSITION_TEXTURE:
                                parsedFormat = DefaultVertexFormats.POSITION_TEX;
                                break;
                            case POSITION_COLOR_TEXTURE:
                                parsedFormat = DefaultVertexFormats.POSITION_TEX_COLOR;
                                break;
                        }
                        wr.begin(mode, parsedFormat);
                    }
                };
            }

            @Override
            public void draw() {
                Tessellator.getInstance().draw();
            }
        };

        EvergreenHUD.EVENT_BUS.post(new ModInit());
    }

    @net.minecraftforge.fml.common.Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        EvergreenHUD.EVENT_BUS.post(new ModPostInit());
    }

}
