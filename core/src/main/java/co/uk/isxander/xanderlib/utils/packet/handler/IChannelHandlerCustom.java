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

public interface IChannelHandlerCustom {

    /**
     * @return the name of the channel handler
     */
    String name();

    /**
     * @return the channel handler to add
     */
    ChannelHandler handler();

    /**
     * @return what handler this needs to be added before
     */
    String addBefore();

    /**
     * @return what handler this needs to be added after
     */
    String addAfter();

    /**
     * If addBefore or addAfter are not null, this becomes redundant.
     * If false, will be added last
     *
     * @return if handler should be added first
     */
    boolean first();

    /**
     * @return what handlers need to exist for this to be added
     */
    String[] requires();

}
