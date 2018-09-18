package cluebot.game;

/**
 * @author Brady
 * @since 12/24/2017 1:58 PM
 */
public final class Suggestion {

    /**
     * The player that made the suggestion
     */
    private final Player suggester;

    /**
     * The player that revealed a card to the suggester. Will
     * be {@code null} if the {@code type} is {@code PASS}.
     */
    private final Player revealer;

    /**
     * The case provided in the suggestion
     */
    private final Case theCase;

    /**
     * The type of suggestion.
     */
    private final Type type;

    public Suggestion(Player suggester, Card suspect, Card weapon, Card room) {
        this.suggester = suggester;
        this.revealer = null;
        this.theCase = new Case(suspect, weapon, room);
        this.type = Type.PASS;
    }

    public Suggestion(Player suggester, Player revealer, Card suspect, Card weapon, Card room) {
        this.suggester = suggester;
        this.revealer = revealer;
        this.theCase = new Case(suspect, weapon, room);
        this.type = Type.REVEAL;
    }

    /**
     * @return The player that made the suggestion
     */
    public final Player getSuggester() {
        return this.suggester;
    }

    /**
     * @return The player that revealed a card to the suggestion. {@code null} if none.
     */
    public final Player getRevealer() {
        return this.revealer;
    }

    /**
     * @return The case provided in the suggestion
     */
    public final Case getCase() {
        return this.theCase;
    }

    /**
     * @return The type of suggestion
     */
    public final Type getType() {
        return this.type;
    }

    /**
     * The type of suggestion
     */
    public enum Type {

        /**
         * Some player revealed one of the cards involved in
         * the case to the player giving the suggestion.
         */
        REVEAL,

        /**
         * No players revealed one of the cards involved in
         * the case to the player giving the suggestion.
         */
        PASS
    }
}
