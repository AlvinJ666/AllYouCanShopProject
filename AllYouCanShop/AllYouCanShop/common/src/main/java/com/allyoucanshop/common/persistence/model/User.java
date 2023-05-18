package com.allyoucanshop.common.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true, columnDefinition = " varchar(128)")
    private String email;

    @Column(length = 64, nullable = false, unique = true, columnDefinition = " varchar(64)")
    private String password;

    @Column(length = 64, nullable = false, columnDefinition = " varchar(64)")
    private String firstName;

    @Column(length = 64, nullable = false, columnDefinition = " varchar(64)")
    private String lastName;

    @Column(length = 64)
    private String photos;

    @Column
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password, String firstName, String lastName, boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public boolean sameUser(User user) {
        return Comparator.comparing(User::getFirstName)
                .thenComparing(User::getLastName)
                .thenComparing(User::getEmail)
                .thenComparing(User::isEnabled)
                .thenComparing(User::getPassword)
                .compare(this, user) == 0;
    }
}
