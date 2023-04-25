package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="role")
@Data
public class Role {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "role_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @Basic
    @Column(name = "createAt", nullable = true)
    private Date createAt = new Date(new java.util.Date().getTime());
   @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "roleByRoleId")
    @Where(clause = "is_deleted = false")
    private Collection<User> usersByRoleId;
    public Role(UUID id, String name ) {
        Id = id;
        this.name = name;
    }
    public Role() {

    }




}
