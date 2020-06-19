package org.tyaa.javaee.hibernate.annotations.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Repositories")
@NoArgsConstructor @Getter @Setter
public class Repository extends AbstractEntity {
    @Column(name="data")
    private String data;
    @ManyToMany(mappedBy="repositories")
    private Set<User> users = new HashSet<>(0);
    public Repository(String data) {
        this.data = data;
    }
}
