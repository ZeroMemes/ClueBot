package cluebot;

import cluebot.game.Player;
import cluebot.gui.AddPlayerDialog;
import cluebot.gui.PlayerCountDialog;
import cluebot.util.UIHelper;

import javax.swing.*;

/**
 * @author Brady
 * @since 12/23/2017 11:01 PM
 */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Integer[] playerCount = new Integer[1];
        PlayerCountDialog dialog = new PlayerCountDialog(card -> playerCount[0] = card);
        dialog.setTitle("Enter the player count");
        UIHelper.openWindow(dialog);

        if (playerCount[0] == null) {
            System.exit(0);
        }

        Player[] players = new Player[playerCount[0]];
        for (int i = 0; i < players.length; i++) {
            int playerNum = i + 1;

            Player[] playerCache = new Player[1];
            AddPlayerDialog addDialog = new AddPlayerDialog(player -> playerCache[0] = player);
            addDialog.setTitle("Enter player #" + playerNum + (i == 0 ? " (You)" : ""));
            UIHelper.openWindow(addDialog);

            if (playerCache[0] == null) {
                System.exit(0);
            } else{
                players[i] = playerCache[0];
            }
        }

        Player localPlayer = players[0];
        Player[] otherPlayers = new Player[players.length - 1];
        System.arraycopy(players, 1, otherPlayers, 0, players.length - 1);

        GameContext context = new GameContext(localPlayer, otherPlayers);

        new ClueBot(context).start();
    }
}
