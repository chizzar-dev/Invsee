package com.metox.invsee;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class InvSeeHolder implements InventoryHolder {

    private final UUID target;
    private final String targetName;

    private Inventory inventory;

    public InvSeeHolder(UUID target, String targetName) {
        this.target = target;
        this.targetName = targetName;
    }

    public UUID getTarget() { return target; }

    public String getTargetName() { return targetName; }

    void setInventory(Inventory inv) { this.inventory = inv; }

    // chizzar-dev
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
