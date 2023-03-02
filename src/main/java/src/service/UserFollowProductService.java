
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.UserFollowProduct;
import src.repository.IUserFollowProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserFollowProductService {
    private  IUserFollowProductRepository userfollowproductRepository;
    @Autowired
    public UserFollowProductService(IUserFollowProductRepository userfollowproductRepository) {
        this.userfollowproductRepository = userfollowproductRepository;
    }

    public List<UserFollowProduct> getAll() {
        return (List<UserFollowProduct>) userfollowproductRepository.findAll();
    }

    public Optional<UserFollowProduct> getOne(UUID id) {
        return userfollowproductRepository.findById(id);
    }

    public UserFollowProduct create(UserFollowProduct userfollowproduct) {
        return userfollowproductRepository.save(userfollowproduct);
    }

    public UserFollowProduct update(UUID id, UserFollowProduct userfollowproduct) {
        UserFollowProduct existingUserFollowProduct = userfollowproductRepository.findById(id).orElse(null);
        if (existingUserFollowProduct != null) {

            userfollowproductRepository.save(existingUserFollowProduct);
        }
        return existingUserFollowProduct;
    }

    public void remove(UUID id) {
        userfollowproductRepository.deleteById(id);
    }
}
