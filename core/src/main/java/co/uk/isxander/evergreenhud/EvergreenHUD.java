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

package co.uk.isxander.evergreenhud;

import co.uk.isxander.evergreenhud.compatability.universal.MCVersion;
import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.CustomKeybind;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.Keyboard;
import co.uk.isxander.evergreenhud.elements.ElementManager;
import co.uk.isxander.evergreenhud.elements.impl.ElementText;
import co.uk.isxander.evergreenhud.event.impl.ModInit;
import co.uk.isxander.evergreenhud.event.impl.ModPostInit;
import co.uk.isxander.evergreenhud.github.BlacklistManager;
import co.uk.isxander.evergreenhud.command.EvergreenHudCommand;
import co.uk.isxander.evergreenhud.config.ElementConfig;
import co.uk.isxander.evergreenhud.gui.screens.impl.GuiMain;
import co.uk.isxander.evergreenhud.github.UpdateChecker;
import co.uk.isxander.evergreenhud.utils.Constants;
import co.uk.isxander.xanderlib.utils.Version;
import me.kbrewster.eventbus.EventBus;
import me.kbrewster.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class EvergreenHUD implements Constants {

    public static final String MOD_ID = "evergreenhud";
    public static final String MOD_NAME = "EvergreenHUD";
    public static final String MOD_VERSION = "2.0-pre4";
    public static final String UPDATE_NAME = "the next step.";

    public static final Version PARSED_VERSION = new Version(MOD_VERSION);
    public static final Logger LOGGER = LogManager.getLogger("EvergreenHUD");
    public static final File DATA_DIR = new File(mc.dataDir(), "config/evergreenhud");

    public static EvergreenHUD instance = null;

    public final static EventBus EVENT_BUS = new EventBus();
    private ElementManager elementManager;
    private boolean development;
    private MCVersion mcVersion;

    private boolean firstLaunch = false;
    private boolean versionTwoFirstLaunch = false;
    private boolean disabled = false;

    private boolean reset = false;

    public EvergreenHUD(MCVersion version) {
        this.mcVersion = version;
        EVENT_BUS.register(this);
    }

    @Subscribe
    public void init(ModInit event) {
        disabled = BlacklistManager.isVersionBlacklisted(MOD_VERSION);
        if (disabled)
            return;

        firstLaunch = !DATA_DIR.exists();
        versionTwoFirstLaunch = !ElementConfig.CONFIG_FILE.exists();

        UniversalManager.commandManager.register(new EvergreenHudCommand());
        UniversalManager.keybindManager.registerKeybind(new CustomKeybind(Keyboard.KEY_HOME, "EvergreenHUD", "Open GUI", () -> {
            mc.openGui(new GuiMain());
        }));
        EVENT_BUS.register(elementManager = new ElementManager());


        XanderLib.getInstance().getGuiEditor().addModifier(GuiOptions.class, new AbstractGuiModifier() {
            @Override
            public void onInitGuiPost(GuiScreen screen, List<GuiButton> buttonList) {
                if (mc.theWorld != null)
                    buttonList.add(new GuiButton(991, screen.width / 2 + 5, screen.height / 6 + 24 - 6, 150, 20, "EvergreenHUD..."));
            }

            @Override
            public void onActionPerformedPost(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) {
                if (button.id == 991) {
                    mc.displayGuiScreen(new GuiMain());
                }
            }
        });

        if (isFirstLaunch()) {
            ElementText textElement = new ElementText();
            textElement.text.set("Thank you for downloading EvergreenHUD! Use /evergreenhud to get to the configuration.");
            getElementManager().addElement(textElement);
            getElementManager().getElementConfig().save();
        }
    }

    @Subscribe
    public void postInit(ModPostInit event) {
        if (disabled) {
            notifications.push("EvergreenHUD",
                    "The current version of this mod has been blacklisted.\n"
                    + "Please check the discord server for updates.\n"
                    + "Click to join the discord.",

            () -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/AJv5ZnNT8q"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Notifications.INSTANCE.pushNotification("EvergreenHUD", "Unfortunately, your computer does not seem to support web-browsing.");
                }
                return null;
            });

            return;
        }

        Version latestVersion = UpdateChecker.getLatestVersion();
        Version currentVersion = EvergreenHUD.PARSED_VERSION;
        if (latestVersion.newerThan(currentVersion)) {
            LOGGER.warn("Discovered new version: " + latestVersion + ". Current Version: " + currentVersion);
            notifyUpdate(latestVersion);
        } else if (!Version.sameVersion(latestVersion, currentVersion)) {
            LOGGER.warn("Running on Development Version");
            development = true;
        }

        if (reset) {
            reset = false;
            notifications.push("EvergreenHUD", "The configuration has been reset due to a version change that makes your configuration incompatible with the current version.");
        }

        if (isFirstLaunch()) {
            notifications.push("EvergreenHUD", "Welcome to EvergreenHUD!\n\nIf you wish to support Xander, the creator, join the discord server!");
        }
    }

    public static void notifyUpdate(Version latestVersion) {
        notifications.push("EvergreenHUD", "You are running an outdated version.\nCurrent: " + EvergreenHUD.PARSED_VERSION + "\nLatest: " + latestVersion.toString());
    }

    public static EvergreenHUD getInstance() {
        return instance;
    }

    public ElementManager getElementManager() {
        return elementManager;
    }

    public void notifyConfigReset() {
        reset = true;
    }

    public boolean isDevelopment() {
        return development;
    }

    public boolean isFirstLaunch() {
        return this.firstLaunch;
    }

    public MCVersion getMcVersion() {
        return this.mcVersion;
    }

    public boolean isVersionTwoFirstLaunch() {
        return this.versionTwoFirstLaunch;
    }

}
