package com.mobi.ecommerce.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobi.ecommerce.role.User_Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name="User")
@Table(name="app_user",uniqueConstraints = {
        @UniqueConstraint(name = "user_unique_email", columnNames = "email") // Ensures email uniqueness in the database
})
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
   private UUID id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    String firstName ;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT",
            updatable = false // Prevents email from being updated
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @CreatedDate
    @Column ( name = "createdAt", nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked;  // Default: Not locked

    @Column(name = "account_enabled", nullable = false)
    private boolean accountEnabled;  // Default: Disabled until email verification
//    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
//    private String phoneNumber;



    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private List<User_Role> userRoles = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "user",
//            orphanRemoval = true,
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            fetch = FetchType.LAZY
//    )
//    private List <Order> orders;
    public User() {
    }

    public User(UUID id, String firstName, String lastName, String email, String password, LocalDateTime createdAt, LocalDateTime lastModifiedDate, boolean accountLocked, boolean accountEnabled, List<User_Role> userRoles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastModifiedDate = lastModifiedDate;
        this.accountLocked = accountLocked;
        this.accountEnabled = accountEnabled;
//        this.phoneNumber = phoneNumber;
        this.userRoles = userRoles;
    }

    public List <User_Role> getUserRoles(){
        return  userRoles;
    }
    public void addUserRole (User_Role userRole){
        if(!userRoles.contains(userRole))
            userRoles.add(userRole);
    }

    public void removeRole(User_Role userRole){
        userRoles.remove(userRole);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

    public String fullName(){
        return firstName +" "+ lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public void setUserRoles(List<User_Role> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String getName() {
        return email;
    }
}
