
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.UserLevel;
import src.repository.IUserLevelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLevelService {
    private  IUserLevelRepository userlevelRepository;
    @Autowired
    public UserLevelService(IUserLevelRepository userlevelRepository) {
        this.userlevelRepository = userlevelRepository;
    }

    public List<UserLevel> getAll() {
        return (List<UserLevel>) userlevelRepository.findAll();
    }

    public Optional<UserLevel> getOne(UUID id) {
        return userlevelRepository.findById(id);
    }

    public UserLevel create(UserLevel userlevel) {
        return userlevelRepository.save(userlevel);
    }

    public UserLevel update(UUID id, UserLevel userlevel) {
        UserLevel existingUserLevel = userlevelRepository.findById(id).orElse(null);
        if (existingUserLevel != null) {

            userlevelRepository.save(existingUserLevel);
        }
        return existingUserLevel;
    }

    public void remove(UUID id) {
        userlevelRepository.deleteById(id);
    }
}
