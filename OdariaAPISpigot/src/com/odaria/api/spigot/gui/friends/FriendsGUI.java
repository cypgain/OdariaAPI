package com.odaria.api.spigot.gui.friends;

import com.odaria.api.spigot.OdariaAPISpigot;
import com.odaria.api.spigot.anvilgui.AnvilGUI;
import com.odaria.api.spigot.friends.NewFriendRequest;
import com.odaria.api.spigot.friends.SendFriendRequest;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FriendsGUI extends GUI {

    private Map<String, String> friends;

    public FriendsGUI(Player player, Map<String, String> friends) {
        super(GUIManager.INSTANCE, player, false, 54, "Liste des amis");
        this.friends = friends;
    }

    @Override
    public void initializeItems() {
        getInventory().setItem(0, createGuiItem(Material.PAPER, "Demandes d'amis", "§aConsulter les demandes d'amis que vous avez reçu"));
        getInventory().setItem(1, createGuiItem(Material.COMPASS, "Ajouter un ami"));
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));

        Map<String, String> sortedFriends = sortHashMapByValues(friends);
        AtomicInteger x = new AtomicInteger(9);
        sortedFriends.forEach((key, value) -> {
            if(value.equalsIgnoreCase("En Ligne")) {
                getInventory().setItem(x.get(), createGuiHeadItem(Material.SKULL_ITEM, key, 3, "Status : §a" + value));
            } else {
                getInventory().setItem(x.get(), createGuiHeadItem(Material.SKULL_ITEM, key, 3, "Status : §c" + value));
            }
            x.getAndIncrement();
        });


    }

    @Override
    public void onOtherInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null) {
            Player player = (Player) event.getWhoClicked();
            Material mat = event.getCurrentItem().getType();
            ItemStack item = event.getCurrentItem();
            switch (mat) {
                case PAPER:
                    this.closeAndRemove();
                    new FriendsRequestsGUI(player).openInventory();
                    break;
                case WOOD_DOOR:
                    this.closeAndRemove();
                    break;
                case COMPASS:
                    NewFriendRequest.Action(player);
                    this.closeAndRemove();
                    break;
                case SKULL_ITEM:
                    this.closeAndRemove();
                    new FriendActionGUI(player, item.getItemMeta().getDisplayName()).openInventory();
                    break;
            }
        }
        event.setCancelled(true);
    }

    @Override
    public void onInventoryClose() {

    }

    @Override
    public void onGuiRemove() {

    }

    public LinkedHashMap<String, String> sortHashMapByValues(Map<String, String> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<String> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

        Iterator<String> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            String val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                String comp1 = passedMap.get(key);
                String comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}

