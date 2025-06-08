package com.mygame.entity.inventory;

import com.mygame.entity.BaseEntity;
import com.mygame.entity.product.Variant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "variant_inventory_limit")
public class VariantInventoryLimit extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false, unique = true)
    @MapsId
    private Variant variant;

    @Column(name = "minimum_limit")
    private Integer minimumLimit;

    @Column(name = "maximum_limit")
    private Integer maximumLimit;
}
