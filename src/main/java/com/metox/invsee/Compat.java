package com.metox.invsee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// chizzar-dev
public final class Compat {

    public static final int MINOR;

    static {
        int m = 8;
        try {
            String raw = Bukkit.getBukkitVersion().split("-")[0];
            String[] parts = raw.split("\\.");
            if (parts.length >= 2) m = Integer.parseInt(parts[1]);
        } catch (Throwable t) {
            m = 8;
        }
        MINOR = m;
    }

    private Compat() {}

    public static String color(String in) {
        if (in == null) return "";
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static Material material(String name, Material def) {
        if (name == null) return def;
        Material mt = Material.matchMaterial(name.trim());
        return mt != null ? mt : def;
    }


    public static boolean hasOffhand() {
        return MINOR >= 9;
    }

    public static ItemStack getOffhand(PlayerInventory inv) {
        try {
            Method m = PlayerInventory.class.getMethod("getItemInOffHand");
            return (ItemStack) m.invoke(inv);
        } catch (Throwable t) {
            return null;
        }
    }

    public static void setOffhand(PlayerInventory inv, ItemStack item) {
        try {
            Method m = PlayerInventory.class.getMethod("setItemInOffHand", ItemStack.class);
            m.invoke(inv, item);
        } catch (Throwable ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Player> online() {
        List<Player> list = new ArrayList<Player>();
        try {
            Object res = Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
            if (res instanceof Player[]) {
                for (Player p : (Player[]) res) list.add(p);
            } else if (res instanceof Collection) {
                for (Object o : (Collection<Object>) res) list.add((Player) o);
            }
        } catch (Throwable ignored) {
        }
        return list;
    }
}
