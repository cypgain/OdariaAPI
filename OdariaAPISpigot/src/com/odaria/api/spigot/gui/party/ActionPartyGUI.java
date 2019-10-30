package com.odaria.api.spigot.gui.party;

import com.odaria.api.commons.core.Party;
import com.odaria.api.spigot.guimanager.GUI;
import com.odaria.api.spigot.guimanager.GUIManager;
import com.odaria.api.spigot.party.KickPlayerParty;
import com.odaria.api.spigot.party.OpenPartyGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ActionPartyGUI extends GUI {

    private Party party;

    public ActionPartyGUI(Player player, String targetPlayerName, Party party) {
        super(GUIManager.INSTANCE, player, false, 54, targetPlayerName);
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
                case ARROW:
                    this.closeAndRemove();
                    OpenPartyGUI.Action(player);
                    break;
                case STAINED_CLAY:
                    this.closeAndRemove();
                    if(item.getData().getData() == 14) {
                        if(party.getLeader().equalsIgnoreCase(player.getDisplayName())) {
                            if(!(party.getLeader().equalsIgnoreCase(event.getInventory().getName()))) {
                                KickPlayerParty.Action(player.getDisplayName(), event.getInventory().getName());
                            } else {
                                player.sendMessage("Vous ne pouvez pas vous exclure vous même");
                            }
                        } else {
                            player.sendMessage("Vous n'etes pas chef du groupe");
                        }
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
        getInventory().setItem(0, createGuiItem(Material.ARROW, "Retour", "§aRetour à la fenetre du groupe"));
        getInventory().setItem(8, createGuiItem(Material.WOOD_DOOR, "Fermer", "§aFermer la fenetre actuelle"));

        getInventory().setItem(31, createGuiItem(Material.STAINED_CLAY, "§cVirer du groupe", 14, "Vous devez être chef du groupe pour effectuer cette action"));
    }
}