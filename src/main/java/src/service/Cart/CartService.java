

package src.service.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.AttributeValue;
import src.model.Cart;
import src.model.CartItems;
import src.model.Product;
import src.repository.IAttributeValueRepository;
import src.repository.ICartItemsRepository;
import src.repository.ICartRepository;
import src.repository.IProductRepository;
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
    private final ModelMapper toDto;
    final
    ICartItemsService cartItemsService;
    final
    IProductRepository productRepository;
    private final IAttributeValueRepository attributeValueRepository;
    @PersistenceContext
    EntityManager em;

    public CartService(ICartRepository cartRepository, ICartItemsRepository cartItemsRepository, ModelMapper toDto, ICartItemsService cartItemsService, IProductRepository productRepository, IAttributeValueRepository attributeValueRepository) {
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.toDto = toDto;
        this.cartItemsService = cartItemsService;
        this.productRepository = productRepository;
        this.attributeValueRepository = attributeValueRepository;
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
    public CompletableFuture<List<CartDto>> getMyCarts(UUID userId) {
        List<Cart> carts = cartRepository.findCartsByUserId(userId);
        return CompletableFuture.completedFuture(carts.stream().map( x -> toDto.map(x, CartDto.class)).toList());
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

        Product product = productRepository.findById(cartItemsCreateDto.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
        Cart cart = cartRepository.findCartsByUserIdAndStoreId(userId, product.getStoreId());
        if (cart == null) {
            cart = new Cart(userId, product.getStoreId());
            cart = cartRepository.save(cart);
        }
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartItems cartItems = toDto.map(cartItemsCreateDto, CartItems.class);
        cartItems.setCartId(cart.getId());
        cartItems = cartItemsRepository.save(cartItems);
        if (cartItemsCreateDto.getAttributesValues().size() > 0) {
            List<AttributeValue> attributeValues = new ArrayList<>();
            for (String attributeValue : cartItemsCreateDto.getAttributesValues()) {
                attributeValues.add(new AttributeValue(null, cartItems.getId(), null, null, attributeValue));
            }
            attributeValueRepository.saveAll(attributeValues);
        }
        return CompletableFuture.completedFuture(toDto.map(cartItems, CartItemsDto.class));
    }

    @Async
    @Override
    public CompletableFuture<Boolean> removeFromCart(UUID productId, UUID userId) {

//        CartDto cartDto = null;
//        try {
//            cartDto = getOneByUserId(userId).get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        if (cartDto != null) {
//            return cartItemsService.removeByCartIdAndProductId(cartDto.getId(), productId);
//        }
//
//        return CompletableFuture.completedFuture(false);
        return null;
    }

}

