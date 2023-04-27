

package src.service.Orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.BadRequestException;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.config.utils.Constant;
import src.config.utils.Utils;
import src.model.*;
import src.repository.*;
import src.service.Delivery.IDeliveryService;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;
import src.service.Orders.Dtos.PayLoadOrder;
import src.service.User.IUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class OrdersService implements IOrdersService {
    private final IOrdersRepository ordersRepository;
    private final ModelMapper toDto;
    @PersistenceContext
    EntityManager em;
    private final IUserRepository userRepository;
    private final IStoreRepository storeRepository;
    private final IDeliveryService deliveryService;
    private final ICartItemsRepository cartItemsRepository;
    private final IUserService userService;
    private final IOrderItemsRepository orderItemsRepository;
    private final IProductRepository productRepository;
    private final IAttributeValueRepository attributeValueRepository;
    private final IUserLevelRepository userLevelRepository;
    private final IStoreLevelRepository storeLevelRepository;

    public OrdersService(ModelMapper toDto, IOrdersRepository ordersRepository,
                         IUserRepository userRepository,
                         IStoreRepository storeRepository, IDeliveryService deliveryService,
                         ICartItemsRepository cartItemsRepository, IUserService userService, IOrderItemsRepository orderItemsRepository,
                         IProductRepository productRepository, IAttributeValueRepository attributeValueRepository, IUserLevelRepository userLevelRepository, IStoreLevelRepository storeLevelRepository) {
        this.toDto = toDto;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.deliveryService = deliveryService;
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.userLevelRepository = userLevelRepository;
        this.storeLevelRepository = storeLevelRepository;
    }

    @Async
    @Override
    public CompletableFuture<List<OrdersDto>> getAll() {
        return CompletableFuture.completedFuture(
                ordersRepository.findAll().stream().map(
                        x -> toDto.map(x, OrdersDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    @Override
    public CompletableFuture<OrdersDto> getOne(UUID id) {
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find orders!"));
        return CompletableFuture.completedFuture(toDto.map(order, OrdersDto.class));
    }

    @Async
    @Override
    public CompletableFuture<OrdersDto> getOne(UUID id, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Unable to find user!"));
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find orders!"));
        Hibernate.initialize(order.getStoreByStoreId());
        if (!order.getUserId().equals(userId)
                || !order.getStoreByStoreId().getOwnId().equals(userId)
                || !user.getRoleByRoleId().getName().equals(Constant.ADMIN)) {
            throw new BadRequestException("You don't have permission to do that!");
        }
        if (!user.getRoleByRoleId().getName().equals(Constant.ADMIN)) {
            order.setAmountToGd(0);
        }
        if (!order.getStoreByStoreId().getOwnId().equals(userId)) {
            order.setAmountToStore(0);
        }
        return CompletableFuture.completedFuture(toDto.map(order, OrdersDto.class));
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<OrdersDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ordersRepository.count();
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Orders> features = new ApiQuery<>(request, em, Orders.class, pagination);
        long total = features.filter().orderBy().exec().size();
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, OrdersDto.class)).toList()));
    }

    @Async
    @Override
    public CompletableFuture<OrdersDto> create(OrdersCreateDto input) {
        Orders orders = ordersRepository.save(toDto.map(input, Orders.class));
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(orders), OrdersDto.class));
    }

    @Async
    @Override
    public CompletableFuture<OrdersDto> update(UUID id, OrdersUpdateDto orders) {
        Orders existingOrders = ordersRepository.findById(id).orElse(null);
        if (existingOrders == null)
            throw new NotFoundException("Unable to find orders!");
        BeanUtils.copyProperties(orders, existingOrders);
        existingOrders.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(existingOrders), OrdersDto.class));
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(UUID id) {
        Orders existingOrders = ordersRepository.findById(id).orElse(null);
        if (existingOrders == null)
            throw new NotFoundException("Unable to find orders!");
        existingOrders.setIsDeleted(true);
        existingOrders.setUpdateAt(new Date(new java.util.Date().getTime()));
        ordersRepository.save(toDto.map(existingOrders, Orders.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<List<OrdersDto>> getMyOrders(UUID id) {
        return CompletableFuture.completedFuture(
                ordersRepository.getMyOrders(id).stream().map(
                        x -> toDto.map(x, OrdersDto.class)
                ).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public CompletableFuture<OrdersDto> addMyOrder(UUID userId, PayLoadOrder input) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user"));
        if (!user.isEnabled() || user.getIsDeleted())
            throw new BadRequestException("User is not active");

        Store store = storeRepository.findById(input.getStoreId()).orElseThrow(() -> new NotFoundException("Not found store"));

        if (store.getIsDeleted())
            throw new BadRequestException("Store is not active");


        String address = input.getAddress();
        address = address.split(",")[address.split(",").length - 1].trim();


        long priceDelivery = deliveryService.calcPrice(input.getDeliveryId(), address, input.getOrders());
        long total = 0;
        Orders order;

        if (input.getOption() == 1) {
            // khong  can xoa trong cart item
            CartItems cartItems = input.getOrders().get(0);
            Product prod = productRepository.findById(cartItems.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
            if (cartItems.getQuantity() > prod.getQuantity())
                throw new BadRequestException("Quantity product is not enough");

            double discount = user.getUserLevelByUserLevelId().getDiscount();
            if (prod.getDateValidPromote() != null)
                total += (new Date(new java.util.Date().getTime())).compareTo(prod.getDateValidPromote()) < 0 ? cartItems.getQuantity() * prod.getPromotionalPrice() : cartItems.getQuantity() * prod.getPrice();
            else
                total += cartItems.getQuantity() * prod.getPrice();
            double amountFromUser = total - total * discount + priceDelivery;
            double amountToGd = store.getStoreLevelByStoreLevelId().getDiscount() * (total - total * discount);
            double amountToStore = total - amountToGd;
            if (!input.isCOD() && user.getEWallet() < amountFromUser) {
                throw new BadRequestException("Not enough money in your wallet");
            }
            prod.setQuantity(prod.getQuantity() - cartItems.getQuantity());

            productRepository.save(prod);
            order = getOrders(userId, input, amountFromUser, amountToGd, amountToStore);
            OrderItems orderItems = new OrderItems(cartItems.getProductId(), cartItems.getQuantity(), order.getId());
            orderItemsRepository.save(orderItems);
        } else {
//            xoa trong cart item
            for (CartItems cartItems : input.getOrders()) {
                Product prod = productRepository.findById(cartItems.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
                if (cartItems.getQuantity() > cartItems.getProductByProductId().getQuantity())
                    throw new BadRequestException("Quantity product is not enough");
                if (prod.getDateValidPromote() != null)
                    total += (new Date(new java.util.Date().getTime())).compareTo(prod.getDateValidPromote()) < 0 ? cartItems.getQuantity() * prod.getPromotionalPrice() : cartItems.getQuantity() * prod.getPrice();
                else
                    total += cartItems.getQuantity() * prod.getPrice();
            }
            double discount = userService.getDiscountFromUserLevel(user.getId());
            double amountToGd = store.getStoreLevelByStoreLevelId().getDiscount() * (total - total * discount);
            double amountFromUser = total - total * discount + priceDelivery;
            double amountToStore = total - total * discount - amountToGd;
            if (!input.isCOD() && user.getEWallet() < amountFromUser) {
                throw new BadRequestException("Not enough money in your wallet");
            }
            order = getOrders(userId, input, amountFromUser, amountToGd, amountToStore);

            List<OrderItems> orderItems = new ArrayList<>();
            List<AttributeValue> attributeValues = new ArrayList<>();
            for (CartItems cartItems : input.getOrders()) {
                Product prod = productRepository.findById(cartItems.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
                prod.setQuantity(prod.getQuantity() - cartItems.getQuantity());
                productRepository.save(prod);
                OrderItems orderItem = new OrderItems(cartItems.getProductId(), cartItems.getQuantity(), order.getId());
                orderItem = orderItemsRepository.save(orderItem);
                OrderItems finalOrderItem = orderItem;
                Hibernate.initialize(finalOrderItem.getAttributesValueByOrderItemId());
                cartItems.getAttributeValuesByCartItemId().forEach(x -> {
                    x.setCartItem_id(null);
                    x.setOrderItem_id(finalOrderItem.getId());
                    attributeValues.add(x);
                });
            }
            attributeValueRepository.saveAll(attributeValues);
            List<UUID> ids = List.of(input.getOrders().stream().map(CartItems::getId).toArray(UUID[]::new));
            cartItemsRepository.deleteAllById(ids);
        }
        return CompletableFuture.completedFuture(toDto.map(order, OrdersDto.class));
    }

    private Orders getOrders(UUID userId, PayLoadOrder input, double amountFromUser, double amountToGd, double amountToStore) {
        Orders order;
        order = new Orders(userId, input.getStoreId(), input.getDeliveryId(), input.getAddress(), input.getPhone(), 0, !input.isCOD(), amountFromUser, amountToStore, amountToGd);
        String code = Utils.generateCodeOrder();
        while (true) {
            if (ordersRepository.countByOrderNumber(code) == null || ordersRepository.countByOrderNumber(code) == 0) {
                code = Utils.generateCodeOrder();
            } else {
                order.setCode(code);
                break;
            }
        }
        order = ordersRepository.save(order);
        return order;
    }

    @Async
    public CompletableFuture<OrdersDto> acceptOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order"));
        //Get OwnerId
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found store"));
        //Check userId == OwnerId
        if (userId.equals(store.getOwnId())) {
            if (order.getStatus() == OrderStatus.NOT_PROCESSED.ordinal()) {
                order.setStatus(OrderStatus.PROCESSING.ordinal());
            } else if (order.getStatus() == OrderStatus.PROCESSING.ordinal()) {
                order.setStatus(OrderStatus.DELIVERING.ordinal());
            } else {
                throw new BadRequestException("Can't change status");
            }
        } else {
            throw new BadRequestException("Only store owner can change order status");
        }
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));
    }

    @Async
    public CompletableFuture<OrdersDto> cancelOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order"));
        //Get OwnerId
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found store"));
        User buyer = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found buyer"));
        //Check userId == OwnerId
        if (userId.equals(store.getOwnId())
                || userId.equals(order.getUserId())
                || buyer.getRoleByRoleId().getName().equals(Constant.ADMIN)) {
            long dateCreateDiff = new Date().getTime() - order.getCreateAt().getTime();
            long dateUpdateDiff = new Date().getTime() - order.getUpdateAt().getTime();
            int point = (int) Math.floor(order.getAmountFromUser() / 5000);

            //Thang mua
            if (order.getStatus() == OrderStatus.PROCESSING.ordinal() ||
                    (order.getStatus() == OrderStatus.DELIVERING.ordinal() && dateUpdateDiff > 15 * 24 * 60 * 60 * 1000)) {

                if (order.isPaidBefore()) {
                    buyer.setEWallet(buyer.getEWallet() + order.getAmountFromUser());
                }

                if (dateCreateDiff <= 15 * 24 * 60 * 60 * 1000) {
                    if (userId.equals(buyer.getId())) {
                        buyer.setPoint(buyer.getPoint() - point);
                        userRepository.save(buyer);
                        updateUserLevel(buyer.getId());
                    } else if (userId.equals(store.getOwnId())) {
                        store.setPoint(store.getPoint() - point);
                        storeRepository.save(store);
                        updateStoreLevel(store.getId());
                    }
                }
                order.setStatus(OrderStatus.CANCELLED.ordinal());
            } else {
                throw new BadRequestException("Can't change status");
            }

        } else {
            throw new BadRequestException("Not allow to cancel order");
        }
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));
    }

    @Async
    public CompletableFuture<OrdersDto> finishOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order"));
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found store"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found buyer"));
        //Check userId == OwnerId
        User buyer = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found buyer"));

        if (user.getRoleByRoleId().getName().equals(Constant.ADMIN) || order.getUserId().equals(user.getId())) {
            Hibernate.initialize(user.getRoleByRoleId());
            Hibernate.initialize(order.getItem());
            store.setEWallet(store.getEWallet() + order.getAmountToStore());

            int point = (int) Math.floor(order.getAmountFromUser() / 10000);

            //Update user, store point
            buyer.setPoint(buyer.getPoint() + point);
            store.setPoint(store.getPoint() + point);

            //Update user, store level
            updateUserLevel(buyer.getId());
            updateStoreLevel(store.getId());

            userRepository.save(buyer);
            storeRepository.save(store);
            order.setStatus(OrderStatus.DELIVERED.ordinal());
            List<Product> productList = new ArrayList<>();
            for (OrderItems item : order.getItem()) {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new NotFoundException("Not found product"));
                product.setQuantity(product.getQuantity() - item.getQuantity());
                product.setQuantity(product.getSold() - item.getQuantity());
                productList.add(product);
            }
            if (productList.size() > 0) {
                productRepository.saveAll(productList);
            }
        } else {
            throw new RuntimeException("Only store admin or buyer can finish order");
        }
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));
    }

    private void updateUserLevel(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found userId"));
        List<UserLevel> userLevels = userLevelRepository.findAll();
        for (UserLevel userLevel : userLevels) {
            if (user.getPoint() >= userLevel.getMinPoint()) {
                user.setUserLevelId(userLevel.getId());
            } else {
                break;
            }
        }
        userRepository.save(user);
    }

    private void updateStoreLevel(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Not found storeId"));
        List<StoreLevel> storeLevels = storeLevelRepository.findAll();
        for (StoreLevel storeLevel : storeLevels) {
            if (store.getPoint() >= storeLevel.getMinPoint()) {
                store.setStoreLevelId(storeLevel.getId());
            } else {
                break;
            }
        }
        storeRepository.save(store);
    }
}

