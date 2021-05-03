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

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.universal.impl.UGL11;
import co.uk.isxander.evergreenhud.compatability.universal.impl.UResolution;
import co.uk.isxander.evergreenhud.utils.Constants;
import co.uk.isxander.xanderlib.utils.MathUtils;
import co.uk.isxander.xanderlib.utils.StringUtils;
import me.kbrewster.eventbus.Subscribe;
import net.apolloclient.utils.GLRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class NotificationManager implements Constants {

    private static final int TOAST_WIDTH = 200;
    private static final int TOAST_PADDING_WIDTH = 5;
    private static final int TOAST_PADDING_HEIGHT = 3;
    private static final int TOAST_TEXT_DISTANCE = 2;

    private final List<Notification> currentNotifications = new ArrayList<>();

    public NotificationManager() {
        EvergreenHUD.EVENT_BUS.register(this);
    }

    public void push(String title, String description, Runnable runnable) {
        if (title == null || description == null)
            throw new NullPointerException("Title or Description is null.");
        currentNotifications.add(new Notification(title, description, runnable));
    }

    public void push(String title, String description) {
        push(title, description, null);
    }

    @Subscribe
    public void onRender(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        UResolution res = resolution;
        if (currentNotifications.size() == 0)
            return;
        Notification notification = currentNotifications.get(0);

        float time = notification.time;
        float opacity = 200;

        if (time <= 1 || time >= 10) {
            float easeTime = Math.min(time, 1);
            opacity = easeTime * 200;
        }
        List<String> wrappedTitle = StringUtils.wrapTextLines(EnumChatFormatting.BOLD + notification.title, fr, TOAST_WIDTH, " ");
        List<String> wrappedText = StringUtils.wrapTextLines(notification.description, fr, TOAST_WIDTH, " ");
        int textLines = wrappedText.size() + wrappedTitle.size();
        float rectWidth = notification.width = MathUtils.lerp(notification.width, TOAST_WIDTH + (TOAST_PADDING_WIDTH * 2), event.renderTickTime / 4f);
        float rectHeight = (TOAST_PADDING_HEIGHT * 2) + (textLines * fr.fontHeight()) + ((textLines - 1) * TOAST_TEXT_DISTANCE);
        float rectX = res.scaledWidth() / 2f - (rectWidth / 2f);
        float rectY = 5;

        float mouseX = mouseUtils.getX();
        float mouseY = mouseUtils.getY();
        boolean mouseOver = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;

        opacity += notification.mouseOverAdd = MathUtils.lerp(notification.mouseOverAdd, (mouseOver ? 40 : 0), event.renderTickTime / 4f);

        gl11.push();
        gl11.enableBlend();
        gl11.enableAlpha();
        gl11.enableDepth();
        GLRenderer.drawRectangle(rectX, rectY, rectWidth, rectHeight, new Color(0, 0, 0, (int)MathUtils.clamp(opacity, 5, 255)));
        if (notification.time > 0.1f) {
            gl11.glEnable(UGL11.GL_SCISSOR_TEST);
            GLRenderer.glScissor(rectX, rectY, rectWidth, rectHeight);
            int color = new Color(255, 255, 255, (int)MathUtils.clamp(opacity, 2, 255)).getRGB();
            int i = 0;
            for (String line : wrappedTitle) {
                fr.drawString(EnumChatFormatting.BOLD + line, res.scaledWidth() / 2f - (fr.stringWidth(line) / 2f), rectY + TOAST_PADDING_HEIGHT + (TOAST_TEXT_DISTANCE * i) + (fr.fontHeight() * i), color, true);
                i++;
            }
            for (String line : wrappedText) {
                fr.drawString(line, res.scaledWidth() / 2f - (fr.stringWidth(line) / 2f), rectY + TOAST_PADDING_HEIGHT + (TOAST_TEXT_DISTANCE * i) + (fr.fontHeight() * i), color, false);
                i++;
            }
            gl11.glDisable(UGL11.GL_SCISSOR_TEST);
        }

        gl11.pop();
        if (notification.time >= 3f) {
            notification.closing = true;
        }
        if (!notification.clicked && mouseOver && mouseUtils.isMouseDown()) {
            notification.clicked = true;
            if (notification.runnable != null)
                notification.runnable.run();
            notification.closing = true;
            if (notification.time > 1f)
                notification.time = 1f;
        }
        if (!((mouseOver && notification.clicked) && notification.time > 1f)) {
            notification.time += (notification.closing ? -0.02f : 0.02f) * (event.renderTickTime * 3f);
        }
        if (notification.closing && notification.time <= 0) {
            currentNotifications.remove(notification);
        }
    }

}
