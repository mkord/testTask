package com.mkord.twitt.event;

public class FollowRequestEvent {

    private final String userRequester;
    private final String userWillBeFollowed;

    public FollowRequestEvent(String userRequester, String userWillBeFollowed) {
        this.userRequester = userRequester;
        this.userWillBeFollowed = userWillBeFollowed;
    }

    public String getUserRequester() {
        return userRequester;
    }

    public String getUserWillBeFollowed() {
        return userWillBeFollowed;
    }

    @Override
    public String toString() {
        return "FollowRequestEvent{" +
                "userRequester='" + userRequester + '\'' +
                ", userWillBeFollowed='" + userWillBeFollowed + '\'' +
                '}';
    }
}
