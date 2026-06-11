package com.spring.security.app_security.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name="roles")
@Data
public class RoleEntity implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // private BigInteger id;

    @Id
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "description")
    private String description;
    @Column(name = "id_customer")
    private BigInteger idCustomer;


}
