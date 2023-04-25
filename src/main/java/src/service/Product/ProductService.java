

package src.service.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.config.utils.Constant;
import src.config.utils.NullAwareBeanUtilsBean;
import src.model.*;
import src.repository.*;
import src.service.Product.Dtos.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ModelMapper toDto;

    private final IStoreRepository storeRepository;
    private final IUserRepository userRepository;
    private final IAttributeRepository attributeRepository;
    private final IAttributeValueRepository attributeValueRepository;
    private final ICategoryRepository categoryRepository;
    @PersistenceContext
    EntityManager em;
    private final IProductImgRepository iProductImgRepository;

    public ProductService(IProductRepository productRepository, ModelMapper toDto, IStoreRepository storeRepository, IUserRepository userRepository, IAttributeRepository attributeRepository, IAttributeValueRepository attributeValueRepository,
                          ICategoryRepository categoryRepository, IProductImgRepository iProductImgRepository) {
        this.productRepository = productRepository;
        this.toDto = toDto;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.categoryRepository = categoryRepository;
        this.iProductImgRepository = iProductImgRepository;
    }

    @Async
    public CompletableFuture<List<ProductDto>> getAll() {
        return CompletableFuture.completedFuture(
                productRepository.findAll().stream().map(
                        x -> toDto.map(x, ProductDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ProductDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(productRepository.findById(id).get(), ProductDto.class));
    }

    @Transactional
    @Override
    public CompletableFuture<ProductDetailDto> getDetailProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find product!"));
        Hibernate.initialize(product.getAttributesByProductId());
        Hibernate.initialize(product.getReviewsByProductId());
        Hibernate.initialize(product.getStoreByStoreId());
        Hibernate.initialize(product.getCategoryByCategoryId());
        Hibernate.initialize(product.getProductImgsByProductId());
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(toDto.map(product, ProductDetailDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<ProductDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = productRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Product> features = new ApiQuery<>(request, em, Product.class, pagination);
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, ProductDto.class)).toList()));
    }

    @Async
    public CompletableFuture<ProductDto> create(ProductCreateDto input) {
        return null;
    }

    @Async
    public CompletableFuture<ProductDto> create(UUID userId, ProductCreatePayload input) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Unable to find user!"));
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Unable to find store!"));
        Product product = toDto.map(input, Product.class);
        product.setStoreId(store.getId());
        product = productRepository.save(product);
        // them tat ca attribute
        if (input.getAttributes().size() > 0) {
            for (AttributePayload attribute : input.getAttributes()) {
                List<AttributeValue> attributeValues = new ArrayList<>();
                Attribute attribute1 = attributeRepository.save(new Attribute(product.getId(), attribute.getName()));
                for (String value : attribute.getValues()) {
                    attributeValues.add(new AttributeValue(attribute1.getId(), null, null, null, value));
                }
                if (attributeValues.size() > 0)
                    attributeValueRepository.saveAll(attributeValues);
            }
        }
        // them tat ca anh
        List<ProductImg> productImgs = new ArrayList<>();
        if (input.getImages().size() > 0) {
            for (String img : input.getImages()) {
                productImgs.add(new ProductImg(product.getId(), img, img));
            }
            iProductImgRepository.saveAll(productImgs);
        }
        return CompletableFuture.completedFuture(toDto.map(product, ProductDto.class));
    }

    @Async
    public CompletableFuture<ProductDto> update(UUID id, ProductUpdateDto product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new NotFoundException("Unable to find product!");
        BeanUtils.copyProperties(product, existingProduct);
        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));

        return CompletableFuture.completedFuture(toDto.map(productRepository.save(existingProduct), ProductDto.class));
    }

    @Async
    public CompletableFuture<ProductCreatePayload> update(UUID id, PayLoadUpdateProduct product) throws InvocationTargetException, IllegalAccessException {

        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find product!"));
        BeanUtilsBean nullAwareBeanUtilsBean = NullAwareBeanUtilsBean.getInstance();
        nullAwareBeanUtilsBean.copyProperties(existingProduct, product.getBasic());
        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        existingProduct = productRepository.save(existingProduct);


        Hibernate.initialize(existingProduct.getAttributesByProductId());
        Hibernate.initialize(existingProduct.getProductImgsByProductId());
        // check add image
        List<ProductImg> productImgs = new ArrayList<>();

        if (product.getListAddImage().size() > 0) {
            for (String img : product.getListAddImage()) {
                productImgs.add(new ProductImg(existingProduct.getId(), img, img));
            }
            iProductImgRepository.saveAll(productImgs);
        }
        // check remove image
        if (product.getListRemoveImage().size() > 0) {
            productImgs = new ArrayList<>();
            for (UUID img : product.getListRemoveImage()) {
                ProductImg productImg = iProductImgRepository.findById(img).orElseThrow(() -> new NotFoundException("Unable to find image!"));
                productImg.setIsDeleted(true);
                productImgs.add(productImg);
            }
            iProductImgRepository.saveAll(productImgs);
        }
        // check remove attribute
        if (product.getListRemoveImage().size() > 0) {
            productImgs = new ArrayList<>();
            for (UUID img : product.getListRemoveImage()) {
                ProductImg productImg = iProductImgRepository.findById(img).orElseThrow(() -> new NotFoundException("Unable to find image!"));
                productImg.setIsDeleted(true);
                productImgs.add(productImg);
            }
            iProductImgRepository.saveAll(productImgs);
        }
        return CompletableFuture.completedFuture(toDto.map(productRepository.save(existingProduct), ProductCreatePayload.class));

        // set laij attribute
//        if (product.getAttributes().size() > 0) {
//            for (AttributeUpdate attribute : product.getAttributes()) {
//                if (attribute.getIsDeleted()) {
//                    // xoa toan bo attribute value lien quan
//                    List<AttributeValue> attributeValues = attributeValueRepository.findAllByAttributeId(attribute.getId());
//                    attributeValues = attributeValues.stream().peek(x -> x.setIsDeleted(true)).toList();
//                    if (attributeValues.size() > 0)
//                        attributeValueRepository.saveAll(attributeValues);
//                    Attribute attr = attributeRepository.findById(attribute.getId()).orElseThrow(() -> new NotFoundException("Unable to find attribute!"));
//                    attr.setIsDeleted(true);
//                    attributeRepository.save(attr);
//                } else {
//                    // neu chua co se tao moi
//                    Attribute attr = attributeRepository.findById(attribute.getId()).orElse(null);
//                    if (attr == null) {
//                        attr = new Attribute(existingProduct.getId(), attribute.getName());
//                    } else {
//                        attr.setName(attribute.getName());
//                    }
//                    attributeRepository.save(attr);
//                }
//            }
//        }
// set lai attribute value
        // set laij image
//        List<ProductImg> productImgs = new ArrayList<>();
//        if (product.getImages().size() > 0) {
//            ProductImg productImg;
//            for (ProductImg img : product.getImages()) {
//                if (img.getIsDeleted()) {
//                    productImg = iProductImgRepository.findById(img.getId()).orElseThrow(() -> new NotFoundException("Unable to find product image!"));
//                    productImg.setIsDeleted(true);
//                    iProductImgRepository.save(productImg);
//                } else {
//                    productImgs.add(new ProductImg(existingProduct.getId(), img.getFileName(), img.getFileName()));
//                }
//            }
//            if (productImgs.size() > 0)
//                iProductImgRepository.saveAll(productImgs);
//        }
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(UUID id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new NotFoundException("Unable to find product!");
        existingProduct.setIsDeleted(true);
        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        productRepository.save(toDto.map(existingProduct, Product.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<ProductDto> updateQuantity(UUID userId, UUID productId, int quantity) {
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Unable to find store!"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Unable to find product!"));
        if (product.getStoreId().equals(store.getId()))
            throw new NotFoundException("Product not belong to store!");
        product.setQuantity(quantity);
        product.setUpdateAt(new Date(new java.util.Date().getTime()));
        product = productRepository.save(product);
        return CompletableFuture.completedFuture(toDto.map(product, ProductDto.class));
    }

    public CompletableFuture<ProductDto> updateStatusProduct(UUID userId, UUID productId, boolean status) {
        Store store = storeRepository.findByUserId(userId).orElse(null);
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Unable to find product!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Unable to find user!"));
        if (store == null && !user.getRoleByRoleId().getName().equals(Constant.ADMIN)) {
            throw new NotFoundException("Unable to find store!");
        }
        if (store != null && !product.getStoreId().equals(store.getId()))
            throw new NotFoundException("Product not belong to store!");
        product.setIsDeleted(status);
        product.setUpdateAt(new Date(new java.util.Date().getTime()));
        product = productRepository.save(product);
        return CompletableFuture.completedFuture(toDto.map(product, ProductDto.class));
    }

    public CompletableFuture<ProductDto> updateActiveProduct(UUID userId, UUID productId, boolean status) {
        Store store = storeRepository.findByUserId(userId).orElse(null);
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Unable to find product!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Unable to find user!"));
        if (store == null && !user.getRoleByRoleId().getName().equals(Constant.ADMIN)) {
            throw new NotFoundException("Unable to find store!");
        }
        if (store != null && !product.getStoreId().equals(store.getId()))
            throw new NotFoundException("Product not belong to store!");
        product.setActive(status);
        product.setUpdateAt(new Date(new java.util.Date().getTime()));
        product = productRepository.save(product);
        return CompletableFuture.completedFuture(toDto.map(product, ProductDto.class));
    }


    @Async
    @Override
    public CompletableFuture<List<String>> findCategoryNamesAndProductNamesByKeyword(String keyword) {
        List<Category> categories = categoryRepository.findByNameStartingWithIgnoreCase(keyword);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        List<String> results = new ArrayList<>(products.stream().map(Product::getName).toList());
        results.addAll(categories.stream().map(Category::getName).toList());
        return CompletableFuture.completedFuture(results);
    }
}

