package src.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="role")
public class Role {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "role_id", nullable = false)
    private UUID roleId;
    @Basic
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<User> usersByRoleId;

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId == role.roleId && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }

    public Collection<User> getUsersByRoleId() {
        return usersByRoleId;
    }

    public void setUsersByRoleId(Collection<User> usersByRoleId) {
        this.usersByRoleId = usersByRoleId;
    }
}
