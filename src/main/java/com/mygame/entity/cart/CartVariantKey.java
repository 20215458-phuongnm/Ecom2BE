package com.mygame.entity.cart;

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
public class CartVariantKey implements Serializable {
    @Column(name = "cart_id", nullable = false)
    Long cartId;

    @Column(name = "variant_id", nullable = false)
    Long variantId;
}
