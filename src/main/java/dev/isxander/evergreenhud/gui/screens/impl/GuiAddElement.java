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

package dev.isxander.evergreenhud.gui.screens.impl;

import dev.isxander.evergreenhud.elements.ElementManager;
import dev.isxander.evergreenhud.gui.components.BetterGuiButton;
import dev.isxander.evergreenhud.gui.components.BetterGuiTextField;
import dev.isxander.evergreenhud.gui.components.CategoryScrollPane;
import dev.isxander.evergreenhud.gui.components.GuiButtonAlt;
import dev.isxander.evergreenhud.EvergreenHUD;
import dev.isxander.evergreenhud.elements.Element;
import dev.isxander.evergreenhud.gui.screens.GuiScreenExt;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiAddElement extends GuiScreenExt {

    private CategoryScrollPane categoryScrollPane;
    private String currentCategory;
    private final List<Element> cachedElements;
    private BetterGuiTextField searchField;

    public GuiAddElement(GuiScreen parent) {
        super(parent);
        this.currentCategory = null;

        ElementManager manager = EvergreenHUD.getInstance().getElementManager();
        this.cachedElements = new ArrayList<>();
        manager.getAvailableElements().forEach((name, elementClass) -> {
            cachedElements.add(manager.getNewElementInstance(name));
        });
        cachedElements.sort(Comparator.comparing(e -> e.getMetadata().getName()));
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        List<String> categories = new ArrayList<>();
        for (Element element : cachedElements) {
            if (!categories.contains(element.getMetadata().getCategory())) {
                categories.add(element.getMetadata().getCategory());
            }
        }
        //Collections.sort(categories);
        categories.add("");
        categories.add("Search");
        currentCategory = categories.get(0);

        this.categoryScrollPane = new CategoryScrollPane(width / 6, height, 0, height, 0, 20, width, height, categories, (category, id) -> {
            if (category.equals("Search")) currentCategory = null;
            else currentCategory = category;

            addButtons(this.searchField.getText());
        });

        this.searchField = new BetterGuiTextField(-1, mc.fontRendererObj, width - 121, height - 21, 120, 20);
        this.searchField.setText("");
        this.searchField.setEnableBackgroundDrawing(true);
        this.searchField.setMaxStringLength(256);
        this.searchField.setVisible(true);
        this.searchField.setCanLoseFocus(true);
        this.searchField.setFocused(true);

        addButtons(this.searchField.getText());
    }

    private void addButtons(String searchTerm) {
        this.buttonList.clear();

        this.buttonList.add(new GuiButtonAlt(0, width / 2 - 90 - 1, height - 20, 182, 20, "Back"));

        final int startY = 50;
        final int buttonGap = 2;
        final int buttonWidth = 120;
        final int buttonHeight = 20;
        final int startButtonIndex = 1;
        int column = 0;
        int row = 0;
        int index = 1;

        List<Element> filtered = cachedElements
                .stream()
                .filter(element ->
                        (currentCategory == null || currentCategory.equalsIgnoreCase(element.getMetadata().getCategory()))
                                && element.getMetadata().getName().toLowerCase().trim().replace(" ", "")
                                .contains(searchTerm.toLowerCase().trim().replace(" ", "")))
                .collect(Collectors.toList());

        for (Element e : filtered) {
            if (startY + (row * buttonHeight + row * buttonGap) > height - 60 && index - 1 < filtered.size()) {
                column++;
                row = 0;
                for (GuiButton button : this.buttonList) {
                    if (button instanceof ElementButton) {
                        button.xPosition -= (buttonWidth / 2) + buttonGap;
                    }
                }
            }

            int x = width / 2 + ((buttonWidth / 2) * column) - (buttonWidth / 2);
            int y = startY + (row * buttonHeight + row * buttonGap);

            ElementButton button = new ElementButton(startButtonIndex + index, x, y, buttonWidth, buttonHeight, e.getMetadata().getName(), null, e);
            if (cachedElements.stream().filter(element -> element.getClass().equals(e.getClass())).count() >= e.getMetadata().getMaxInstances()) {
                button.description = "You have reached the max number of instances for this type.";
                button.enabled = false;
            }
            this.buttonList.add(button);

            row++;
            index++;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        float scale = 2;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(mc.fontRendererObj, EnumChatFormatting.GREEN + "Add Element", (int)(width / 2 / scale), (int)(5 / scale), -1);
        GlStateManager.popMatrix();
        drawCenteredString(mc.fontRendererObj, "Add a new element to your screen.", width / 2, 25, -1);

        this.searchField.drawTextBox();
        this.searchField.drawTextBoxDescription(mc, mouseX, mouseY);

        String text = "Search Elements";
        mc.fontRendererObj.drawString(text, width - 4 - mc.fontRendererObj.getStringWidth(text), height - 25 - mc.fontRendererObj.FONT_HEIGHT, 0x999999, true);

        this.categoryScrollPane.drawScreen(mouseX, mouseY, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(getParentScreen());
        } else {
            if (button instanceof ElementButton) {
                ElementButton eb = (ElementButton) button;
                EvergreenHUD.getInstance().getElementManager().addElement(eb.getElement());

                if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    mc.displayGuiScreen(eb.getElement().getElementConfigGui(this));
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE)
            mc.displayGuiScreen(getParentScreen());

        if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
            this.categoryScrollPane.setIndex(this.categoryScrollPane.getCategories().indexOf("Search"));
            addButtons(this.searchField.getText());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    private static class ElementButton extends BetterGuiButton {

        private final Element element;

        public ElementButton(int id, int xPos, int yPos, int width, int height, String displayString, String description, Element element) {
            super(id, xPos, yPos, width, height, displayString, description);
            this.element = element;
        }

        public Element getElement() {
            return this.element;
        }

    }

}
