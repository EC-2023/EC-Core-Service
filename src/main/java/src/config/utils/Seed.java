package src.config.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import src.repository.*;

// Mo cmt de tao data
//@Component
public class Seed {
    @PersistenceContext
    private EntityManager entityManager;
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
//        // Add role
//        List<Role> roles = new ArrayList<>();
//        roles.add(new Role(Constant.UUID_ADMIN, Constant.ADMIN));
//        roles.add(new Role(Constant.UUID_USER, Constant.USER));
//        roles.add(new Role(Constant.UUID_VENDOR, Constant.VENDOR));
//        role.saveAll(roles);
//
//        // Add user level
//        List<UserLevel> userLevels = new ArrayList<>();
//        userLevels.add(new UserLevel(Constant.UUID_BRONZE, "Đồng", 100, 0.01));
//        userLevels.add(new UserLevel(Constant.UUID_SILVER, "Bạc", 500, 0.03));
//        userLevels.add(new UserLevel(Constant.UUID_GOLD, "Vàng", 1000, 0.05));
//        userLevels.add(new UserLevel(Constant.UUID_DIAMOND, "Kim Cương", 200, 0.08));
//        userLevel.saveAll(userLevels);
//
//        //Add commission
//        List<Commission> commissions = new ArrayList<>();
//        commissions.add(new Commission(Constant.UUID_BRONZE, "Cấp 1", 0.08, "Hoa hồng level 1"));
//        commissions.add(new Commission(Constant.UUID_SILVER, "Cấp 2", 0.05, "Hoa hồng level 2"));
//        commissions.add(new Commission(Constant.UUID_GOLD, "Cấp 3", 0.03, "Hoa hồng level 3"));
//        commission.saveAll(commissions);
//
//        //Add delivery
//        List<Delivery> deliveries = new ArrayList<>();
//        deliveries.add(new Delivery(UUID.randomUUID(), "GHTK", 0.01, "Giao hàng tiết kiệm"));
//        deliveries.add(new Delivery(UUID.randomUUID(), "GHN", 0.03, "Giao hàng nhanh"));
//        deliveries.add(new Delivery(UUID.randomUUID(), "GHHT", 0.08, "Giao hàng hỏa tốc"));
//        delivery.saveAll(deliveries);
//
//        // Add shop level
//
//        // Add user level


        ////////////////////////////
        insertRoles();
        insertUserLevels();
        insertCommissions();
        insertDeliveries();
    }


    private void insertRoles() {
        String sql = "INSERT INTO role (id, name, is_deleted, create_at, update_at) VALUES" +
                " ('" + Constant.UUID_ADMIN + "', '" + Constant.ADMIN + "', false, now(), now())," +
                " ('" + Constant.UUID_USER + "', '" + Constant.USER + "', false, now(), now())," +
                " ('" + Constant.UUID_VENDOR + "', '" + Constant.VENDOR + "', false, now(), now())" +
                " ON CONFLICT (id) DO NOTHING;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    private void insertUserLevels() {
        String sql = "INSERT INTO user_level (id, name, point_required, discount, is_deleted, create_at, update_at) VALUES" +
                " ('" + Constant.UUID_BRONZE + "', 'Đồng', 100, 0.01, false, now(), now())," +
                " ('" + Constant.UUID_SILVER + "', 'Bạc', 500, 0.03, false, now(), now())," +
                " ('" + Constant.UUID_GOLD + "', 'Vàng', 1000, 0.05, false, now(), now())," +
                " ('" + Constant.UUID_DIAMOND + "', 'Kim Cương', 200, 0.08, false, now(), now())" +
                " ON CONFLICT (id) DO NOTHING;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    private void insertCommissions() {
        String sql = "INSERT INTO commission (id, name, rate, description, is_deleted, create_at, update_at) VALUES" +
                " ('" + Constant.UUID_BRONZE + "', 'Cấp 1', 0.08, 'Hoa hồng level 1', false, now(), now())," +
                " ('" + Constant.UUID_SILVER + "', 'Cấp 2', 0.05, 'Hoa hồng level 2', false, now(), now())," +
                " ('" + Constant.UUID_GOLD + "', 'Cấp 3', 0.03, 'Hoa hồng level 3', false, now(), now())" +
                " ON CONFLICT (id) DO NOTHING;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    private void insertDeliveries() {
        String sql = "INSERT INTO delivery (id, name, price, description, is_deleted, create_at, update_at) VALUES" +
                " (uuid_generate_v4(), 'GHTK', 0.01, 'Giao hàng tiết kiệm', false, now(), now())," +
                " (uuid_generate_v4(), 'GHN', 0.03, 'Giao hàng nhanh', false, now(), now())," +
                " (uuid_generate_v4(), 'GHHT', 0.08, 'Giao hàng hỏa tốc', false, now(), now())" +
                " ON CONFLICT (id) DO NOTHING;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
