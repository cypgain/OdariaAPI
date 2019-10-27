package com.odaria.api.spigot.guimanager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI {
    public List<ItemStack> items;

    private GUIManager instance;
    private Player player;
    private Inventory inventory;
    private boolean allowOutsideClick;

    public GUI(GUIManager instance, Player player, boolean allowOutsideClick, int slot, String title)
    {
        this.instance = instance;
        setPlayer(player);
        setAllowOutsideClick(allowOutsideClick);
        inventory = Bukkit.createInventory(player, slot, title);
        instance.put(player, this);
    }

    public abstract void onOtherInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClose();

    public abstract void onGuiRemove();

    public abstract void initializeItems();

    public GUIManager getInstance()
    {
        return instance;
    }

    public ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metalore.add(lorecomments);
        }

        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createGuiItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (byte)data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metalore.add(lorecomments);
        }
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createGuiHeadItem(Material material, String name, int data, String... lore) {
        ItemStack item = new ItemStack(material, 1, (byte)data);
        SkullMeta playerHeadMeta = (SkullMeta) item.getItemMeta();
        playerHeadMeta.setOwner(name);
        playerHeadMeta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metaLore.add(lorecomments);
        }
        playerHeadMeta.setLore(metaLore);
        item.setItemMeta(playerHeadMeta);
        return item;
    }

    public void openInventory() {
        initializeItems();
        player.openInventory(inventory);
    }

    public void closeAndRemove()
    {
        player.closeInventory();
        remove();
    }

    public void remove()
    {
        instance.getGUIs().remove(player);
        onGuiRemove();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public void setAllowOutsideClick(boolean allowOutsideClick)
    {
        this.allowOutsideClick = allowOutsideClick;
    }

    public boolean isAllowOutsideClick()
    {
        return allowOutsideClick;
    }
}
