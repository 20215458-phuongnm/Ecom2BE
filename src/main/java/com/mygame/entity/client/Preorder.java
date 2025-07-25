package com.mygame.entity.client;

import com.mygame.entity.BaseEntity;
import com.mygame.entity.authentication.User;
import com.mygame.entity.product.Product;
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
@Table(name = "preorder", uniqueConstraints = @UniqueConstraint(name = "uc_preorder", columnNames = {"user_id", "product_id"}))
public class Preorder extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // TODO: 3 trạng thái: Chờ thông báo, Đã thông báo có hàng, Hủy thông báo
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
}
