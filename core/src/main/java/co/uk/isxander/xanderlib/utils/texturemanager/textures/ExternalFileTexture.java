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

package co.uk.isxander.xanderlib.utils.texturemanager.textures;

import co.uk.isxander.xanderlib.utils.Constants;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import java.awt.image.BufferedImage;
import java.io.*;

public class ExternalFileTexture extends AbstractTexture {

    protected final File textureLocation;

    public ExternalFileTexture(File file) {
        this.textureLocation = file;
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        InputStream is = new FileInputStream(textureLocation);

        BufferedImage img = TextureUtil.readBufferedImage(is);
        img = modifyImage(img);

        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), img, false, false);
        is.close();
    }

    protected BufferedImage modifyImage(BufferedImage original) {
        return original;
    }

    public interface ImageModifier {
        BufferedImage modify(BufferedImage original);
    }
}
