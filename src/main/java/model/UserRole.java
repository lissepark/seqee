package model;

import java.util.HashSet;
import java.util.Set;

public class UserRole {
    private ListRole listRole;

    private Set<User> user = new HashSet<>();

    public ListRole getListRole() {
        return listRole;
    }

    public void setListRole(ListRole listRole) {
        this.listRole = listRole;
    }
}
