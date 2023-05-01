

package src.service.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.ICartItemsService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    private final ICartRepository cartRepository;

    private final ICartItemsRepository cartItemsRepository;
    private final IUserRepository userRepository;
    private final ModelMapper toDto;
    final
    ICartItemsService cartItemsService;
    final
    IProductRepository productRepository;
    private final IAttributeValueRepository attributeValueRepository;
    private final IAttributeRepository attributeRepository;

    @PersistenceContext
    EntityManager em;

    public CartService(ICartRepository cartRepository, ICartItemsRepository cartItemsRepository, IUserRepository userRepository, ModelMapper toDto, ICartItemsService cartItemsService, IProductRepository productRepository, IAttributeValueRepository attributeValueRepository, IAttributeRepository attributeRepository) {
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.userRepository = userRepository;
        this.toDto = toDto;
        this.cartItemsService = cartItemsService;
        this.productRepository = productRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.attributeRepository = attributeRepository;
    }

    @Async
    @Override
    public CompletableFuture<List<CartDto>> getAll() {
        return CompletableFuture.completedFuture(
                cartRepository.findAll().stream().map(
                        x -> toDto.map(x, CartDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    @Override
    public CompletableFuture<CartDto> getOne(UUID id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (cartOptional.isEmpty()) {
            throw new NotFoundException("Not found cart by id " + id);
        }

        return CompletableFuture.completedFuture(toDto.map(cartOptional.get(), CartDto.class));
    }


    @Async
    @Override
    public CompletableFuture<PagedResultDto<CartDto>> getMyCarts(HttpServletRequest request, Integer limit, Integer skip) {
        UUID userId = ((UUID) (request.getAttribute("id")));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user by id " + userId));
        request.setAttribute("custom", request.getQueryString() + "&userId%7B%7Beq%7D%7D=" + userId);
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Cart> features = new ApiQuery<>(request, em, Cart.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x ->
                        {
                            Hibernate.initialize(x.getCartItemsByCartId());
                            return toDto.map(x, CartDto.class);
                        }
                ).toList()));
    }

    @Async
    @Override
    public CompletableFuture<CartDto> getOneByUserId(UUID userId) {

        List<Cart> carts = cartRepository.findCartsByUserId(userId);

        carts.sort((cart1, cart2) -> cart2.getUpdateAt().compareTo(cart1.getUpdateAt()));

        if (carts.size() > 0) {
            return CompletableFuture.completedFuture(toDto.map(carts.get(0), CartDto.class));
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<CartDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = cartRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);

        ApiQuery<Cart> features = new ApiQuery<>(request, em, Cart.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CartDto.class)).toList()));
    }

    @Async
    @Override
    public CompletableFuture<CartDto> create(CartCreateDto input) {
        return null;
    }

    @Async
    @Override
    public CompletableFuture<CartDto> create(UUID userId) {
        Cart cart = new Cart(userId);
        return CompletableFuture.completedFuture(toDto.map(cartRepository.save(cart), CartDto.class));
    }

    @Async
    @Override
    public CompletableFuture<CartDto> update(UUID id, CartUpdateDto cart) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart == null)
            throw new NotFoundException("Unable to find cart!");
        BeanUtils.copyProperties(cart, existingCart);
        existingCart.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(cartRepository.save(existingCart), CartDto.class));
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(UUID id) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart == null)
            throw new NotFoundException("Unable to find cart!");
        existingCart.setIsDeleted(true);
        existingCart.setUpdateAt(new Date(new java.util.Date().getTime()));
        cartRepository.save(toDto.map(existingCart, Cart.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<CartItemsDto> addToCart(CartItemsCreateDto cartItemsCreateDto, UUID userId) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Product product = productRepository.findById(cartItemsCreateDto.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
        Cart cart = cartRepository.findCartsByUserIdAndStoreId(userId, product.getStoreId());
        if (cart == null) {
            cart = new Cart(userId, product.getStoreId());
            cart = cartRepository.save(cart);
        }
        List<CartItems> cartItems = cartItemsRepository.findByCartItemByProductId(cartItemsCreateDto.getProductId());
        if (cartItems.size() > 0) {
            for (CartItems cartItem : cartItems) {
                StringBuilder check = new StringBuilder();
                for (AttributeValue attributeValue : cartItem.getAttributeValuesByCartItemId()) {
                    check.append(attributeValue.getName()).append(", ");
                }
                for (String val : cartItemsCreateDto.getAttributesValues()) {
                    if (check.toString().contains(val)) {
                        cartItem.setQuantity(cartItem.getQuantity() + cartItemsCreateDto.getQuantity());
                        return CompletableFuture.completedFuture(toDto.map(cartItemsRepository.save(cartItem), CartItemsDto.class));
                    }
                }
            }
        }
        CartItems cartItem = toDto.map(cartItemsCreateDto, CartItems.class);
        cartItem.setCartId(cart.getId());
        cartItem = cartItemsRepository.save(cartItem);
        if (cartItemsCreateDto.getAttributesValues().size() > 0) {
            List<AttributeValue> attributeValues = new ArrayList<>();
            for (String attributeValue : cartItemsCreateDto.getAttributesValues()) {
                attributeValues.add(new AttributeValue(null, cartItem.getId(), null, null, attributeValue));
            }
            attributeValueRepository.saveAll(attributeValues);
        }
        return CompletableFuture.completedFuture(toDto.map(cartItems, CartItemsDto.class));
    }

    @Override
    @Transactional
    public CompletableFuture<Boolean> removeFromCart(UUID cartItemID, UUID userId) {
        CartItems cartItem = cartItemsRepository.findById(cartItemID).orElseThrow(() -> new NotFoundException("Not found cart item"));
        Hibernate.initialize(cartItem.getCartByCartId());
        if (cartItem.getCartByCartId().getUserId().equals(userId)) {
            attributeValueRepository.deleteByCartItemId(cartItemID);
            cartItemsRepository.delete(cartItem);
            return CompletableFuture.completedFuture(true);
        }
        throw new BadRequestException("Don't have permission to delete this cart item");
    }

    @Override
    @Transactional
    public CompletableFuture<Boolean> removeAllCart(UUID userId) {
        List<Cart> carts = cartRepository.findCartsByUserId(userId);
        for (Cart cart : carts) {
            List<CartItems> cartItems = cartItemsRepository.findByCartId(cart.getId());
            for (CartItems cartItem : cartItems) {
                Hibernate.initialize(cartItem.getCartByCartId());
                attributeValueRepository.deleteByCartItemId(cartItem.getId());
                cartItemsRepository.delete(cartItem);
            }
        }
        return CompletableFuture.completedFuture(true);
    }

}

