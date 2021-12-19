package me.choukas.dodgecreeper.core.listeners.player;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.api.item.ItemListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    public static final String LISTENER_NBT_TAG = "listener";

    private final ItemListenerManager itemListenerManager;

    @Inject
    public PlayerInteractListener(ItemListenerManager itemListenerManager) {
        this.itemListenerManager = itemListenerManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (item == null || !(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || !NBTEditor.contains(item, LISTENER_NBT_TAG)) {
            return;
        }

        Player player = event.getPlayer();

        UUID uuid = UUID.fromString(NBTEditor.getString(item, LISTENER_NBT_TAG));
        this.itemListenerManager.getItemListener(uuid).onRightClick(player);
    }
}
