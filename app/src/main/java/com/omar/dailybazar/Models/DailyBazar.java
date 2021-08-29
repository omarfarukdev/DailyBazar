package com.omar.dailybazar.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DailyBazar {

    
    private List<String> date=null;

    private List<Bazar> bazars=null;

    public DailyBazar() {
    }

    public DailyBazar(List<String> date) {
        this.date = date;
    }

    public DailyBazar(List<String> date, List<Bazar> bazars) {
        this.date = date;
        this.bazars = bazars;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Bazar> getBazars() {
        return bazars;
    }

    public void setBazars(List<Bazar> bazars) {
        this.bazars = bazars;
    }
}
