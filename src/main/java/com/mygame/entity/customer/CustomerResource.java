package com.mygame.entity.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mygame.entity.BaseEntity;
import com.mygame.entity.order.OrderResource;
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
@Table(name = "customer_resource")
public class CustomerResource extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

    @OneToMany(mappedBy = "customerResource", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Customer> employees = new ArrayList<>();

    @OneToMany(mappedBy = "customerResource", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderResource> orderResources = new ArrayList<>();
}
