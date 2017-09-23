package com.postbox.model;

import javax.persistence.*;

@Entity
@Table(name = "incoming_requests")
public class IncomingRequest {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(length=10000)
    String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
