package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<Cheese> chesses;

    public Menu() { }

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Cheese> getChesses() {
        return chesses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(Cheese item) {
        chesses.add(item);
    }
}
