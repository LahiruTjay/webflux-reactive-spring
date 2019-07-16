package com.example.reactive.model;

import java.util.Date;

public class WebFluxEvent {

    private long id;
    private Date date;

    public WebFluxEvent(long id, Date date) {
        super();
        this.id = id;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
