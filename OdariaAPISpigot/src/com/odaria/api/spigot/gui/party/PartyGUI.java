package com.odaria.api.spigot.gui.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.friends.OpenFriendsGUI;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import com.odaria.api.spigot.party.LeaveParty;
import com.odaria.api.spigot.party.RemoveParty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PartyGUI extends GUI {
    private Party party;

    public PartyGUI(Player player, Party party) {
        super(GUIManager.INSTANCE, player, false, 54, "Groupe de " + party.getLeader());
        this.party = party;
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
                case WOOD_DOOR:
                    this.closeAndRemove();
                    break;
                case STAINED_CLAY:
                    this.closeAndRemove();
                    if(item.getData().getData() == 14) {
                        RemoveParty.Action(getPlayer().getDisplayName());
                    } else {
                        LeaveParty.Action(player);
                    }
                    break;
                case SKULL_ITEM:
                    this.closeAndRemove();
                    if(item.getData().getData() == 2) {
                        OpenFriendsGUI.Action(player);
                    } else {
                        new ActionPartyGUI(player, item.getItemMeta().getDisplayName(), party);
                    }
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

    @Override
    public void initializeItems() {
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));
        if(party.getLeader().equalsIgnoreCase(getPlayer().getDisplayName())) {
            getInventory().setItem(0, createGuiItem(Material.STAINED_CLAY, "§cDissoudre le groupe", 14));
            getInventory().setItem(1, createGuiItem(Material.SKULL_ITEM, "§cInviter un ami dans le groupe", 2));
        } else {
            getInventory().setItem(0, createGuiItem(Material.STAINED_CLAY, "§cQuitter le groupe", 15));
        }
        int x = 9;
        for(String playerName : party.getPlayers()) {
            if(playerName.equalsIgnoreCase(party.getLeader())) {
                getInventory().setItem(x, createGuiHeadItem(Material.SKULL_ITEM, playerName, 3, "§aChef du groupe"));
            } else {
                getInventory().setItem(x, createGuiHeadItem(Material.SKULL_ITEM, playerName, 3));
            }
            x++;
        }
    }
}
