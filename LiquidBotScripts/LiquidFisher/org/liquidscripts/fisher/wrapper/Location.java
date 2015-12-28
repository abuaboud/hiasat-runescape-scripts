package org.liquidscripts.fisher.wrapper;

import org.liquid.automation.osrs.api.wrapper.Area;
import org.liquid.automation.osrs.api.wrapper.Path;
import org.liquid.automation.osrs.api.wrapper.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 8/26/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Location {

    Path tiles_to_bank;
    Path tiles_to_fish;
    Area bank_area;
    String name;
    Fish[] fishes;

    public Location(final Path tiles_to_fish,final Path tiles_to_bank, final Area bank_area, String name,Fish[] fishes) {
        this.tiles_to_bank = tiles_to_bank;
        this.tiles_to_fish = tiles_to_fish;
        this.bank_area = bank_area;
        this.name = name;
        this.fishes = fishes;
    }

    public Location(final Area bank_area, String name,Fish[] fishes) {
        this.tiles_to_bank = null;
        this.tiles_to_fish = null;
        this.bank_area = bank_area;
        this.name = name;
        this.fishes = fishes;
    }
    public String getName() {
        return name;
    }

    public Path getTilesToBank() {
        return tiles_to_bank;
    }

    public Path getTilesToFish() {
        return tiles_to_fish;
    }

    public Area getBankArea() {
        return bank_area;
    }

    public Fish[] getFishes(){
        return fishes;
    }
}
