package me.gibson.nooffhanditems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OffhandChecker extends BukkitRunnable {

    private final JavaPlugin plugin;

    public OffhandChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack offhandItem = player.getInventory().getItemInOffHand();
            if (plugin.getConfig().getStringList("blocked-items").contains(offhandItem.getType().name())) {
                if (player.getInventory().addItem(offhandItem).isEmpty()) {
                    player.sendMessage("You can't hold this item in your offhand! It has been moved to your inventory.");
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), offhandItem);
                    player.sendMessage("You can't hold this item in your offhand! It has been dropped at your location.");
                }
                player.getInventory().setItemInOffHand(null);
            }
        }
    }
}