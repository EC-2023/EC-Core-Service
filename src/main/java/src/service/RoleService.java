
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Role;
import src.repository.IRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    private  IRoleRepository roleRepository;
    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Optional<Role> getOne(UUID id) {
        return roleRepository.findById(id);
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role update(UUID id, Role role) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {

            roleRepository.save(existingRole);
        }
        return existingRole;
    }

    public void remove(UUID id) {
        roleRepository.deleteById(id);
    }
}
