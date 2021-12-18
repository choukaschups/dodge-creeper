package me.choukas.dodgecreeper.core.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.api.item.ItemListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    public static final String NBT_LISTENER_TAG = "listener";

    private final ItemListenerManager itemListenerManager;

    @Inject
    public PlayerInteractListener(ItemListenerManager itemListenerManager) {
        this.itemListenerManager = itemListenerManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (!NBTEditor.contains(item, NBT_LISTENER_TAG)) {
            return;
        }

        Player player = event.getPlayer();

        UUID uuid = UUID.fromString(NBTEditor.getString(item, NBT_LISTENER_TAG));
        this.itemListenerManager.getItemListener(uuid).onRightClick(player);
    }
}
