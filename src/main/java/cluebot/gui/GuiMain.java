package cluebot.gui;

import cluebot.ClueBot;
import cluebot.GameContext;
import cluebot.game.Card;
import cluebot.game.Player;
import cluebot.util.TableHelper;
import cluebot.util.UIHelper;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Brady
 * @since 12/24/2017 8:26 PM
 */
public final class GuiMain {

    public JPanel contentPane;

    private JTable playerTable;
    public JTextField caseSuspect;
    public JTextField caseWeapon;
    public JTextField caseRoom;
    private JButton newSuggestion;
    private JButton viewSuggestionsButton;
    private JButton myInfoButton;
    private JButton cardButton;

    public GuiMain(ClueBot bot) {
        $$$setupUI$$$();
        newSuggestion.addActionListener(e -> {
            SuggestionDialog dialog = new SuggestionDialog(bot.getContext(), bot::handleSuggestion);
            UIHelper.openWindow(dialog);
        });

        JPopupMenu playerPopupMenu = new JPopupMenu();
        JMenuItem playerViewInfo = new JMenuItem("View Info");
        playerViewInfo.addActionListener(e -> {
            Player player = bot.getContext().getAllPlayers()[playerTable.getSelectedRow()];
            PlayerInfoDialog dialog = new PlayerInfoDialog(bot, player);
            UIHelper.openWindow(dialog);
        });
        playerPopupMenu.add(playerViewInfo);
        playerPopupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    int rowAtPoint = playerTable.rowAtPoint(SwingUtilities.convertPoint(playerPopupMenu, new Point(0, 0), playerTable));
                    if (rowAtPoint > -1) {
                        playerTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        playerTable.setComponentPopupMenu(playerPopupMenu);

        viewSuggestionsButton.addActionListener(e -> {
            SuggestionHistoryDialog dialog = new SuggestionHistoryDialog(bot);
            UIHelper.openWindow(dialog);
        });

        myInfoButton.addActionListener(e -> {
            PlayerInfoDialog dialog = new PlayerInfoDialog(bot, bot.getContext().getLocalPlayer());
            UIHelper.openWindow(dialog);
        });

        cardButton.addActionListener(e -> {
            CardTableDialog dialog = new CardTableDialog(bot);
            UIHelper.openWindow(dialog);
        });

        update(bot);
    }

    public final Card getRevealedCard() {
        Card[] cache = new Card[1];
        RevealedCardDialog dialog = new RevealedCardDialog(card -> cache[0] = card);
        dialog.setTitle("Enter the revealed card");
        UIHelper.openWindow(dialog);
        return cache[0];
    }

    public final void update(ClueBot bot) {
        DefaultTableModel playerTableModel = (DefaultTableModel) (playerTable.getModel());
        TableHelper.removeAllRows(playerTableModel);
        TableHelper.addAllRows(playerTableModel, bot.getContext().getAllPlayers(), player -> new Object[]{
                player.getName(),
                player.getDeck().getSize()
        });

        caseSuspect.setText(bot.getCase().getSuspect().toString());
        caseWeapon.setText(bot.getCase().getWeapon().toString());
        caseRoom.setText(bot.getCase().getRoom().toString());
    }

    private void createUIComponents() {
        playerTable = new JTable(new DefaultTableModel(new Object[]{"Name", "Cards"}, 0));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(9, 4, new Insets(10, 10, 10, 10), -1, -1));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(5, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Players");
        contentPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Case");
        contentPane.add(label2, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Suspect");
        contentPane.add(label3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        caseSuspect = new JTextField();
        caseSuspect.setEditable(false);
        contentPane.add(caseSuspect, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        caseWeapon = new JTextField();
        caseWeapon.setEditable(false);
        caseWeapon.setText("");
        contentPane.add(caseWeapon, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        caseRoom = new JTextField();
        caseRoom.setEditable(false);
        caseRoom.setText("");
        contentPane.add(caseRoom, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Weapon");
        contentPane.add(label4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Room");
        contentPane.add(label5, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newSuggestion = new JButton();
        newSuggestion.setText("Add Suggestion");
        contentPane.add(newSuggestion, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new GridConstraints(1, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(250, 150), null, 0, false));
        scrollPane1.setViewportView(playerTable);
        viewSuggestionsButton = new JButton();
        viewSuggestionsButton.setText("View Suggestions");
        contentPane.add(viewSuggestionsButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        myInfoButton = new JButton();
        myInfoButton.setText("My Info");
        contentPane.add(myInfoButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cardButton = new JButton();
        cardButton.setText("Card Table");
        contentPane.add(cardButton, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
