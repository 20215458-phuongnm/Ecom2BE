package com.mygame.entity.general;

import com.mygame.entity.BaseEntity;
import com.mygame.entity.authentication.User;
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
@Table(name = "notification")
public class Notification extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "anchor")
    private String anchor;

    // 2 trạng thái: 1 - Chưa đọc, 2 - Đã đọc
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;
}
