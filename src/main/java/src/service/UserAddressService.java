
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.UserAddress;
import src.repository.IUserAddressRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAddressService {
    private  IUserAddressRepository useraddressRepository;
    @Autowired
    public UserAddressService(IUserAddressRepository useraddressRepository) {
        this.useraddressRepository = useraddressRepository;
    }

    public List<UserAddress> getAll() {
        return (List<UserAddress>) useraddressRepository.findAll();
    }

    public Optional<UserAddress> getOne(UUID id) {
        return useraddressRepository.findById(id);
    }

    public UserAddress create(UserAddress useraddress) {
        return useraddressRepository.save(useraddress);
    }

    public UserAddress update(UUID id, UserAddress useraddress) {
        UserAddress existingUserAddress = useraddressRepository.findById(id).orElse(null);
        if (existingUserAddress != null) {

            useraddressRepository.save(existingUserAddress);
        }
        return existingUserAddress;
    }

    public void remove(UUID id) {
        useraddressRepository.deleteById(id);
    }
}
