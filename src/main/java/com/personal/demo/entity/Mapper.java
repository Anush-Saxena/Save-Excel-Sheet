package com.personal.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapper_id")
    private int mapperId;

    private String userName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_mapper_id", referencedColumnName = "mapper_id")
    List<Material> materialList;

    public Mapper(String userName, List<Material> list){
        this.userName = userName;
        this.materialList = list;
    }

}
