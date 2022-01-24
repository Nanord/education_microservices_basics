package com.stm.user.manager.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Page;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "role", schema = "user_manager")
@Accessors(chain = true)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;
    @UpdateTimestamp
    @Column(name = "modified")
    private ZonedDateTime modified;
    @Column(name = "sysname", nullable = false, unique = true)
    @Size(max = 20)
    private String sysname;
    @Column(name = "name")
    @Size(max = 20)
    private String name;
    @Column(name = "description")
    @Size(max = 50)
    private String description;


    //TODO FetchType.LAZY. Не подгружает в объект данные, пока не вызовится метод getUsers().
    // В данном примере помогает избежать лишних запросов в базу.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user2role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> users;
}
