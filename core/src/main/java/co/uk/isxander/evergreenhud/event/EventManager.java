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

package co.uk.isxander.evergreenhud.event;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.event.impl.*;
import me.kbrewster.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static EventManager instance;

    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();
        return instance;
    }

    private final List<Listenable> listenables = new ArrayList<>();

    public EventManager() {
        EvergreenHUD.EVENT_BUS.register(this);
    }

    public void addListener(Listenable listener) {
        this.listenables.add(listener);
    }
    
    public void removeListener(Listenable listener) {
        this.listenables.remove(listener);
    }

    @Subscribe
    public void onClientTick(ClientTickEvent event) {
        listenables.stream().filter(Listenable::canReceiveEvents).forEach((listenable -> listenable.onClientTick(event)));
    }

    @Subscribe
    public void onRenderTick(RenderEvent event) {
        listenables.stream().filter(Listenable::canReceiveEvents).forEach((listenable -> listenable.onRenderTick(event)));
    }

    @Subscribe
    public void onRenderGameOverlay(RenderHud event) {
        listenables.stream().filter(Listenable::canReceiveEvents).forEach((listenable -> listenable.onRenderHud(event)));
    }

    @Subscribe
    public void onAttackEntity(AttackEntityEvent event) {
        listenables.stream().filter(Listenable::canReceiveEvents).forEach((listenable -> listenable.onAttackEntity(event)));
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Incoming event) {
        listenables.stream().filter(Listenable::canReceiveEvents).forEach((listenable -> listenable.onPacketReceive(event)));
    }

}
