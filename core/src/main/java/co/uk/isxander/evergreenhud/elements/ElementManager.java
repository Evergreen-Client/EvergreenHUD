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

package co.uk.isxander.evergreenhud.elements;

import co.uk.isxander.evergreenhud.gui.screens.GuiScreenElements;
import co.uk.isxander.evergreenhud.utils.Constants;
import co.uk.isxander.evergreenhud.config.ElementConfig;
import co.uk.isxander.evergreenhud.config.MainConfig;
import co.uk.isxander.evergreenhud.event.EventManager;
import me.kbrewster.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ElementManager implements Constants {

    private final List<Element> currentElements;

    /* Config */
    private final MainConfig mainConfig;
    private final ElementConfig elementConfig;
    private boolean enabled;
    private boolean showInChat;
    private boolean showInDebug;
    private boolean showUnderGui;

    private final Logger logger;

    public ElementManager() {
        this.currentElements = new ArrayList<>();
        this.mainConfig = new MainConfig(this);
        this.elementConfig = new ElementConfig(this);
        resetConfig();

        this.logger = LogManager.getLogger("Evergreen Manager");
        this.getElementConfig().load();
        this.getMainConfig().load();
    }

    public void resetConfig() {
        this.enabled = true;
        this.showInChat = true;
        this.showInDebug = false;
        this.showUnderGui = true;
    }

    public List<Element> getCurrentElements() {
        return currentElements;
    }

    @Subscribe
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;

        if (isEnabled()) {
            if ((mc.inGameHasFocus && !mc.gameSettings.showDebugInfo) || (mc.gameSettings.showDebugInfo && showInDebug) || (mc.currentScreen instanceof GuiChat && showInChat) || (!(mc.currentScreen instanceof GuiChat) && mc.currentScreen != null && showUnderGui && !(mc.currentScreen instanceof GuiScreenElements))) {
                for (Element e : currentElements) {
                    e.render(event);
                }
            }
        }
    }

    public void addElement(Element element) {
        EventManager.getInstance().addListener(element);
        this.currentElements.add(element);
        element.onAdded();
    }

    public void removeElement(Element element) {
        EventManager.getInstance().removeListener(element);
        this.currentElements.remove(element);
        element.onRemoved();
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public ElementConfig getElementConfig() {
        return elementConfig;
    }

    public Logger getLogger() {
        return logger;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean doShowInChat() {
        return showInChat;
    }

    public void setShowInChat(boolean showInChat) {
        this.showInChat = showInChat;
    }

    public boolean doShowInDebug() {
        return showInDebug;
    }

    public void setShowInDebug(boolean showInDebug) {
        this.showInDebug = showInDebug;
    }

    public boolean isShowUnderGui() {
        return showUnderGui;
    }

    public void setShowUnderGui(boolean showUnderGui) {
        this.showUnderGui = showUnderGui;
    }
}
