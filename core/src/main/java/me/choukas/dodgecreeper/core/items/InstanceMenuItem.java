package me.choukas.dodgecreeper.core.items;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.api.item.ItemListener;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import me.choukas.dodgecreeper.core.inventories.InstanceMenu;
import me.choukas.dodgecreeper.core.listeners.player.PlayerInteractListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import java.util.UUID;

public class InstanceMenuItem {

    private final Translator translator;

    @Inject
    public InstanceMenuItem(Translator translator) {
        this.translator = translator;
    }

    public ItemStack asItemStack(Player owner) {
        ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(
                this.translator.translate(owner,
                        Component.translatable(TranslationKeys.INSTANCE_MENU_ITEM_NAME)
                )
        );
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
            this.instanceMenu.asSmartInventory(player)
                    .open(player);
        }
    }
}
