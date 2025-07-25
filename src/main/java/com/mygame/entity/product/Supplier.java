package com.mygame.entity.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mygame.entity.BaseEntity;
import com.mygame.entity.address.Address;
import com.mygame.entity.inventory.PurchaseOrder;
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
@Table(name = "supplier")
public class Supplier extends BaseEntity {
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "contact_fullname")
    private String contactFullname;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    private Address address;

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();
}
