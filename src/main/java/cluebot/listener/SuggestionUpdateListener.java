package cluebot.listener;

import cluebot.game.Suggestion;

import java.util.List;

/**
 * Called when the list of suggestions updates
 *
 * @author Brady
 * @since 12/26/2017 12:28 PM
 */
public interface SuggestionUpdateListener {

    void accept(List<Suggestion> suggestions);
}
