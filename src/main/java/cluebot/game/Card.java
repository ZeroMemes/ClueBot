package cluebot.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All of the cards in the game
 *
 * @author Brady
 * @since 12/23/2017 10:49 PM
 */
public enum Card {
    
    // Unknown
    UNKNOWN(Type.UNKNOWN),

    // Suspects
    MUSTARD(Type.SUSPECT),
    PLUM(Type.SUSPECT),
    GREEN(Type.SUSPECT),
    PEACOCK(Type.SUSPECT),
    SCARLET(Type.SUSPECT),
    WHITE(Type.SUSPECT),

    // Weapons
    KNIFE(Type.WEAPON),
    CANDLE(Type.WEAPON),
    REVOLVER(Type.WEAPON),
    ROPE(Type.WEAPON),
    PIPE(Type.WEAPON),
    WRENCH(Type.WEAPON),

    // Rooms
    HALL(Type.ROOM),
    LOUNGE(Type.ROOM),
    DINING(Type.ROOM),
    KITCHEN(Type.ROOM),
    BALL_ROOM(Type.ROOM),
    CONSERVATORY(Type.ROOM),
    BILLIARD(Type.ROOM),
    LIBRARY(Type.ROOM),
    STUDY(Type.ROOM);

    /**
     * The type of card
     */
    private final Type type;

    Card(Type type) {
        this.type = type;
    }

    /**
     * Returns the type of card that this card is. The type will
     * either be {@code SUSPECT}, {@code WEAPON} or {@code ROOM}.
     *
     * @see Type
     *
     * @return The type of card
     */
    public final Type getType() {
        return this.type;
    }

    /**
     * Returns a list of cards that contains
     * all of the cards of the specified type.
     *
     * @param type The type
     * @return A list of all of the cards of the specified type
     */
    public static List<Card> ofType(Type type) {
        return Arrays.stream(values()).filter(card -> card.getType() == type).collect(Collectors.toList());
    }

    /**
     * The type of card
     */
    public enum Type {
        UNKNOWN, SUSPECT, WEAPON, ROOM
    }
}
