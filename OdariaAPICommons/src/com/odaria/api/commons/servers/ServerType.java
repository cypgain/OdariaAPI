package com.odaria.api.commons.servers;

public enum ServerType {
    SKYWARS_HUB("SKYWARS_HUB", "skywars_hub", true),
    SKYWARS_SOLO("SKYWARS_SOLO", "skywars_solo", false),
    SKYWARS_DUO("SKYWARS_DUO", "skywars_duo", false),
    SKYWARS_MEGA("SKYWARS_MEGA", "skywars_mega", false),
    PAINTBALL_HUB("PAINTBALL_HUB", "paintball_hub", true),
    PAINTBALL_GAME("PAINTBALL_GAME", "paintball_game", false),
    HUNGERGAME_HUB("HUNGERGAME_HUB", "hungergame_hub", true),
    HUNGERGAME_GAME("HUNGERGAME_GAME", "hungergame_game", false),
    GODOFODARIA_GAME("GODOFODARIA_GAME", "godofodaria_game", false);

    private String name;
    private String template;
    private boolean isHub;

    ServerType(String name, String template, boolean isHub) {
        this.name = name;
        this.template = template;
        this.isHub = isHub;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }

    public boolean isHub() {
        return isHub;
    }

    public static ServerType getByName(String name)
    {
        for (ServerType type : ServerType.values()) {
            if(type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
