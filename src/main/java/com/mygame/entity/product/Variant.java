package com.mygame.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.mygame.entity.BaseEntity;
import com.mygame.entity.cart.CartVariant;
import com.mygame.entity.inventory.CountVariant;
import com.mygame.entity.inventory.DocketVariant;
import com.mygame.entity.inventory.PurchaseOrderVariant;
import com.mygame.entity.order.OrderVariant;
import com.mygame.utils.JsonNodeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "variant")
public class Variant extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "properties", columnDefinition = "JSON")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode properties;

    @Deprecated
    @Column(name = "images", columnDefinition = "JSON")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode images;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

//    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL)
//    private StorageLocation storageLocation;
//
//    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL)
//    private VariantInventoryLimit variantInventoryLimit;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<CountVariant> countVariants = new HashSet<>();

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<DocketVariant> docketVariants = new HashSet<>();

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<PurchaseOrderVariant> purchaseOrderVariants = new HashSet<>();

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<OrderVariant> orderVariants = new HashSet<>();

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private Set<CartVariant> cartVariants = new HashSet<>();
}
