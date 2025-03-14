package com.mobi.ecommerce.role;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType name;
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "role"
    )
    private List<User_Role> userRoles;

    public Role(UUID id, RoleType name, List<User_Role> userRoles) {
        this.id = id;
        this.name = name;
        this.userRoles = userRoles;
    }

    public Role(RoleType name, List<User_Role> userRoles) {
        this.name = name;
        this.userRoles = userRoles;
    }

    public Role(RoleType name) {
        this.name = name;
    }

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public RoleType getName() {
        return name;
    }

    public List<User_Role> getUserRoles() {
        return userRoles;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public void setUserRoles(List<User_Role> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole (User_Role userRole){
        if(!userRoles.contains(userRole))
            userRoles.add(userRole);
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name=" + name + "}";
    }
}
