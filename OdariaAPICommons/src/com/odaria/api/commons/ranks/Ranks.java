package com.odaria.api.commons.ranks;

import java.util.ArrayList;
import java.util.List;

public enum Ranks {
    ADMINISTRATOR_PLUS("§d[§4Admin§5+§d]§4", "§d[§4Admin§5+§d]§4", "§e",2, "§d[§4A§5+§d]§4"),
    ADMINISTRATOR("§d[§4Admin§d]§4", "§d[§4Admin§d]§4", "§e",3, "§d[§4A§d]§4"),
    MODERATOR("§a[§2Moderateur§a]§2", "§a[§2Moderatrice§a]§2", "§e",4, "§a[§2M§a]§2"),
    DEVELOPER("§3[§bDev§3]§b", "§3[§bDev§3]§b", "§e",7, "§3[§bD§3]§b"),
    GUIDE("§2[§aGuide§2]§a", "§2[§aGuide§2]§a", "§e",5, "§2[§aG§2]§a"),
    BUILDER("§d[§5Architecte§d]§5", "§d[§5Architecte§d]§5", "§e",6, "§d[§5Ar§d]§5"),
    VIP("§6[§eVIP§6]§e", "§6[§eVIP§6]§e", "",8, "§6[§eV§6]§e"),
    PLAYER("§8[§7Joueur§8]§7", "§8[§7Joueuse§8]§7", "",1, "§8[§7J§8]§7");

    private String nameM;
    private String nameW;
    private String messageFormat;
    private int groupId;
    private List<String> permissions;
    private String tabPrefix;

    Ranks(String nameM, String nameW, String messageFormat, int groupId, String tabPrefix)
    {
        this.nameM = nameM;
        this.nameW = nameW;
        this.messageFormat = messageFormat;
        this.groupId = groupId;
        permissions = new ArrayList<>();
        this.tabPrefix = tabPrefix;
    }

    public void addPermission(String permission) {
        permissions.add(permission.toLowerCase());
    }

    public static Ranks getById(int id)
    {
        for(Ranks g : Ranks.values())
        {
            if(id == g.getGroupId())
            {
                return g;
            }
        }
        return Ranks.PLAYER;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getNameM() {
        return nameM;
    }

    public String getNameW() {
        return nameW;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public String getTabPrefix() {
        return tabPrefix;
    }
}

