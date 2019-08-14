package com.ajudi46.ngomanaged;

public class EventInfo {
    public String event_name;
    public String email_id;
    public String username;

    public EventInfo(String event_name, String email_id, String username) {
        this.event_name = event_name;
        this.email_id = email_id;
        this.username = username;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getUsername() {
        return username;
    }
}
