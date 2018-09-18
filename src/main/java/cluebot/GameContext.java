package cluebot;

import cluebot.game.Card;
import cluebot.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Brady
 * @since 12/23/2017 11:02 PM
 */
public final class GameContext {

    /**
     * The local player
     */
    private final Player localPlayer;

    /**
     * All players excluding the local player
     */
    private final Player[] otherPlayers;

    /**
     * All players including the local player
     */
    private final Player[] allPlayers;

    GameContext(Player localPlayer, Player[] otherPlayers) {
        this.localPlayer = localPlayer;
        this.otherPlayers = otherPlayers;
        this.allPlayers = new Player[otherPlayers.length + 1];
        {
            allPlayers[0] = localPlayer;
            System.arraycopy(otherPlayers, 0, allPlayers, 1, otherPlayers.length);
        }
    }

    /**
     * @return The local player
     */
    public final Player getLocalPlayer() {
        return this.localPlayer;
    }

    /**
     * @return All players excluding the local player
     */
    public final Player[] getOtherPlayers() {
        return this.otherPlayers;
    }

    /**
     * @return All players including the local player
     */
    public final Player[] getAllPlayers() {
        return this.allPlayers;
    }

    /**
     * Returns the player that has the specified card, {@code null}
     * if there is not a player that has the card for certain.
     *
     * @param card The card
     * @return The player with the specified card, {@code null} if unknown.
     */
    public final Player findPlayerWithCard(Card card) {
        return Arrays.stream(allPlayers).filter(p -> p.hasCard(card)).findFirst().orElse(null);
    }

    /**
     * Returns a list of the players that could have the specified card.
     *
     * @see Player#couldHaveCard(GameContext, Card)
     *
     * @param card The card
     * @return The list of players that could have the card
     */
    public final List<Player> findPlayersThatCouldHaveCard(Card card) {
        return Arrays.stream(allPlayers).filter(p -> p.couldHaveCard(this, card)).collect(Collectors.toList());
    }

    /**
     * Finds all players that were passed when going from one player (start) to another (end).
     * <p>
     * Given the player list is as follows: [p1, p2, p3, p4, p5, p6]
     * <blockquote><pre>
     *     getPlayersInRange(p1, p1) -> [p2, p3, p4, p5, p6]
     *     getPlayersInRange(p1, p6) -> [p2, p3, p4, p5]
     *     getPlayersInRange(p5, p2) -> [p6, p1]
     * </pre></blockquote>
     *
     * @param pStart The starting player
     * @param pEnd The ending player
     * @return The players in-between
     */
    public final List<Player> getPassedPlayers(Player pStart, Player pEnd) {
        List<Player> previous = new ArrayList<>();

        List<Player> players = Arrays.asList(allPlayers);
        int start = players.indexOf(pStart);
        int end = players.indexOf(pEnd);

        // This prevents the bad code from messing up
        if (end - start == 1)
            return previous;

        boolean secondPass = true;

        // Do the first loop from the start player to the end index
        for (int i = start + 1; i < players.size(); i++) {
            if (end > start && i == end) {
                secondPass = false;
                break;
            }
            previous.add(players.get(i));
        }

        // Do the second loop from the start index to the start player
        if (secondPass) {
            for (int i = 0; i < end; i++) {
                previous.add(players.get(i));
            }
        }

        return previous;
    }
}
