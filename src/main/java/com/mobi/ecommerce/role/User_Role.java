package com.mobi.ecommerce.role;

import com.mobi.ecommerce.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_role")
public class User_Role {
    @EmbeddedId
    private User_RoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name="userRole_user_id_fk"))
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id",
            foreignKey = @ForeignKey(name="userRole_role_id_fk"))
    private Role role;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

    public User_Role() {}

    public User_Role(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new User_RoleId(user.getId(), role.getId()); // ✅ Fix: Initialize ID
        this.createdAt = LocalDateTime.now(); // ✅ Optional: Auto-set timestamp
    }

    public User_RoleId getId() {
        return id;
    }

    public void setId(User_RoleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_Role that = (User_Role) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
