package com.mobi.ecommerce.role;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class User_RoleId {
    @Column(name = "user_id")
    private UUID student_id;
    @Column(name="role_id")
    private UUID role_id;

    public User_RoleId(UUID student_id, UUID role_id) {
        this.student_id = student_id;
        this.role_id = role_id;
    }

    public User_RoleId() {
    }

    public UUID getStudent_id() {
        return student_id;
    }

    public UUID getRole_id() {
        return role_id;
    }

    public void setStudent_id(UUID student_id) {
        this.student_id = student_id;
    }

    public void setRole_id(UUID role_id) {
        this.role_id = role_id;
    }
}
