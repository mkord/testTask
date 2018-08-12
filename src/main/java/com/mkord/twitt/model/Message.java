package com.mkord.twitt.model;

import java.util.Date;

public class Message implements Comparable<Message> {
    private final String user;
    private final String message;
    private final Date postingDate;

    public Message(String user, String message, Date postingDate) {
        this.user = user;
        this.message = message;
        this.postingDate = postingDate;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!user.equals(message1.user)) return false;
        if (!message.equals(message1.message)) return false;
        return postingDate.equals(message1.postingDate);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + postingDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", postingDate=" + postingDate +
                '}';
    }

    @Override
    public int compareTo(Message m) {
        return m.getPostingDate().compareTo(getPostingDate());
    }
}
