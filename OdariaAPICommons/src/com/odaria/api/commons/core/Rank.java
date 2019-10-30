package com.odaria.api.commons.core;

import java.util.ArrayList;
import java.util.List;

public class Rank {
    private int groupId;
    private List<String> permissions;

    public Rank() {

    }

    public Rank(int groupId) {
        this.groupId = groupId;
        permissions = new ArrayList<>();
    }

    public int getGroupId() {
        return groupId;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
