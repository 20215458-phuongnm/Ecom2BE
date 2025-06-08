package com.mygame.entity.order;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class OrderVariantKey implements Serializable {
    @Column(name = "order_id", nullable = false)
    Long orderId;

    @Column(name = "variant_id", nullable = false)
    Long variantId;
}
