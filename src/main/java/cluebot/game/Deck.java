package cluebot.game;

import java.util.Arrays;

/**
 * A player's deck.
 *
 * @author Brady
 * @since 12/23/2017 10:55 PM
 */
public final class Deck {

    /**
     * The cards in the deck
     */
    private final Card[] cards;

    Deck(int size) {
        this.cards = new Card[size];

        // Populate array with unknown cards
        for (int i = 0; i < size; i++) {
            this.cards[i] = Card.UNKNOWN;
        }
    }

    /**
     * Replaces the first UNKNOWN card in the deck with the specified card
     * if there is an UNKNOWN card available and the deck doesn't already have
     * the same card.
     *
     * @param card The card being confirmed
     * @return Whether or not the card was inserted into the deck
     */
    public final boolean confirm(Card card) {
        if (hasCard(card))
            return false;

        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == Card.UNKNOWN) {
                cards[i] = card;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether or not this deck has the specified card
     *
     * @param card The card
     * @return Whether or not this deck has the specified card
     */
    public final boolean hasCard(Card card) {
        return Arrays.stream(cards).anyMatch(c -> c == card);
    }

    /**
     * @return The size of this deck
     */
    public final int getSize() {
        return this.cards.length;
    }

    /**
     * @return The cards in this deck
     */
    public final Card[] getCards() {
        return this.cards;
    }

    /**
     * @return The confirmed cards in this deck
     */
    public final Card[] getConfirmedCards() {
        return Arrays.stream(cards).filter(card -> card != Card.UNKNOWN).toArray(Card[]::new);
    }

    /**
     * @return Whether or not this deck contains no unknown cards.
     */
    public final boolean isComplete() {
        return Arrays.stream(cards).noneMatch(card -> card == Card.UNKNOWN);
    }
}
