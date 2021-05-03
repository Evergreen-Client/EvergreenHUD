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

package co.uk.isxander.evergreenhud.command;

import co.uk.isxander.evergreenhud.compatability.universal.impl.command.ClientCommand;
import co.uk.isxander.evergreenhud.gui.screens.impl.GuiMain;
import co.uk.isxander.evergreenhud.utils.Constants;
import co.uk.isxander.xanderlib.utils.Multithreading;
import co.uk.isxander.xanderlib.utils.Version;
import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.github.UpdateChecker;

public class EvergreenHudCommand implements ClientCommand, Constants {

    @Override
    public String name() {
        return "evergreenhud";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("check")) {
                Multithreading.runAsync(() -> {
                    if (EvergreenHUD.getInstance().isDevelopment()) {
                        notifications.push("EvergreenHUD", "You are on a development version. There are no updates available.");
                    } else {
                        Version latest = UpdateChecker.getLatestVersion();
                        if (latest.newerThan(EvergreenHUD.PARSED_VERSION)) {
                            EvergreenHUD.notifyUpdate(latest);
                        } else {
                            notifications.push("EvergreenHUD", "There are no updates available.");
                        }
                    }
                });
            } else if (args[0].equalsIgnoreCase("version")) {
                notifications.push("EvergreenHUD", "You are running on version " + EvergreenHUD.MOD_VERSION + "\nIf you want to check for updates, use \"/evergreenhud update\"");
            }
        } else {
            mc.openGui(new GuiMain());
        }
    }
}
