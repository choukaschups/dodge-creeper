package me.choukas.dodgecreeper.api.item;

import java.util.UUID;

public interface ItemListenerManager {

    ItemListener getItemListener(UUID uuid);
}
