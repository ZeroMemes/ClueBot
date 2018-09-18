package cluebot.game;

import cluebot.GameContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Brady
 * @since 12/23/2017 10:49 PM
 */
public final class Player {

    /**
     * The name of this player
     */
    private final String name;

    /**
     * The deck this player holds
     */
    private final Deck deck;

    /**
     * The cards that this player does not have
     */
    private final List<Card> notOwnedCards;

    /**
     * The possibilities of this player having certain cards
     */
    private final Map<Card, Integer> possibilityMap;

    /**
     * Used to Keep track of when suggestions provide a possibility
     * for a specified card type. When reviewing past suggestions,
     * this ensures that the possibility isn't incremented again, giving
     * exponentially high numbers.
     */
    private final Map<Card.Type, Map<Suggestion, Boolean>> suggestionProvide;

    public Player(String name, int cards) {
        this.name = name;
        this.deck = new Deck(cards);
        this.notOwnedCards = new ArrayList<>();
        this.possibilityMap = new LinkedHashMap<>();
        this.suggestionProvide = new HashMap<>();
    }

    /**
     * Adds the possibility for the specified card based on the specified suggestion.
     *
     * @param suggestion The suggestion
     * @param card The card
     */
    public final void addPossibility(Suggestion suggestion, Card card) {
        // Ensure there is a Map created for the specified card's type
        this.suggestionProvide.computeIfAbsent(card.getType(), type -> new HashMap<>());

        // If the suggestion has already added a possibility for the same type, ignore this action
        if (this.suggestionProvide.get(card.getType()).computeIfAbsent(suggestion, s -> false))
            return;

        // Increment the possibility;
        this.possibilityMap.put(card, this.possibilityMap.computeIfAbsent(card, i -> 0) + 1);

        // Mark the suggestion as already used for the specified type
        this.suggestionProvide.get(card.getType()).put(suggestion, true);
    }

    /**
     * Returns the possibility that this player has the
     * specified card. If there is no known possibility,
     * then {@code -1} will be returned.
     *
     * @param card The card
     * @return The possibility of the specified card, {@code -1} if undefined.
     */
    public final int getPossibility(Card card) {
        Integer i = possibilityMap.get(card);
        return i == null ? -1 : i;
    }

    /**
     * Cleans possibilities that have been confirmed to be {@code true} or {@code false}.
     * Single parameter is a function that takes in a card and returns a player with that
     * card, {@code null} if there isn't a player known to have the card.
     */
    public final void cleanPossibilities(GameContext context) {
        this.possibilityMap.forEach((card, possibility) -> {
            if (context.findPlayerWithCard(card) != null || !this.couldHaveCard(context, card)) {
                this.possibilityMap.remove(card);
            }
        });
    }

    /**
     * Marks multiple cards as not being owned by this player
     *
     * @param cards The cards
     */
    public final void addNotOwned(Card... cards) {
        Arrays.stream(cards).forEach(this::addNotOwned);
    }

    /**
     * Marks a card as not being owned by this player
     *
     * @param card The card
     */
    public final void addNotOwned(Card card) {
        if (!this.notOwnedCards.contains(card))
            this.notOwnedCards.add(card);
    }

    /**
     * Returns whether or not this player has the specified card. Will
     * only return answers that are 100% certain given the information
     * that has been provided to the bot.
     *
     * @param card The card
     * @return Whether or not this player has the card
     */
    public final boolean hasCard(Card card) {
        return !this.notOwnedCards.contains(card) && this.deck.hasCard(card);
    }

    /**
     * Returns whether or not this player could have the specified card.
     * A player may be able to have the card if their entire deck isn't
     * known, it has been confirmed that they do not have the specified
     * card, or there are players that already have all of the other cards
     * in the set of cards in the specified card's category.
     *
     * @param context The game context associated with this player
     * @param card The card
     * @return Whether or not this player could have the card
     */
    public final boolean couldHaveCard(GameContext context, Card card) {
        // Get all of the cards of the same type as the specified card that do not have an owner
        List<Card> cards = Card.ofType(card.getType()).stream().filter(c -> context.findPlayerWithCard(c) == null).collect(Collectors.toList());
        return (cards.size() != 1 || cards.get(0) != card) && !this.notOwnedCards.contains(card) && (!this.deck.isComplete() || this.deck.hasCard(card));

    }

    /**
     * @return The name of this player
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @return The deck of this player
     */
    public final Deck getDeck() {
        return this.deck;
    }

    /**
     * @return A map of cards and their possibility
     */
    public final Map<Card, Integer> getPossibleCards() {
        return this.possibilityMap;
    }

    /**
     * @return A map of cards that this player does not have
     */
    public final List<Card> getNotOwnedCards() {
        return this.notOwnedCards;
    }
}
