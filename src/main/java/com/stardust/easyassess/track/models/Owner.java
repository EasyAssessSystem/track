package com.stardust.easyassess.track.models;

public class Owner extends DataModel {
    private String id;
    private String name;

    public Owner() {

    }

    public Owner(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Owner)) return false;
        if (((Owner) o).getId().equals(this.getId())) return true;
        return false;
    }
}
