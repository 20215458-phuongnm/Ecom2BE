package com.mygame.entity.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mygame.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "order_cancellation_reason")
public class OrderCancellationReason extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

    @OneToMany(mappedBy = "orderCancellationReason", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
}
