package com.mygame.entity.promotion;

import com.mygame.entity.BaseEntity;
import com.mygame.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "promotion")
public class Promotion extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "promotion_product",
            joinColumns = @JoinColumn(name = "promotion_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false)
    )
    private Set<Product> products = new HashSet<>();
}
