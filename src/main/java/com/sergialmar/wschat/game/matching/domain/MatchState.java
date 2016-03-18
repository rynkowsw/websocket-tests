package com.sergialmar.wschat.game.matching.domain;

import lombok.Data;

import java.util.Optional;

/**
 * Created by Wojciech on 2016-03-16.
 */

@Data
public class MatchState {

    public MatchState(String user1, String user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public Optional<String> getSecondLogin(String login)
    {
        if(user1.equals(login))
            return Optional.of(user2);

        if(user2.equals(login))
            return Optional.of(user1);


        return Optional.ofNullable( null);
    }

    private String user1;
    private String user2;
}
