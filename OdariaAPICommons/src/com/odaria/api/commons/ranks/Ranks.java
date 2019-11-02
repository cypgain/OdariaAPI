package com.odaria.api.commons.ranks;

import java.util.ArrayList;
import java.util.List;

public enum Ranks {
    ADMINISTRATOR_PLUS("§d[§4Admin§5+§d]§4", "§e",2, "§d[§4A§5+§d]§4"),
    ADMINISTRATOR("§d[§4Admin§d]§4", "§e",3, "§d[§4A§d]§4"),
    MODERATOR("§a[§2Moderateur§a]§2", "§e",4, "§a[§2M§a]§2"),
    DEVELOPER("§3[§bDev§3]§b", "§e",7, "§3[§bD§3]§b"),
    GUIDE("§2[§aGuide§2]§a", "§e",5, "§2[§aG§2]§a"),
    BUILDER("§d[§5Architecte§d]§5", "§e",6, "§d[§5Ar§d]§5"),
    VIP("§6[§eVIP§6]§e", "",8, "§6[§eV§6]§e"),
    PLAYER("§8[§7Joueur§8]§7", "",1, "§8[§7J§8]§7");

    private String name;
    private String messageFormat;
    private int groupId;
    private String tabPrefix;

    Ranks(String name, String messageFormat, int groupId, String tabPrefix)
    {
        this.name = name;
        this.messageFormat = messageFormat;
        this.groupId = groupId;
        this.tabPrefix = tabPrefix;
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

    public String getName() {
        return name;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public String getTabPrefix() {
        return tabPrefix;
    }
}

