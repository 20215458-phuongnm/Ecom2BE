package com.mygame.entity.reward;

import com.mygame.entity.BaseEntity;
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
@Table(name = "reward_strategy")
public class RewardStrategy extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RewardType code;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
}
