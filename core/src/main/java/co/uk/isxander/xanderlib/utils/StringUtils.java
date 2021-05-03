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

package co.uk.isxander.xanderlib.utils;

import co.uk.isxander.evergreenhud.compatability.universal.impl.UFontRenderer;
import com.google.common.base.Joiner;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StringUtils {

    public String shiftString(String s, int amt) {
        List<Character> chars = new ArrayList<>();
        for (char c : s.toCharArray())
            chars.add(c);

        for (int i = 0; i < amt; i++) {
            Character c = chars.get(chars.size() - 1);
            chars.remove(chars.size() - 1);
            chars.add(0, c);
        }

        return Joiner.on("").join(chars);
    }

    public static String formatBedwarsStar(int stars) {
        if (stars < 100)
            return EnumChatFormatting.GRAY + "[" + stars + "\u272B]";
        else if (stars < 200)
            return EnumChatFormatting.WHITE + "[" + stars + "\u272B]";
        else if (stars < 300)
            return EnumChatFormatting.GOLD + "[" + stars + "\u272B]";
        else if (stars < 400)
            return EnumChatFormatting.AQUA + "[" + stars + "\u272B]";
        else if (stars < 500)
            return EnumChatFormatting.DARK_GREEN + "[" + stars + "\u272B]";
        else if (stars < 600)
            return EnumChatFormatting.DARK_AQUA + "[" + stars + "\u272B]";
        else if (stars < 700)
            return EnumChatFormatting.DARK_RED + "[" + stars + "\u272B]";
        else if (stars < 800)
            return EnumChatFormatting.LIGHT_PURPLE + "[" + stars + "\u272B]";
        else if (stars < 900)
            return EnumChatFormatting.BLUE + "[" + stars + "\u272B]";
        else if (stars < 1000)
            return EnumChatFormatting.DARK_PURPLE + "[" + stars + "\u272B]";
        else {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            boolean forwards = true;
            String colors = "c6eabd5";
            for (char c : ("[" + stars + "\u272B]").toCharArray()) {
                sb.append("\u00a7").append(colors.toCharArray()[i]).append(c);

                if (i >= colors.length() - 1)
                    forwards = false;
                else if (i < 1)
                    forwards = true;

                if (forwards)
                    i++;
                else
                    i--;
            }
            return sb.toString();
        }
    }

    public static int hasDuplicateCharacters(String str, int times) {
        int repeat = 0;
        int index = -1;
        Character lastChar = null;
        for (char c : str.toCharArray()) {
            index++;

            if (lastChar == null) {
                lastChar = c;
                continue;
            }

            if (lastChar == c) {
                repeat++;
            } else {
                repeat = 0;
            }

            if (repeat >= times) {
                return index;
            }

            lastChar = c;
        }

        return -1;
    }

    public static String wrapText(String text, UFontRenderer fontRenderer, int lineWidth, String split) {
        // split with line ending too
        String[] words = text.split("(" + split + "|\n)");
        // current line width
        int lineLength = 0;
        // string concatenation in loop is bad
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // add a the word splitter after the word every time except the last word
            if (i != words.length - 1) {
                word += split;
            }

            // length of next word
            int wordLength = fontRenderer.stringWidth(word);

            if (lineLength + wordLength <= lineWidth) { // if the current line length plus this next word is less than the maximum line width
                // if the condition is met, we can just append the word to the current line as it is small enough
                output.append(word);
                lineLength += wordLength;
            } else if (wordLength <= lineWidth) { // the word is not big enough to be larger than the whole line max width
                // make a new line before adding the word
                output.append("\n").append(word);
                // the next line has just been made and has been populated with only one word. reset line length and add the word we just added
                lineLength = wordLength;
            } else {
                // the single word will not fit so run the function again with just this word
                // and tell it that every character is it's own word
                output.append(wrapText(word, fontRenderer, lineWidth, "")).append(split);
            }
        }

        return output.toString();
    }

    public static List<String> wrapTextLines(String text, UFontRenderer fontRenderer, int lineWidth, String split) {
        String wrapped = wrapText(text, fontRenderer, lineWidth, split);
        if (wrapped.equals("")) {
            return new ArrayList<>();
        }

        return Arrays.asList(wrapped.split("\n"));
    }

    public static int count(String text, String toCheck) {
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.substring(i).startsWith(toCheck)) {
                count++;
                i += toCheck.length() - 1;
            }
        }

        return count;
    }

    public static String firstUpper(String original) {
        if (original.length() == 1) return original.toUpperCase();
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

}
