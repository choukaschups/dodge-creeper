package me.choukas.dodgecreeper.core.items;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.core.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class PusherItem {

    private static final String OWNER_NBT_TAG = "owner";

    public ItemStack asItemStack(Player owner) {
        ItemStack itemStack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(((TextComponent) GlobalTranslator.render(Component.translatable(Messages.PUSHER_NAME), Locale.FRENCH)).content());
        itemStack.setItemMeta(itemMeta);

        itemStack = NBTEditor.set(itemStack, owner.getUniqueId(), OWNER_NBT_TAG);

        return itemStack;
    }
}
