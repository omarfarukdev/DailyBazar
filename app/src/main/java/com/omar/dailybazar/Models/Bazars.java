package com.omar.dailybazar.Models;

import java.util.List;

public class Bazars {
    private List<Bazar> bazars=null;

    public Bazars(List<Bazar> bazars) {
        this.bazars = bazars;
    }

    public List<Bazar> getBazars() {
        return bazars;
    }

    public void setBazars(List<Bazar> bazars) {
        this.bazars = bazars;
    }
}
