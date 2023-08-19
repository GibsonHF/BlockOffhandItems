package me.gibson.nooffhanditems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class OffhandListener implements Listener {

    private final JavaPlugin plugin;

    public OffhandListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        // Check if the action is related to the off-hand slot (slot number 40)
        if (event.getSlot() == 40) {
            ItemStack clickedItem = event.getCursor();
            if (clickedItem != null && plugin.getConfig().getStringList("blocked-items").contains(clickedItem.getType().name())) {
                // Cancel the event immediately, so the item can't be moved to the offhand slot
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                player.sendMessage("You can't click this item in your offhand!");
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack offhandItem = player.getInventory().getItemInOffHand();

        if (plugin.getConfig().getStringList("blocked-items").contains(offhandItem.getType().name())) {
                player.sendMessage("You can't use this item in your offhand!");
            // cancel the click event to prevent the item from being used
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack offhandItem = event.getOffHandItem();

        if (plugin.getConfig().getStringList("blocked-items").contains(offhandItem.getType().name())) {
            // Check if player's inventory is full
                player.sendMessage("You can't hold this item in your offhand!");
            // Cancel the event to prevent the item from moving to offhand
            event.setCancelled(true);
        }
    }
}