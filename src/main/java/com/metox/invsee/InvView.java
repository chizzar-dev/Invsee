package com.metox.invsee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class InvView {

    private final InvSee plugin;

    public InvView(InvSee plugin) {
        this.plugin = plugin;
    }

    public static boolean editable(int slot) {
        if (slot >= 0 && slot <= 35) return true;
        if (slot >= 45 && slot <= 48) return true;
        if (slot == 49 && Compat.hasOffhand()) return true;
        return false;
    }

    public boolean allowEdit() {
        return plugin.getConfig().getBoolean("allow-edit", true);
    }


    public Inventory build(Player target) {
        InvSeeHolder holder = new InvSeeHolder(target.getUniqueId(), target.getName());

        String title = Compat.color(plugin.getConfig().getString("title", "&8%player% - Envanter")
                .replace("%player%", target.getName()));
        if (title.length() > 32) title = title.substring(0, 32);

        Inventory inv = Bukkit.createInventory(holder, 54, title);
        holder.setInventory(inv);

        PlayerInventory ti = target.getInventory();

        for (int gui = 0; gui <= 26; gui++) {
            inv.setItem(gui, ti.getItem(9 + gui));
        }
        for (int gui = 27; gui <= 35; gui++) {
            inv.setItem(gui, ti.getItem(gui - 27));
        }

        ItemStack filler = filler();
        for (int i = 36; i <= 44; i++) inv.setItem(i, filler);
        inv.setItem(50, filler);
        inv.setItem(51, filler);
        inv.setItem(52, filler);

        inv.setItem(45, ti.getHelmet());
        inv.setItem(46, ti.getChestplate());
        inv.setItem(47, ti.getLeggings());
        inv.setItem(48, ti.getBoots());

        if (Compat.hasOffhand()) {
            inv.setItem(49, Compat.getOffhand(ti));
        } else {
            inv.setItem(49, filler);
        }

        inv.setItem(53, info(target.getName()));
        return inv;
    }

    // chizzar-dev
    public boolean sync(Inventory inv) {
        if (!allowEdit()) return true;
        if (inv == null || !(inv.getHolder() instanceof InvSeeHolder)) return true;

        InvSeeHolder holder = (InvSeeHolder) inv.getHolder();
        UUID id = holder.getTarget();

        Player target = null;
        for (Player p : Compat.online()) {
            if (p.getUniqueId().equals(id)) {
                target = p;
                break;
            }
        }
        if (target == null) return false;

        PlayerInventory ti = target.getInventory();

        for (int gui = 0; gui <= 26; gui++) {
            ti.setItem(9 + gui, norm(inv.getItem(gui)));
        }
        for (int gui = 27; gui <= 35; gui++) {
            ti.setItem(gui - 27, norm(inv.getItem(gui)));
        }

        ti.setHelmet(norm(inv.getItem(45)));
        ti.setChestplate(norm(inv.getItem(46)));
        ti.setLeggings(norm(inv.getItem(47)));
        ti.setBoots(norm(inv.getItem(48)));

        if (Compat.hasOffhand()) {
            Compat.setOffhand(ti, norm(inv.getItem(49)));
        }

        try {
            target.updateInventory();
        } catch (Throwable ignored) {
        }
        return true;
    }

    private ItemStack norm(ItemStack it) {
        if (it == null || it.getType() == Material.AIR) return null;
        return it;
    }


    private ItemStack filler() {
        Material mat = Compat.material(plugin.getConfig().getString("filler.material", "GLASS_PANE"), Material.STONE);
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Compat.color(plugin.getConfig().getString("filler.name", " ")));
            it.setItemMeta(meta);
        }
        return it;
    }

    private ItemStack info(String targetName) {
        Material mat = Compat.material(plugin.getConfig().getString("info.material", "PAPER"), Material.PAPER);
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Compat.color(plugin.getConfig().getString("info.name", "&e%player%").replace("%player%", targetName)));

            List<String> lore = plugin.getConfig().getStringList("info.lore");
            if (lore != null && !lore.isEmpty()) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, Compat.color(lore.get(i).replace("%player%", targetName)));
                }
                meta.setLore(lore);
            }
            it.setItemMeta(meta);
        }
        return it;
    }
}
