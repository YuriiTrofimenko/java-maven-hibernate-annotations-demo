package org.tyaa.javaee.hibernate.annotations.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Users")
@NoArgsConstructor @Getter @Setter
public class User extends AbstractEntity {
    @Column(name="age")
    private int age;
    @Column(name="first_name", length=25)
    private String firstName;
    @Column(name="last_name", length=25)
    private String lastName;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    @OneToOne
    // @JoinColumn(name = "id")
    @PrimaryKeyJoinColumn
    private UserDetails userDetails;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="UsersRepositories")
    private Set<Repository> repositories = new HashSet<>(0);

    /* public User() {}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Set<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<Repository> repositories) {
        this.repositories = repositories;
    } */
}
