package me.choukas.dodgecreeper.core.items;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.api.item.ItemListener;
import me.choukas.dodgecreeper.core.Messages;
import me.choukas.dodgecreeper.core.inventories.InstanceMenu;
import me.choukas.dodgecreeper.core.listeners.player.PlayerInteractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import java.util.Locale;
import java.util.UUID;

public class InstanceMenuItem {

    public ItemStack asItemStack() {
        // Note : Add a player as argument to change the item's name according to its language
        ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(((TextComponent) GlobalTranslator.render(Component.translatable(Messages.INSTANCE_MENU_ITEM_NAME), Locale.FRENCH)).content());
        itemStack.setItemMeta(itemMeta);

        itemStack = NBTEditor.set(itemStack, Listener.UUID.toString(), PlayerInteractListener.LISTENER_NBT_TAG);

        return itemStack;
    }

    public static class Listener implements ItemListener {

        public static final UUID UUID = java.util.UUID.randomUUID();

        private final InstanceMenu instanceMenu;

        @Inject
        public Listener(InstanceMenu instanceMenu) {
            this.instanceMenu = instanceMenu;
        }

        @Override
        public void onRightClick(Player player) {
            this.instanceMenu.asSmartInventory().open(player);
        }
    }
}
