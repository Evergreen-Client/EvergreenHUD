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

package dev.isxander.evergreenhud.gui.screens;

import dev.isxander.evergreenhud.EvergreenHUD;
import dev.isxander.evergreenhud.elements.Element;
import dev.isxander.evergreenhud.elements.RenderOrigin;
import dev.isxander.evergreenhud.utils.SnapPoint;
import dev.isxander.evergreenhud.gui.screens.impl.GuiElementConfig;
import dev.isxander.xanderlib.utils.GLRenderer;
import dev.isxander.xanderlib.utils.MathUtils;
import dev.isxander.xanderlib.utils.Resolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiScreenElements extends GuiScreenExt {

    protected Element dragging = null;
    protected Element lastClicked = null;
    protected float offX = 0, offY = 0;

    public GuiScreenElements(GuiScreen parentScreen) {
        super(parentScreen);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseXInt, int mouseYInt, float partialTicks) {
        super.drawScreen(mouseXInt, mouseYInt, partialTicks);

        drawElements(mouseXInt, mouseYInt, partialTicks);
    }

    public void noElementsDrawScreen(int mouseXInt, int mouseYInt, float partialTicks) {
        super.drawScreen(mouseXInt, mouseYInt, partialTicks);
    }

    protected void drawElements(int mouseXInt, int mouseYInt, float partialTicks) {
        ScaledResolution res = Resolution.get();

        for (Element e : EvergreenHUD.getInstance().getElementManager().getCurrentElements()) {
            e.render(partialTicks, RenderOrigin.GUI);
            e.renderGuiOverlay(lastClicked != null && lastClicked == e);
        }

        float mouseX = ((float)Mouse.getX()) * ((float)this.width) / ((float)this.mc.displayWidth);
        float mouseY = ((float)this.height) - ((float)Mouse.getY()) * ((float)this.height) / ((float)this.mc.displayHeight) - 1f;

        if (dragging != null) {
            float elementX = mouseX - offX;
            float elementY = mouseY - offY;

            if (false) {
                boolean foundPoint = false;
                for (SnapPoint localPoint : dragging.getSnapPoints()) {
                    float localX = localPoint.calculateX(res);
                    float localY = localPoint.calculateY(res);
                    for (Element element : EvergreenHUD.getInstance().getElementManager().getCurrentElements()) {
                        if (dragging == element) continue;

                        for (SnapPoint foreignPoint : element.getSnapPoints()) {
                            float foreignX = foreignPoint.calculateX(res);
                            float foreignY = foreignPoint.calculateY(res);

                            SnapPoint.SnapAxis axis = foreignPoint.calculateSnappingAxis(localPoint, res);

                            if (axis == null) {
                                continue;
                            }

                            if (axis == SnapPoint.SnapAxis.X) {
                                if (elementY > localY + SnapPoint.MAX_SNAP_DIST || elementY < localY - SnapPoint.MAX_SNAP_DIST) {
                                    foundPoint = true;
                                    continue;
                                }
                                elementY = localY + (foreignY - localY);
                            } else {
                                if (elementX > localX + SnapPoint.MAX_SNAP_DIST || elementX < localX - SnapPoint.MAX_SNAP_DIST) {
                                    foundPoint = true;
                                    continue;
                                }
                                elementX = localX + (foreignX - localX);
                            }
                            GLRenderer.drawLine(localX, localY, foreignX, foreignY, 1, new Color(255, 0, 0));

                            foundPoint = true;
                            break;
                        }
                        if (foundPoint) break;
                    }
                    if (foundPoint) break;
                }
            }

            dragging.getPosition().setRawX(elementX, res);
            dragging.getPosition().setRawY(elementY, res);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution res = Resolution.get();
        boolean clickedElement = false;

        float x = ((float)Mouse.getX()) * ((float)this.width) / ((float)this.mc.displayWidth);
        float y = ((float)this.height) - ((float)Mouse.getY()) * ((float)this.height) / ((float)this.mc.displayHeight) - 1f;

        List<Element> reversed = new ArrayList<>(EvergreenHUD.getInstance().getElementManager().getCurrentElements());
        Collections.reverse(reversed);
        for (Element e : reversed) {
            e.onMouseClicked(mouseX, mouseY);
            if (e.calculateHitBox(1, e.getPosition().getScale()).doesPositionOverlap(mouseX, mouseY)) {
                lastClicked = dragging = e;
                offX = x - e.getPosition().getRawX(res);
                offY = y - e.getPosition().getRawY(res);
                clickedElement = true;
                break;
            }
        }
        if (EvergreenHUD.getInstance().getElementManager().getCurrentElements().contains(lastClicked)) {
            EvergreenHUD.getInstance().getElementManager().getCurrentElements().remove(lastClicked);
            EvergreenHUD.getInstance().getElementManager().getCurrentElements().add(lastClicked);
        }

        if (!clickedElement) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            lastClicked = null;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
        offX = offY = 0;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        ScaledResolution res = Resolution.get();
        if (lastClicked != null) {
            switch (keyCode) {
                case Keyboard.KEY_UP:
                    lastClicked.getPosition().setRawY(MathUtils.clamp(lastClicked.getPosition().getRawY(res) - 1, 0, res.getScaledHeight() - 1), res);
                    break;
                case Keyboard.KEY_DOWN:
                    lastClicked.getPosition().setRawY(MathUtils.clamp(lastClicked.getPosition().getRawY(res) + 1, 0, res.getScaledHeight() - 1), res);
                    break;
                case Keyboard.KEY_LEFT:
                    lastClicked.getPosition().setRawX(MathUtils.clamp(lastClicked.getPosition().getRawX(res) - 1, 0, res.getScaledWidth() - 1), res);
                    break;
                case Keyboard.KEY_RIGHT:
                    lastClicked.getPosition().setRawX(MathUtils.clamp(lastClicked.getPosition().getRawX(res) + 1, 0, res.getScaledWidth() - 1), res);
                    break;
                case Keyboard.KEY_RETURN:
                    if (this instanceof GuiElementConfig) {
                        GuiElementConfig elementConfig = (GuiElementConfig) this;
                        if (elementConfig.element == lastClicked) {
                            break;
                        }
                    }
                    mc.displayGuiScreen(lastClicked.getElementConfigGui(this));
                    break;
                case Keyboard.KEY_DELETE:
                case Keyboard.KEY_BACK:
                    EvergreenHUD.getInstance().getElementManager().removeElement(lastClicked);
                    break;
            }
        }

    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        EvergreenHUD.getInstance().getElementManager().getElementConfig().save();
    }

}
