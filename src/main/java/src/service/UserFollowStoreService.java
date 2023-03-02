
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.UserFollowStore;
import src.repository.IUserFollowStoreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserFollowStoreService {
    private  IUserFollowStoreRepository userfollowstoreRepository;
    @Autowired
    public UserFollowStoreService(IUserFollowStoreRepository userfollowstoreRepository) {
        this.userfollowstoreRepository = userfollowstoreRepository;
    }

    public List<UserFollowStore> getAll() {
        return (List<UserFollowStore>) userfollowstoreRepository.findAll();
    }

    public Optional<UserFollowStore> getOne(UUID id) {
        return userfollowstoreRepository.findById(id);
    }

    public UserFollowStore create(UserFollowStore userfollowstore) {
        return userfollowstoreRepository.save(userfollowstore);
    }

    public UserFollowStore update(UUID id, UserFollowStore userfollowstore) {
        UserFollowStore existingUserFollowStore = userfollowstoreRepository.findById(id).orElse(null);
        if (existingUserFollowStore != null) {

            userfollowstoreRepository.save(existingUserFollowStore);
        }
        return existingUserFollowStore;
    }

    public void remove(UUID id) {
        userfollowstoreRepository.deleteById(id);
    }
}
