package cluebot.game;

/**
 * A case
 *
 * @author Brady
 * @since 12/24/2017 9:49 AM
 */
public final class Case {

    /**
     * The suspect
     */
    private Card suspect;

    /**
     * The weapon
     */
    private Card weapon;

    /**
     * The room
     */
    private Card room;

    public Case() {
        this.suspect = Card.UNKNOWN;
        this.weapon = Card.UNKNOWN;
        this.room = Card.UNKNOWN;
    }

    public Case(Card suspect, Card weapon, Card room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    /**
     * Sets the specified card to either the suspect, room
     * or weapon for this case, based on the {@code Type}
     * of the card.
     *
     * @param card The card
     */
    public final void set(Card card) {
        switch (card.getType()) {
            case SUSPECT:
                this.suspect = card;
                break;
            case WEAPON:
                this.weapon = card;
                break;
            case ROOM:
                this.room = card;
                break;
        }
    }

    /**
     * Sets the suspect
     *
     * @param suspect The new suspect
     */
    public final void setSuspect(Card suspect) {
        this.suspect = suspect;
    }

    /**
     * Sets the weapon
     *
     * @param weapon The new weapon
     */
    public final void setWeapon(Card weapon) {
        this.weapon = weapon;
    }

    /**
     * Sets the room
     *
     * @param room The new room
     */
    public final void setRoom(Card room) {
        this.room = room;
    }

    /**
     * @return The suspect
     */
    public final Card getSuspect() {
        return suspect;
    }

    /**
     * @return The weapon
     */
    public final Card getWeapon() {
        return weapon;
    }

    /**
     * @return The room
     */
    public final Card getRoom() {
        return room;
    }
}
