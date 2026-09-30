package com.metox.invsee;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InvListener implements Listener {

    private final InvSee plugin;
    private final InvView view;

    public InvListener(InvSee plugin, InvView view) {
        this.plugin = plugin;
        this.view = view;
    }

    // chizzar-dev
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory top = e.getInventory();
        if (top == null || !(top.getHolder() instanceof InvSeeHolder)) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        if (!view.allowEdit()) {
            e.setCancelled(true);
            return;
        }

        if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            e.setCancelled(true);
            return;
        }

        int raw = e.getRawSlot();
        if (raw < 0 || raw >= top.getSize()) {
            return;
        }

        if (!InvView.editable(raw)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Inventory top = e.getInventory();
        if (top == null || !(top.getHolder() instanceof InvSeeHolder)) return;

        if (!view.allowEdit()) {
            e.setCancelled(true);
            return;
        }

        int size = top.getSize();
        for (int raw : e.getRawSlots()) {
            if (raw < size && !InvView.editable(raw)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (inv == null || !(inv.getHolder() instanceof InvSeeHolder)) return;

        boolean saved = view.sync(inv);
        if (!saved && view.allowEdit() && e.getPlayer() instanceof Player) {
            InvSeeHolder holder = (InvSeeHolder) inv.getHolder();
            plugin.send((Player) e.getPlayer(), "offline-nosave", "%player%", holder.getTargetName());
        }
    }
}
