package src.config.utils;

import org.springframework.beans.factory.annotation.Autowired;
import src.model.Commission;
import src.model.Delivery;
import src.model.Role;
import src.model.UserLevel;
import src.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Mo cmt de tao data
//@Component
public class Seed {
    @Autowired
    IUserRepository user;
    @Autowired
    IRoleRepository role;
    @Autowired
    IUserLevelRepository userLevel;
    @Autowired
    ICommissionRepository commission;
    @Autowired
    IDeliveryRepository delivery;
    @Autowired
    IStoreLevelRepository storeLevel;
    @Autowired
    IStoreRepository store;

    // Mo cmt de tao data
//    @PostConstruct
    public void Seeder() {
        // Add role
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Constant.UUID_ADMIN, Constant.ADMIN));
        roles.add(new Role(Constant.UUID_USER, Constant.USER));
        roles.add(new Role(Constant.UUID_VENDOR, Constant.VENDOR));
        role.saveAll(roles);

        // Add user level
        List<UserLevel> userLevels = new ArrayList<>();
        userLevels.add(new UserLevel(Constant.UUID_BRONZE, "Đồng", 100, 0.01));
        userLevels.add(new UserLevel(Constant.UUID_SILVER, "Bạc", 500, 0.03));
        userLevels.add(new UserLevel(Constant.UUID_GOLD, "Vàng", 1000, 0.05));
        userLevels.add(new UserLevel(Constant.UUID_DIAMOND, "Kim Cương", 200, 0.08));
        userLevel.saveAll(userLevels);

        //Add commission
        List<Commission> commissions = new ArrayList<>();
        commissions.add(new Commission(Constant.UUID_BRONZE, "Cấp 1", 0.08, "Hoa hồng level 1"));
        commissions.add(new Commission(Constant.UUID_SILVER, "Cấp 2", 0.05, "Hoa hồng level 2"));
        commissions.add(new Commission(Constant.UUID_GOLD, "Cấp 3", 0.03, "Hoa hồng level 3"));
        commission.saveAll(commissions);

        //Add delivery
        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(UUID.randomUUID(), "GHTK", 0.01, "Giao hàng tiết kiệm"));
        deliveries.add(new Delivery(UUID.randomUUID(), "GHN", 0.03, "Giao hàng nhanh"));
        deliveries.add(new Delivery(UUID.randomUUID(), "GHHT", 0.08, "Giao hàng hỏa tốc"));
        delivery.saveAll(deliveries);

        // Add shop level

        // Add user level
    }

}
