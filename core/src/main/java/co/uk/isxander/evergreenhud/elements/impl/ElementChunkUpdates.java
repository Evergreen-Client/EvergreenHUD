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

package co.uk.isxander.evergreenhud.elements.impl;

import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.event.impl.ClientTickEvent;
import co.uk.isxander.evergreenhud.settings.impl.DoubleSetting;

public class ElementChunkUpdates extends Element {

    private long time = 0L;
    private String chunkUpdates = "0";

    public DoubleSetting updateTime;

    @Override
    public void initialise() {
        addSettings(updateTime = new DoubleSetting("Update Time", "How often the counter updates.", 1, 0.5, 10, " secs"));
    }

    @Override
    protected ElementData metadata() {
        return new ElementData("Chunk Updates", "Displays the amount of chunk updates currently taking place.");
    }

    @Override
    protected String getValue() {
        return chunkUpdates;
    }

    @Override
    public void onClientTick(ClientTickEvent event) {
        if (System.currentTimeMillis() - time > updateTime.get() * 1000L) {
            time = System.currentTimeMillis();
            chunkUpdates = Integer.toString(RenderChunk.renderChunksUpdated);
        }
    }

    @Override
    public String getDisplayTitle() {
        return "Updates";
    }

}
