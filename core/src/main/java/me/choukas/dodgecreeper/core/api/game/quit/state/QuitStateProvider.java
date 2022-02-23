package me.choukas.dodgecreeper.core.api.game.quit.state;

import me.choukas.dodgecreeper.api.game.GameState;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class QuitStateProvider {

    private final Map<GameState, QuitState> gameStates;

    @Inject
    public QuitStateProvider(QuitWaitingState waitingState,
                             QuitRunningState runningState,
                             QuitFinishState finishState) {
        this.gameStates = new HashMap<>();
        this.gameStates.put(GameState.WAITING, waitingState);
        this.gameStates.put(GameState.RUNNING, runningState);
        this.gameStates.put(GameState.FINISH, finishState);
    }

    public QuitState provide(GameState state) {
        return this.gameStates.get(state);
    }
}
