/*
 * Copyright (C) Evergreen [2020 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 */

package com.evergreenclient.hudmod.elements.impl;

import com.evergreenclient.hudmod.elements.Element;
import com.evergreenclient.hudmod.settings.impl.ArraySetting;
import com.evergreenclient.hudmod.utils.ElementData;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ElementCps extends Element {

    private final List<Long> left = new ArrayList<>();
    private boolean leftPressed;
    private final List<Long> right = new ArrayList<>();
    private boolean rightPressed;

    public ArraySetting button;

    @Override
    public void initialise() {
        addSettings(button = new ArraySetting("Button", "Which button to display.", "Both", new String[]{"Left", "Right", "Both"}));
    }

    @Override
    public ElementData metadata() {
        return new ElementData("CPS Mod", "Shows how many times you can click in a second.");
    }

    @Override
    protected String getValue() {
        String text = "";
        switch (button.get().toLowerCase()) {
            case "left":
                text = Integer.toString(left.size());
                break;
            case "right":
                text = Integer.toString(right.size());
                break;
            case "both":
                text = left.size() + " | " + right.size();
                break;
        }
        return text;
    }

    @Override
    public String getDisplayTitle() {
        return "CPS";
    }

    @Override
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        boolean pressed = Mouse.isButtonDown(0);
        if (pressed != leftPressed) {
            leftPressed = pressed;
            if (pressed) left.add(System.currentTimeMillis());
        }

        pressed = Mouse.isButtonDown(1);
        if (pressed != rightPressed) {
            rightPressed = pressed;
            if (pressed) right.add(System.currentTimeMillis());
        }

        left.removeIf(l -> l + 1000 < System.currentTimeMillis());
        right.removeIf(l -> l + 1000 < System.currentTimeMillis());
    }

}
