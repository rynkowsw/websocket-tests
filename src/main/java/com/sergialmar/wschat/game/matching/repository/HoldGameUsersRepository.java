package com.sergialmar.wschat.game.matching.repository;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2016-03-16.
 */
public class HoldGameUsersRepository {
    private ArrayList<String> userList = new ArrayList<>();

    public void add(String user) {
        userList.add(user);
    }

    public Boolean checkIfThereIsUser(){
        return userList.isEmpty();
    }

    public String get(){
        String object = userList.iterator().next();
        userList.remove(object);
        return object;
    }
}
