package com.mkord.twitt.model;


public class FollowRequest {
    private final String userRequester;
    private final String userBeFollowed;

    public FollowRequest(String userRequester, String userBeFollowed) {
        this.userRequester = userRequester;
        this.userBeFollowed = userBeFollowed;
    }

    public String getUserRequester() {
        return userRequester;
    }

    public String getUserBeFollowed() {
        return userBeFollowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowRequest that = (FollowRequest) o;

        if (!userRequester.equals(that.userRequester)) return false;
        return userBeFollowed.equals(that.userBeFollowed);

    }

    @Override
    public int hashCode() {
        int result = userRequester.hashCode();
        result = 31 * result + userBeFollowed.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FollowRequest{" +
                "userRequester='" + userRequester + '\'' +
                ", userBeFollowed='" + userBeFollowed + '\'' +
                '}';
    }
}
