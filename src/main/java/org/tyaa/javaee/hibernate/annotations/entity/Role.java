package org.tyaa.javaee.hibernate.annotations.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Roles")
// @NoArgsConstructor @Getter @Setter
public class Role extends AbstractEntity {
    @Column(name="title", length=25)
    private String title;
    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>(0);

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
