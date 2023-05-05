

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
        Pagination pagination = Pagination.create(0, skip, limit);
        String cateId = request.getParameter("category");
        if (cateId != null) {
            List<UUID> cates = new ArrayList<>();
            cates.add(UUID.fromString(cateId));
            List<Category> categories = categoryRepository.findAllChild(UUID.fromString(cateId));
            for (Category cate : categories) {
                if (cate != null) {
                    Category tmp = cate.getParentCategory();
                    while (tmp != null) {
                        cates.add(cate.getId());
                        tmp = tmp.getParentCategory();
                    }
                }
            }
            request.setAttribute("category", cates);
        }
        ApiQuery<Product> features = new ApiQuery<>(request, em, Product.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
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

    @Transactional
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
        // check add attribute
        List<Attribute> attributes = new ArrayList<>();
        if (product.getListAddAttribute().size() > 0) {
            for (AttributeValueUpdate data : product.getListAddAttribute()) {
                Attribute tmp = attributeRepository.save(new Attribute(existingProduct.getId(), data.getName()));
                if (data.getValues().size() > 0) {
                    for (AttributeValueUpdate value : data.getValues()) {
                        attributeValueRepository.save(new AttributeValue(tmp.getId(), null, null, null, value.getName()));
                    }
                }
            }
        }
        // check remove attribute
        if (product.getListRemoveAttribute().size() > 0) {
            attributes = new ArrayList<>();
            for (AttributeValueUpdate data : product.getListRemoveAttribute()) {
                Attribute tmp = attributeRepository.findById(UUID.fromString(data.getId())).orElseThrow(() -> new NotFoundException("Unable to find attribute!"));
                tmp.setIsDeleted(true);
                attributes.add(tmp);
            }
            attributeRepository.saveAll(attributes);
        }
        // check edit attribute
        if (product.getListEditAttribute().size() > 0) {
            attributes = new ArrayList<>();
            for (AttributeValueUpdate data : product.getListEditAttribute()) {
                Attribute tmp = attributeRepository.findById(UUID.fromString(data.getId())).orElseThrow(() -> new NotFoundException("Unable to find attribute!"));
                tmp.setName(data.getName());
                attributes.add(tmp);
            }
            attributeRepository.saveAll(attributes);
        }
        // check add attribute value
        List<AttributeValue> attributeValuess = new ArrayList<>();
        if (product.getListAddAttributeValue().size() > 0) {
            for (AttributeValueUpdate data : product.getListAddAttributeValue()) {
                if (!data.getAttributeId().contains("my-id-"))
                    attributeValuess.add(new AttributeValue(UUID.fromString(data.getAttributeId()), null, existingProduct.getId(), null, data.getName()));
            }
            attributeValueRepository.saveAll(attributeValuess);
        }
        // check remove attribute value
        if (product.getListRemoveAttributeValue().size() > 0) {
            attributeValuess = new ArrayList<>();
            for (AttributeValueUpdate data : product.getListRemoveAttributeValue()) {
                AttributeValue tmp = attributeValueRepository.findById(UUID.fromString(data.getId())).orElseThrow(() -> new NotFoundException("Unable to find attribute!"));
                tmp.setIsDeleted(true);
                attributeValuess.add(tmp);
            }
            attributeValueRepository.saveAll(attributeValuess);
        }
        // check edit attribute value
        if (product.getListEditAttributeValue().size() > 0) {
            attributes = new ArrayList<>();
            for (AttributeValueUpdate data : product.getListEditAttributeValue()) {
                AttributeValue tmp = attributeValueRepository.findById(UUID.fromString(data.getId())).orElseThrow(() -> new NotFoundException("Unable to find attribute!"));
                tmp.setName(data.getName());
                attributeValuess.add(tmp);
            }
            attributeRepository.saveAll(attributes);
        }

        return CompletableFuture.completedFuture(toDto.map(productRepository.save(existingProduct), ProductCreatePayload.class));

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
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Unable to find store!"));
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

