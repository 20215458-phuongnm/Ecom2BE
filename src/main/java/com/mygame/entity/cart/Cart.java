package com.mygame.entity.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mygame.entity.BaseEntity;
import com.mygame.entity.authentication.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Reference: https://dba.stackexchange.com/a/133641
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartVariant> cartVariants = new HashSet<>();

    // 2 trạng thái: (1) Normal, (2) Complete
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
}
