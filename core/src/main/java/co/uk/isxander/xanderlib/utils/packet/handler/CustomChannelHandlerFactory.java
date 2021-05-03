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

package co.uk.isxander.xanderlib.utils.packet.handler;

import io.netty.channel.ChannelHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomChannelHandlerFactory {

    private String name;
    private ChannelHandler handler;
    private String addBefore;
    private String addAfter;
    private boolean first;
    private List<String> requirements;

    public static CustomChannelHandlerFactory newInstance() {
        return new CustomChannelHandlerFactory(null, null, null, null, false, new ArrayList<>());
    }

    private CustomChannelHandlerFactory(String name, ChannelHandler handler, String addBefore, String addAfter, boolean first, ArrayList<String> requirements) {
        this.name = name;
        this.handler = handler;
        this.addBefore = addBefore;
        this.addAfter = addAfter;
        this.first = first;
        this.requirements = requirements;
    }

    public CustomChannelHandlerFactory setName(String name) {
        this.name = name;
        return this;
    }

    public CustomChannelHandlerFactory setHandler(ChannelHandler handler) {
        if (handler.getClass().getAnnotation(ChannelHandler.Sharable.class) == null) {
            throw new IllegalArgumentException("ChannelHandler must be sharable.");
        }
        this.handler = handler;
        return this;
    }

    public CustomChannelHandlerFactory setAddBefore(String addBefore) {
        this.addBefore = addBefore;
        return this;
    }

    public CustomChannelHandlerFactory setAddAfter(String addAfter) {
        this.addAfter = addAfter;
        return this;
    }

    public CustomChannelHandlerFactory setFirst(boolean first) {
        this.first = first;
        return this;
    }

    public CustomChannelHandlerFactory setRequirements(String... requirements) {
        this.requirements = Arrays.asList(requirements);
        return this;
    }

    public CustomChannelHandlerFactory addRequirements(String... requirements) {
        this.requirements.addAll(Arrays.asList(requirements));
        return this;
    }

    public CustomChannelHandlerFactory removeRequirements(String... requirements) {
        this.requirements.removeAll(Arrays.asList(requirements));
        return this;
    }

    public IChannelHandlerCustom build() {
        if (validate()) {
            return new IChannelHandlerCustom() {
                @Override
                public String name() {
                    return name;
                }

                @Override
                public ChannelHandler handler() {
                    return handler;
                }

                @Override
                public String addBefore() {
                    return addBefore;
                }

                @Override
                public String addAfter() {
                    return addAfter;
                }

                @Override
                public boolean first() {
                    return first;
                }

                @Override
                public String[] requires() {
                    return requirements.toArray(new String[0]);
                }
            };
        }
        throw new IllegalStateException("Name or Channel Handler are null. These are needed to create a valid IChannelHandlerCustom");
    }

    public boolean validate() {
        return !(name == null || handler == null || requirements == null);
    }

}
