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

package co.uk.isxander.xanderlib.ui.notification;

final class Notification {

    // these are package private on purpose im not an idiot
    String title;
    String description;
    Runnable runnable;

    float time;
    float width;
    float mouseOverAdd;
    boolean closing;
    boolean clicked;

    Notification(String title, String description, Runnable runnable) {
        this.title = title;
        this.description = description;
        this.runnable = runnable;

        this.time = 0;
        this.width = 0;
        this.mouseOverAdd = 0;
        this.closing = false;
        this.clicked = false;
    }
}
