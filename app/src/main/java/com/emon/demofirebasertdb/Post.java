package com.emon.demofirebasertdb;

public class Post {
   private String name,email,uid;

    public Post() {
    }

    public Post(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid=uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
