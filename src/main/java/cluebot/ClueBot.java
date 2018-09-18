package cluebot;

import cluebot.game.*;
import cluebot.gui.GuiMain;
import cluebot.listener.SuggestionUpdateListener;
import cluebot.util.UIHelper;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Brady
 * @since 12/23/2017 11:02 PM
 */
public final class ClueBot {

    /**
     * Context of the game being analyzed
     */
    private final GameContext context;

    /**
     * The confirmed case
     */
    private final Case theCase;

    /**
     * A history of all the suggestions
     */
    private final LinkedList<Suggestion> suggestions;

    /**
     * Maps suggestions to a card. Used to keep track of the
     * suggestions where a card was revealed to the local player.
     */
    private final Map<Suggestion, Card> revealedToLocalPlayer;

    /**
     * The GUI
     */
    private final GuiMain gui;

    /**
     * List of {@code SuggestionUpdateListeners} listening for suggestion updates
     */
    private final List<SuggestionUpdateListener> suggestionUpdateListeners;

    ClueBot(GameContext context) {
        this.context = context;
        this.theCase = new Case();
        this.suggestions = new LinkedList<>();
        this.revealedToLocalPlayer = new LinkedHashMap<>();
        this.suggestionUpdateListeners = new ArrayList<>();

        this.gui = new GuiMain(this);

        // Review the case from the start just in case
        findCase();
    }

    final void start() {
        JFrame frame = new JFrame("Gui");
        frame.setTitle("ClueBot (TM)");
        frame.setContentPane(gui.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIHelper.openWindow(frame);
    }

    /**
     * Runs through the review process for the specified suggestion.
     *
     * @param suggestion The suggestion
     */
    public final void handleSuggestion(Suggestion suggestion) {
        // Review the suggestion that was made
        reviewSuggestion(suggestion);

        // Review the past suggestions in the case of new things being figured out
        this.suggestions.forEach(this::reviewSuggestion);

        // Re-review the suggestion in the case of the review of past suggestions figured things out
        reviewSuggestion(suggestion);

        // Add the suggestion
        this.suggestions.add(suggestion);

        // Call all of the update listeners
        this.suggestionUpdateListeners.forEach(l -> l.accept(this.suggestions));

        dumpPlayerInfo();
        findCase();
    }

    /**
     * @return The context this bot is using
     */
    public final GameContext getContext() {
        return this.context;
    }

    /**
     * @return The suggestions that have been given to the bot
     */
    public final LinkedList<Suggestion> getSuggestions() {
        return this.suggestions;
    }

    /**
     * Adds a {@code SuggestionUpdateListener} to this bot.
     *
     * @param listener The listeners
     */
    public final void addSuggestionUpdateListener(SuggestionUpdateListener listener) {
        this.suggestionUpdateListeners.add(listener);
    }

    /**
     * @return The confidential case
     */
    public final Case getCase() {
        return this.theCase;
    }

    /**
     * Reviews the specified suggestion, redirects to the proper review method.
     *
     * @param suggestion The suggestion
     */
    private void reviewSuggestion(Suggestion suggestion) {
        switch (suggestion.getType()) {
            case PASS:
                reviewPassSuggestion(suggestion);
                break;
            case REVEAL:
                reviewRevealSuggestion(suggestion);
                break;
        }
    }

    /**
     * Reviews a "PASS" type suggestion.
     *
     * @param suggestion The suggestion
     */
    private void reviewPassSuggestion(Suggestion suggestion) {
        final Player suggester = suggestion.getSuggester();
        final Card suspect = suggestion.getCase().getSuspect();
        final Card weapon = suggestion.getCase().getWeapon();
        final Card room = suggestion.getCase().getRoom();

        if (suggester == context.getLocalPlayer()) { // If the pass is from a suggestion by the local player

            // If we don't have the suspect card, it's the case suspect
            if (!suggester.getDeck().hasCard(suspect)) {
                this.theCase.setSuspect(suspect);
            }

            // If we don't have the weapon card, it's the case weapon
            if (!suggester.getDeck().hasCard(weapon)) {
                this.theCase.setWeapon(weapon);
            }

            // If we don't have the room card, it's the case room
            if (!suggester.getDeck().hasCard(room)) {
                this.theCase.setRoom(room);
            }

        } else { // If the pass is from a suggestion by another player

            // If there isn't another player with the suspect card, and the suspect isn't the case, the suggester might have the suspect card
            if (context.findPlayerWithCard(suspect) == null && theCase.getSuspect() != suspect) {
                suggester.addPossibility(suggestion, suspect);
            }

            // If there isn't another player with the weapon card, and the weapon isn't the case, the suggester might have the weapon card
            if (context.findPlayerWithCard(weapon) == null && theCase.getWeapon() != weapon) {
                suggester.addPossibility(suggestion, weapon);
            }

            // If there isn't another player with the room card, and the room isn't the case, the suggester might have the room card
            if (context.findPlayerWithCard(room) == null && theCase.getRoom() != room) {
                suggester.addPossibility(suggestion, room);
            }
        }

        context.getPassedPlayers(suggester, suggester).forEach(p -> p.addNotOwned(suspect, weapon, room));
    }

    /**
     * Reviews a "REVEAL" type suggestion.
     *
     * @param suggestion The suggestion
     */
    private void reviewRevealSuggestion(Suggestion suggestion) {
        final Player suggester = suggestion.getSuggester();
        final Player revealer = suggestion.getRevealer();
        final Card suspect = suggestion.getCase().getSuspect();
        final Card weapon = suggestion.getCase().getWeapon();
        final Card room = suggestion.getCase().getRoom();

        if (suggester == context.getLocalPlayer()) { // If the reveal is from a suggestion by the local player

            Card revealed = this.revealedToLocalPlayer.computeIfAbsent(suggestion, s -> this.gui.getRevealedCard());

            // If we don't already know that they have the card they revealed to us, then confirm it
            if (!revealer.getDeck().hasCard(revealed)) {
                revealer.getDeck().confirm(revealed);
            }

        } else { // If the reveal is from a suggestion by another player
            Player playerWithSuspect = context.findPlayerWithCard(suspect);
            Player playerWithWeapon = context.findPlayerWithCard(weapon);
            Player playerWithRoom = context.findPlayerWithCard(room);

            // If we know of 2 other players that have the weapon and room that aren't the revealer, the revealer must have the suspect
            if (playerWithWeapon != null && playerWithRoom != null && playerWithWeapon != revealer && playerWithRoom != revealer) {
                revealer.getDeck().confirm(suspect);
            }

            // If we know of 2 other players that have the suspect and room that aren't the revealer, the revealer must have the weapon
            if (playerWithSuspect != null && playerWithRoom != null && playerWithSuspect != revealer && playerWithRoom != revealer) {
                revealer.getDeck().confirm(weapon);
            }

            // If we know of 2 other players that have the suspect and weapon that aren't the revealer, the revealer must have the room
            if (playerWithSuspect != null && playerWithWeapon != null && playerWithSuspect != revealer && playerWithWeapon != revealer) {
                revealer.getDeck().confirm(room);
            }

            // If there isn't a player that we know for certain has the suspect, the revealer might have it
            if (context.findPlayerWithCard(suspect) == null) {
                revealer.addPossibility(suggestion, suspect);
            }

            // If there isn't a player that we know for certain has the weapon, the revealer might have it
            if (context.findPlayerWithCard(weapon) == null) {
                revealer.addPossibility(suggestion, weapon);
            }

            // If there isn't a player that we know for certain has the room, the revealer might have it
            if (context.findPlayerWithCard(room) == null) {
                revealer.addPossibility(suggestion, room);
            }
        }

        context.getPassedPlayers(suggester, revealer).forEach(p -> p.addNotOwned(suspect, weapon, room));

        revealer.cleanPossibilities(context);
    }

    private void dumpPlayerInfo() {
        // Spit out the confirmed and possible cards that people have
        for (Player player : context.getOtherPlayers()) {
            System.out.println(player.getName());
            System.out.println(" Confirmed Cards");
            for (Card card : player.getDeck().getCards()) {
                if (card != Card.UNKNOWN) {
                    System.out.println("  " + card);
                }
            }
            System.out.println(" Possible Cards");
            player.getPossibleCards().forEach((card, possibility) -> System.out.println("  " + card + ": " + possibility));
        }
    }

    /**
     * Reviews the current known information to find the confidential case information
     */
    private void findCase() {
        for (Card.Type type : Card.Type.values()) {
            List<Card> withoutOwners = Card.ofType(type).stream()
                    .filter(card -> context.findPlayersThatCouldHaveCard(card).size() == 0)
                    .collect(Collectors.toList());

            if (withoutOwners.size() == 1)
                theCase.set(withoutOwners.get(0));
        }
        gui.update(this);
    }
}
