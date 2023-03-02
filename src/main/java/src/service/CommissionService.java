
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Commission;
import src.repository.ICommissionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommissionService {
    private  ICommissionRepository commissionRepository;
    @Autowired
    public CommissionService(ICommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public List<Commission> getAll() {
        return (List<Commission>) commissionRepository.findAll();
    }

    public Optional<Commission> getOne(UUID id) {
        return commissionRepository.findById(id);
    }

    public Commission create(Commission commission) {
        return commissionRepository.save(commission);
    }

    public Commission update(UUID id, Commission commission) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission != null) {

            commissionRepository.save(existingCommission);
        }
        return existingCommission;
    }

    public void remove(UUID id) {
        commissionRepository.deleteById(id);
    }
}
