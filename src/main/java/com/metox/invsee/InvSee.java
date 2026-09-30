package com.metox.invsee;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class InvSee extends JavaPlugin implements TabExecutor {

    private InvView view;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        view = new InvView(this);

        if (getCommand("invsee") != null) {
            getCommand("invsee").setExecutor(this);
            getCommand("invsee").setTabCompleter(this);
        }
        getServer().getPluginManager().registerEvents(new InvListener(this, view), this);

        getLogger().info("InvSee aktif - algilanan surum 1." + Compat.MINOR);
        // chizzar-dev
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            send(sender, "players-only");
            return true;
        }
        Player viewer = (Player) sender;

        if (!viewer.hasPermission("invsee.use")) {
            send(viewer, "no-permission");
            return true;
        }
        if (args.length < 1) {
            send(viewer, "usage");
            return true;
        }

        String first = args[0].toLowerCase();
        if (first.equals("enderchest") || first.equals("ender") || first.equals("ec")) {
            if (args.length < 2) {
                send(viewer, "usage");
                return true;
            }
            Player t = Bukkit.getPlayerExact(args[1]);
            if (t == null) {
                send(viewer, "player-not-found");
                return true;
            }
            if (t.getUniqueId().equals(viewer.getUniqueId())) {
                send(viewer, "self");
                return true;
            }
            viewer.openInventory(t.getEnderChest());
            send(viewer, "opened-ender", "%player%", t.getName());
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            send(viewer, "player-not-found");
            return true;
        }
        if (target.getUniqueId().equals(viewer.getUniqueId())) {
            send(viewer, "self");
            return true;
        }

        Inventory inv = view.build(target);
        viewer.openInventory(inv);
        send(viewer, "opened", "%player%", target.getName());
        return true;
    }

    // chizzar-dev
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if (args.length == 1) {
            String pref = args[0].toLowerCase();
            if ("enderchest".startsWith(pref)) out.add("enderchest");
            for (Player p : Compat.online()) {
                if (p.getName().toLowerCase().startsWith(pref)) out.add(p.getName());
            }
            return out;
        }
        if (args.length == 2) {
            String s = args[0].toLowerCase();
            if (s.equals("enderchest") || s.equals("ender") || s.equals("ec")) {
                String pref = args[1].toLowerCase();
                for (Player p : Compat.online()) {
                    if (p.getName().toLowerCase().startsWith(pref)) out.add(p.getName());
                }
            }
        }
        return out;
    }

    public void send(CommandSender sender, String key, String... repl) {
        String m = getConfig().getString("messages." + key, "");
        if (m == null || m.isEmpty()) return;
        for (int i = 0; i + 1 < repl.length; i += 2) {
            m = m.replace(repl[i], repl[i + 1]);
        }
        sender.sendMessage(Compat.color(m));
    }
}
