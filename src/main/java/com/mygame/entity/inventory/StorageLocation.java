package com.mygame.entity.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "storage_location")
public class StorageLocation extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonBackReference
    private Warehouse warehouse;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false, unique = true)
    @MapsId
    private Variant variant;

    @Column(name = "name", nullable = false)
    private String name;
}
