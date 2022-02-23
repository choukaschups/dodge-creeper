package me.choukas.dodgecreeper.core.api.game.join.state;

import me.choukas.dodgecreeper.api.game.GameState;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class JoinStateProvider {

    private final Map<GameState, JoinState> gameStates;

    @Inject
    public JoinStateProvider(JoinWaitingState waitingState,
                             JoinRunningState runningState,
                             JoinFinishState finishState) {
        this.gameStates = new HashMap<>();
        this.gameStates.put(GameState.WAITING, waitingState);
        this.gameStates.put(GameState.RUNNING, runningState);
        this.gameStates.put(GameState.FINISH, finishState);
    }

    public JoinState provide(GameState state) {
        return this.gameStates.get(state);
    }
}
