package com.sergialmar.wschat.game.matching.repository;

import com.sergialmar.wschat.game.matching.domain.MatchState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Wojciech on 2016-03-16.
 */
public class ActiveMathRepository {
    private Map<String, MatchState> activeMatch = new ConcurrentHashMap<>();
    private Map<String, MatchState> activeMatch2 = new ConcurrentHashMap<>();

    public void add(String user1, String user2) {
        MatchState pair = new MatchState(user1, user2);
        activeMatch.put(user1, pair);
        activeMatch2.put(user2, pair);
    }

    public String getUserForUser(String login) {

        MatchState state = getState(login);


    }

    private MatchState getState(String login) {
        MatchState state = activeMatch.get(login);
        if (state != null)
            return state;

        state = activeMatch2.get(login);

        return state;
    }


}
