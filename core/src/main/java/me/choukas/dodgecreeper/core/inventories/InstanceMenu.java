package me.choukas.dodgecreeper.core.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.choukas.dodgecreeper.api.server.ServerManager;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class InstanceMenu {

    private static final String MENU_ID = "instance-menu";

    private final InventoryManager inventoryManager;
    private final Provider provider;
    private final Translator translator;

    @Inject
    public InstanceMenu(InventoryManager inventoryManager,
                        Provider provider,
                        Translator translator) {
        this.inventoryManager = inventoryManager;
        this.provider = provider;
        this.translator = translator;
    }

    public SmartInventory asSmartInventory(Player player) {
        return SmartInventory.builder()
                .manager(this.inventoryManager)
                .id(MENU_ID)
                .title(
                        this.translator.translate(player,
                                Component.translatable(TranslationKeys.INSTANCE_MENU_TITLE)
                        )
                )
                .provider(this.provider)
                .build();
    }

    public static class Provider implements InventoryProvider {

        private final ServerManager serverManager;

        @Inject
        public Provider(ServerManager serverManager) {
            this.serverManager = serverManager;
        }

        @Override
        public void init(Player player, InventoryContents contents) {

        }

        @Override
        public void update(Player player, InventoryContents contents) {
            this.serverManager.getServers().whenComplete((result, error) ->
                    result.forEach(server ->
                            contents.add(
                                    ClickableItem.of(
                                            new ItemStack(Material.ACACIA_DOOR), // TODO Add name
                                            (event) ->
                                                    this.serverManager.connect(player, server)
                                    )
                            )
                    ));
        }
    }
}
