package me.choukas.dodgecreeper.core.items;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;

public class PusherItem {

    private static final String OWNER_NBT_TAG = "owner";

    private final Translator translator;

    @Inject
    public PusherItem(Translator translator) {
        this.translator = translator;
    }

    public ItemStack asItemStack(Player owner) {
        ItemStack itemStack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(
                this.translator.translate(owner,
                        Component.translatable(TranslationKeys.PUSHER_NAME)
                )
        );
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 5, true);
        itemStack.setItemMeta(itemMeta);

        itemStack = NBTEditor.set(itemStack, owner.getUniqueId().toString(), OWNER_NBT_TAG);

        return itemStack;
    }
}
