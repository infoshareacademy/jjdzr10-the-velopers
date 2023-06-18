package com.infoshare.webapp.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "The username cannot be empty")
    @Size(min=2, max=20, message = "The username should contain between 2 and 20 characters")
    private String username;
//    private Score userScore;
    @Column(name = "password", nullable = false)
    @NotEmpty(message = "The password cannot be empty")
//    @Size(min=2, max=10, message = "The password should contain between 2 and 10 characters")
    private String password;
    @NotEmpty(message = "The email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGame> results = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
