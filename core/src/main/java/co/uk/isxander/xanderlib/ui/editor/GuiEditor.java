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

package co.uk.isxander.xanderlib.ui.editor;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import me.kbrewster.eventbus.Subscribe;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

public final class GuiEditor {

    public final Map<Class<? extends GuiScreen>, ArrayList<AbstractGuiModifier>> guiModifiers;

    private int mouseX, mouseY;
    private float partialTicks;

    public GuiEditor() {
        this.mouseX = 0;
        this.mouseY = 0;
        this.partialTicks = 0;

        this.guiModifiers = new HashMap<>();
        EvergreenHUD.EVENT_BUS.register(this);
    }

    public void addModifier(Class<? extends GuiScreen> gui, AbstractGuiModifier... buttons) {
        this.guiModifiers.putIfAbsent(gui, new ArrayList<>());
        this.guiModifiers.get(gui).addAll(Arrays.asList(buttons));
    }

    public void removeModifier(Class<? extends GuiScreen> gui, AbstractGuiModifier... buttons) {
        List<AbstractGuiModifier> mod = guiModifiers.get(gui);
        if (mod != null) {
            mod.removeAll(Arrays.asList(buttons));
        }
    }

    @Subscribe(priority = 5)
    public void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onInitGuiPre(event.gui, event.buttonList);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onInitGuiPost(event.gui, event.buttonList);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onActionPerformedPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onActionPerformedPre(event.gui, event.buttonList, event.button);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onActionPerformedPost(GuiScreenEvent.ActionPerformedEvent.Post event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onActionPerformedPost(event.gui, event.buttonList, event.button);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDrawScreenPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                this.mouseX = event.mouseX;
                this.mouseY = event.mouseY;
                this.partialTicks = event.renderPartialTicks;
                mod.onDrawScreenPre(event.gui, mouseX, mouseY, event.renderPartialTicks);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDrawScreenPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onDrawScreenPost(event.gui, mouseX, mouseY, partialTicks);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBackgroundDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onBackgroundDraw(event.gui, event.getMouseX(), event.getMouseY());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyboardInputPre(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onKeyboardInputPre(event.gui);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyboardInputPost(GuiScreenEvent.KeyboardInputEvent.Post event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onKeyboardInputPost(event.gui);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMouseInputPre(GuiScreenEvent.MouseInputEvent.Pre event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onMouseInputPre(event.gui);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMouseInputPost(GuiScreenEvent.MouseInputEvent.Post event) {
        List<AbstractGuiModifier> mods = guiModifiers.get(event.gui.getClass());
        if (mods != null) {
            for (AbstractGuiModifier mod : mods) {
                mod.onMouseInputPost(event.gui);
            }
        }
    }

}
