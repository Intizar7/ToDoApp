package com.example.todoapplication;

public class ToDo {
    private String Title;
    private String Explanation;
    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public ToDo(String title, String explanation, User user) {
        Title = title;
        Explanation = explanation;
        this.user = user;
    }
}
