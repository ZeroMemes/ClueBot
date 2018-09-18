package cluebot.util;

import java.awt.*;

/**
 * @author Brady
 * @since 12/25/2017 1:28 PM
 */
public final class UIHelper {

    public static void openWindow(Window window) {
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
