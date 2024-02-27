/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

public class Pass {

    int id;

    int event_id;
    int client_id;
    String createdAt;

    public Pass() {
    }

    public Pass(int event_id, int client_id, String createdAt) {
        this.event_id = event_id;
        this.client_id = client_id;
        this.createdAt = createdAt;
    }

    public Pass(int id, int event_id, int client_id, String createdAt) {
        this.id = id;
        this.event_id = event_id;
        this.client_id = client_id;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Pass{" + "id=" + id + ", event_id=" + event_id + ", client_id=" + client_id + ", createdAt=" + createdAt + '}';
    }

}
