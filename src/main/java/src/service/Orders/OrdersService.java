

package src.service.Orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
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
import src.model.*;
import src.repository.*;
import src.service.Delivery.IDeliveryService;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;
import src.service.Orders.Dtos.PayLoadOrder;
import src.service.User.IUserService;

import java.util.*;
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
    private final IUserLevelRepository userLevelRepository;
    private final IStoreLevelRepository storeLevelRepository;

    public OrdersService(ModelMapper toDto, IOrdersRepository ordersRepository,
                         IUserRepository userRepository,
                         IStoreRepository storeRepository, IDeliveryService deliveryService,
                         ICartItemsRepository cartItemsRepository, IUserService userService, IOrderItemsRepository orderItemsRepository,
                         IProductRepository productRepository, IUserLevelRepository userLevelRepository, IStoreLevelRepository storeLevelRepository) {
        this.toDto = toDto;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.deliveryService = deliveryService;
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
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
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.findById(id).get(), OrdersDto.class));
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<OrdersDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
         ordersRepository.count();
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Orders> features = new ApiQuery<>(request, em, Orders.class, pagination);
        long total =features.filter().orderBy().exec().size();
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

    @Async
    @Override
    @Transactional
    public CompletableFuture<OrdersDto> addMyOrder(UUID userId, PayLoadOrder input) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user"));
        if (!user.isEnabled() || user.getIsDeleted())
            throw new BadRequestException("User is not active");
        Store store = storeRepository.findById(input.getStoreId()).orElseThrow(() -> new NotFoundException("Not found store"));
        String address = input.getAddress();
        address = address.split(",")[address.split(",").length - 1].trim();
        if (store.getIsDeleted())
            throw new BadRequestException("Store is not active");
        long priceDelivery = deliveryService.calcPrice(input.getDeliveryId(), address, input.getOrders());
        long total = 0;
        Orders order;
        if (input.getOption() == 1) {
            // khong  can xoa trong cart item
            CartItems cartItems = input.getOrders().get(0);
            Product prod = productRepository.findById(cartItems.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
            if (cartItems.getQuantity() > cartItems.getProductByProductId().getQuantity())
                throw new BadRequestException("Quantity product is not enough");
            double discount = user.getUserLevelByUserLevelId().getDiscount();
            total += (new Date(new java.util.Date().getTime())).compareTo(cartItems.getProductByProductId().getDateValidPromote()) < 0 ? cartItems.getQuantity() * cartItems.getProductByProductId().getPromotionalPrice() : cartItems.getQuantity() * cartItems.getProductByProductId().getPrice();
            double amountFromUser = total - total * discount + priceDelivery;
            double amountToGd = store.getStoreLevelByStoreLevelId().getDiscount() * (total - total * discount);
            double amountToStore = total - amountToGd;
            if (input.isCOD() && user.getEWallet() < amountFromUser) {
                throw new BadRequestException("Not enough money in your wallet");
            }
            prod.setQuantity(prod.getQuantity() - cartItems.getQuantity());
            productRepository.save(prod);
            order = new Orders(userId, input.getStoreId(), input.getDeliveryId(), input.getAddress(), input.getPhone(), 0, !input.isCOD(), amountFromUser, amountToStore, amountToGd);
            order = ordersRepository.save(order);
            OrderItems orderItems = new OrderItems(cartItems.getProductId(), cartItems.getQuantity(), order.getId());
            orderItemsRepository.save(orderItems);
        } else {
//            xoa trong cart item
            for (CartItems cartItems : input.getOrders()) {
                if (cartItems.getQuantity() > cartItems.getProductByProductId().getQuantity())
                    throw new BadRequestException("Quantity product is not enough");
                total += (new Date(new java.util.Date().getTime())).compareTo(cartItems.getProductByProductId().getDateValidPromote()) < 0 ? cartItems.getQuantity() * cartItems.getProductByProductId().getPromotionalPrice() : cartItems.getQuantity() * cartItems.getProductByProductId().getPrice();
            }
            double discount = userService.getDiscountFromUserLevel(user.getId());
            double amountToGd = store.getStoreLevelByStoreLevelId().getDiscount() * (total - total * discount);
            double amountFromUser = total - total * discount + priceDelivery;
            double amountToStore = total - total * discount - amountToGd;
            if (input.isCOD() && user.getEWallet() < amountFromUser) {
                throw new BadRequestException("Not enough money in your wallet");
            }
            order = new Orders(userId, input.getStoreId(), input.getDeliveryId(), input.getAddress(), input.getPhone(), 0, !input.isCOD(), amountFromUser, amountToStore, amountToGd);
            order = ordersRepository.save(order);
            for (CartItems cartItems : input.getOrders()) {
                Product prod = productRepository.findById(cartItems.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
                if (cartItems.getQuantity() > cartItems.getProductByProductId().getQuantity())
                    throw new BadRequestException("Quantity product is not enough");
                prod.setQuantity(prod.getQuantity() - cartItems.getQuantity());
                productRepository.save(prod);
                total += (new Date(new java.util.Date().getTime())).compareTo(cartItems.getProductByProductId().getDateValidPromote()) < 0 ? cartItems.getQuantity() * cartItems.getProductByProductId().getPromotionalPrice() : cartItems.getQuantity() * cartItems.getProductByProductId().getPrice();
                OrderItems orderItems = new OrderItems(cartItems.getProductId(), cartItems.getQuantity(), order.getId());
                orderItemsRepository.save(orderItems);
            }
            List<UUID> ids = List.of(input.getOrders().stream().map(CartItems::getId).toArray(UUID[]::new));
            cartItemsRepository.deleteAllById(ids);
        }
        return CompletableFuture.completedFuture(toDto.map(order, OrdersDto.class));
    }

    public CompletableFuture<OrdersDto> acceptOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found productId"));
        //Get OwnerId
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found storeId"));
        //Check userId == OwnerId
        if (userId.equals(store.getOwnId())) {
            if (order.getStatus() == OrderStatus.NOT_PROCESSED.ordinal()) {
                order.setStatus(OrderStatus.PROCESSING.ordinal());
            } else if (order.getStatus() == OrderStatus.PROCESSING.ordinal()) {
                order.setStatus(OrderStatus.DELIVERING.ordinal());
            } else {
                throw new RuntimeException("Can't change status");
            }
        } else {
            throw new RuntimeException("Only store owner can change order status");
        }

        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));
    }

    public CompletableFuture<OrdersDto> cancelOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found productId"));
        //Get OwnerId
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found storeId"));
        User buyer = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found buyerId"));
        //Check userId == OwnerId
        if (userId.equals(store.getOwnId()) || userId.equals(order.getUserId())) {

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
                    buyer.setPoint(buyer.getPoint() - point);
                    store.setPoint(store.getPoint() - point);
                }

                userRepository.save(buyer);
                storeRepository.save(store);
                order.setStatus(OrderStatus.CANCELLED.ordinal());
            } else {
                throw new RuntimeException("Can't change status");
            }

        } else {
            throw new RuntimeException("Not allow to cancel order");
        }

        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));
    }

    public CompletableFuture<OrdersDto> finishOrder(UUID userId, UUID orderId) {
        //Get StoreId
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found orderId"));
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new NotFoundException("Not found storeId"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found userId"));
        Role role = user.getRoleByRoleId();
        User buyer = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new NotFoundException("Not found buyerId"));
        //Check userId == OwnerId
        if (role.getName().equals("Admin")) {

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
        } else {
            throw new RuntimeException("Only store admin can finish order");
        }

        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(order), OrdersDto.class));

    }

    private void updateUserLevel(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found userId"));
        List<UserLevel> userLevels = userLevelRepository.findAll();
        userLevels.sort(Comparator.comparingInt(UserLevel::getMinPoint).reversed());

        for (UserLevel userLevel : userLevels) {
            if (user.getPoint() >= userLevel.getMinPoint()) {
                user.setUserLevelId(userLevel.getId());
                userRepository.save(user);
                break;
            }
        }
    }

    private void updateStoreLevel(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Not found storeId"));
        List<StoreLevel> storeLevels = storeLevelRepository.findAll();
        storeLevels.sort(Comparator.comparingInt(StoreLevel::getMinPoint).reversed());

        for (StoreLevel storeLevel : storeLevels) {
            if (store.getPoint() >= storeLevel.getMinPoint()) {
                store.setStoreLevelId(storeLevel.getId());
                storeRepository.save(store);
                break;
            }
        }

    }

}

