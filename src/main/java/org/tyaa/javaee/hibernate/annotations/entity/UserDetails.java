package org.tyaa.javaee.hibernate.annotations.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "UsersDetails")
@NoArgsConstructor
@Getter @Setter
public class UserDetails extends AbstractEntity {
    @Column(name="text")
    private String text;
    @OneToOne(mappedBy="userDetails")
    private User user;
    public UserDetails(String text) {
        this.text = text;
    }
}
