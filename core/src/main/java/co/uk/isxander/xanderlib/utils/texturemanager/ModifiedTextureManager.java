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

package co.uk.isxander.xanderlib.utils.texturemanager;

import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.texturemanager.textures.ExternalFileTexture;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ModifiedTextureManager implements Constants {

    private final Map<File, ITextureObject> fileTextureMap;

    public ModifiedTextureManager() {
        this.fileTextureMap = new HashMap<>();
    }

    public void bindTexture(File file) {
        bindTexture(file, (img) -> img);
    }

    public void bindTexture(File file, ExternalFileTexture.ImageModifier imageModifier) {
        ITextureObject texture = fileTextureMap.get(file);
        if (texture == null) {
            texture = new ExternalFileTexture(file) {
                @Override
                protected BufferedImage modifyImage(BufferedImage original) {
                    return imageModifier.modify(original);
                }
            };
            loadTexture(file, texture);
        }

        GlStateManager.bindTexture(texture.getGlTextureId());
    }

    private boolean loadTexture(File file, ITextureObject texture) {
        boolean success = true;

        try {
            texture.loadTexture(mc.getResourceManager());
        } catch (IOException e) {
            XanderLib.LOGGER.warn("Failed to load texture: " + file.getPath(), e);
            texture = TextureUtil.missingTexture;
            this.fileTextureMap.put(file, texture);
            success = false;
        } catch (Throwable throwable) {
            ITextureObject finalTexture = texture;
            CrashReport crash = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crash.makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", file.getPath());
            crashreportcategory.addCrashSectionCallable("Texture object class", () -> finalTexture.getClass().getName());
            throw new ReportedException(crash);
        }
        this.fileTextureMap.put(file, texture);
        return success;
    }

    public ITextureObject getTexture(File file) {
        return fileTextureMap.get(file);
    }

    public void deleteTexture(File file) {
        if (fileTextureMap.containsKey(file)) {
            GlStateManager.deleteTexture(fileTextureMap.get(file).getGlTextureId());
            fileTextureMap.remove(file);
        }
    }


}
