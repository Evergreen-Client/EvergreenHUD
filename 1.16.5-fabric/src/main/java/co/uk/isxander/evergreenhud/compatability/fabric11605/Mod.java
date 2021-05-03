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
import co.uk.isxander.evergreenhud.compatability.fabric11605.gui.AbstractGuiScreen;
import co.uk.isxander.evergreenhud.compatability.fabric11605.keybind.KeybindManager;
import co.uk.isxander.evergreenhud.compatability.fabric11605.mixin.AccessorMinecraft;
import co.uk.isxander.evergreenhud.compatability.fabric11605.mixin.AccessorWorldRenderer;
import co.uk.isxander.evergreenhud.compatability.universal.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.*;
import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UEntity;
import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UPlayer;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.GuiProvider;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButton;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.screen.UGuiScreen;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.screen.UGuiScreenImp;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.Keyboard;
import co.uk.isxander.evergreenhud.compatability.universal.impl.render.*;
import co.uk.isxander.evergreenhud.event.impl.ClientTickEvent;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public class Mod implements ClientModInitializer {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> EvergreenHUD.EVENT_BUS.post(new ClientTickEvent()));
        new KeybindManager();

        UniversalManager.guiProvider = new GuiProvider() {
            @Override
            public UGuiScreen gui() {
                return new AbstractGuiScreen();
            }

            @Override
            public UGuiButton button() {
                return null;
            }
        };
        UniversalManager.commandManager = command ->
                ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal(command.name()).executes(context -> {
            // TODO: 03/05/2021 get list of arguments
            command.execute(new String[]{});

            return 0;
        }));

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
                    public double getPrevX() {
                        return mc.player.prevX;
                    }

                    @Override
                    public double getPrevY() {
                        return mc.player.prevY;
                    }

                    @Override
                    public double getPrevZ() {
                        return mc.player.prevZ;
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

                    @Override
                    public double calculateReachDistFromEntity(UEntity entity) {
                        // TODO: 03/05/2021 find modern code for calculating reach dist
                        return -1;
                    }

                    @Override
                    public int id() {
                        return mc.player.getEntityId();
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
                            mc.textRenderer.drawWithShadow(null, text, x, y, color);
                        else
                            mc.textRenderer.draw(null, text, x, y, color);
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
                Screen screen = new Screen(new LiteralText(gui.getTitle())) {
                    @Override
                    public Text getTitle() {
                        return new LiteralText(gui.getTitle());
                    }

                    @Override
                    public String getNarrationMessage() {
                        return getTitle().asString();
                    }

                    @Override
                    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                        gui.draw(mouseX, mouseY, delta);
                    }

                    @Override
                    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                        gui.keyTyped(Keyboard.getGlfw(keyCode + modifiers));
                        return true;
                    }

                    @Override
                    public boolean shouldCloseOnEsc() {
                        return true;
                    }

                    @Override
                    public void onClose() {
                        gui.guiClosed();
                    }

                    @Override
                    protected <T extends AbstractButtonWidget> T addButton(T button) {
                        return null;
                    }

                    @Override
                    protected <T extends Element> T addChild(T child) {
                        return null;
                    }

                    @Override
                    protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y) {

                    }

                    @Override
                    public List<Text> getTooltipFromItem(ItemStack stack) {
                        return new ArrayList<>();
                    }

                    @Override
                    public void renderTooltip(MatrixStack matrices, Text text, int x, int y) {

                    }

                    @Override
                    public void renderTooltip(MatrixStack matrices, List<Text> lines, int x, int y) {

                    }

                    @Override
                    public void renderOrderedTooltip(MatrixStack matrices, List<? extends OrderedText> lines, int x, int y) {

                    }

                    @Override
                    protected void renderTextHoverEffect(MatrixStack matrices, @Nullable Style style, int x, int y) {

                    }

                    @Override
                    protected void insertText(String text, boolean override) {

                    }

                    @Override
                    public boolean handleTextClick(@Nullable Style style) {
                        return true;
                    }

                    @Override
                    public void sendMessage(String message) {

                    }

                    @Override
                    public void sendMessage(String message, boolean toHud) {

                    }

                    @Override
                    public void init(MinecraftClient client, int width, int height) {

                    }

                    @Override
                    public List<? extends Element> children() {
                        return new ArrayList<>();
                    }

                    @Override
                    protected void init() {
                        gui.init();
                    }

                    @Override
                    public void tick() {

                    }

                    @Override
                    public void removed() {

                    }

                    @Override
                    public void renderBackground(MatrixStack matrices) {

                    }

                    @Override
                    public void renderBackground(MatrixStack matrices, int vOffset) {

                    }

                    @Override
                    public void renderBackgroundTexture(int vOffset) {

                    }

                    @Override
                    public boolean isPauseScreen() {
                        return gui.isPauseScreen();
                    }

                    @Override
                    public void resize(MinecraftClient client, int width, int height) {

                    }

                    @Override
                    protected boolean isValidCharacterForName(String name, char character, int cursorPos) {
                        return true;
                    }

                    @Override
                    public boolean isMouseOver(double mouseX, double mouseY) {
                        return true;
                    }

                    @Override
                    public void filesDragged(List<Path> paths) {

                    }

                    @Nullable
                    @Override
                    public Element getFocused() {
                        return this;
                    }

                    @Override
                    public void setFocused(@Nullable Element focused) {

                    }

                    @Override
                    protected void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {

                    }

                    @Override
                    protected void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {

                    }

                    @Override
                    protected void fillGradient(MatrixStack matrices, int xStart, int yStart, int xEnd, int yEnd, int colorStart, int colorEnd) {

                    }

                    @Override
                    public void method_29343(int i, int j, BiConsumer<Integer, Integer> biConsumer) {

                    }

                    @Override
                    public void drawTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {

                    }

                    @Override
                    public int getZOffset() {
                        return 0;
                    }

                    @Override
                    public void setZOffset(int zOffset) {

                    }

                    @Override
                    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
                        return Optional.of(this);
                    }

                    @Override
                    public boolean mouseClicked(double mouseX, double mouseY, int button) {
                        gui.mouseClicked((int) mouseX, (int) mouseY, button);
                        return true;
                    }

                    @Override
                    public boolean mouseReleased(double mouseX, double mouseY, int button) {
                        gui.mouseReleased((int) mouseX, (int) mouseY, button);
                        return true;
                    }

                    @Override
                    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
                        return true;
                    }

                    @Override
                    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
                        return true;
                    }

                    @Override
                    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
                        return true;
                    }

                    @Override
                    public boolean charTyped(char chr, int modifiers) {
                        return true;
                    }

                    @Override
                    public void setInitialFocus(@Nullable Element element) {

                    }

                    @Override
                    public void focusOn(@Nullable Element element) {

                    }

                    @Override
                    public boolean changeFocus(boolean lookForwards) {
                        return true;
                    }

                    @Override
                    public void mouseMoved(double mouseX, double mouseY) {
                        gui.mouseMoved((int) mouseX, (int) mouseY);
                    }
                };
            }

            @Override
            public int getFps() {
                return ((AccessorMinecraft) mc).getCurrentFps();
            }

            @Override
            public int renderedEntityCount() {
                return ((AccessorWorldRenderer) mc.worldRenderer).getEntityCount();
            }
        };

        EvergreenHUD.instance = new EvergreenHUD(MCVersion.FABRIC_1_16_5);
    }

}
