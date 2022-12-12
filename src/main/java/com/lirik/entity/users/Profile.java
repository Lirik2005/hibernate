package com.lirik.entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Profile {

    @Id
    @Column(name = "user_Id")
    private Long id;

    @OneToOne
//    @JoinColumn(name = "user_id") // Можно сослаться на таблицу Users вот так
    @PrimaryKeyJoinColumn  // А можно и так
    private User userForMapping;

    private String street;

    private String language;

    public void setUser(User user) {
        this.userForMapping = user;
        this.id = user.getId();
    }
}
