
package src.service.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Category;
import src.repository.ICategoryRepository;
import src.service.Category.Dtos.CategoryCreateDto;
import src.service.Category.Dtos.CategoryDto;
import src.service.Category.Dtos.CategoryUpdateDto;
import src.service.Category.Dtos.ParentCategory;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static src.config.utils.MapperUtils.toDto;

@Service("CategoryService")
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ModelMapper toDto;
    @PersistenceContext
    EntityManager em;

    public CategoryService(ICategoryRepository categoryRepository, ModelMapper toDto) {
        this.categoryRepository = categoryRepository;
        this.toDto = toDto;
    }

    @Async
    @Transactional
    public CompletableFuture<List<CategoryDto>> getAll() {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(
                categoryRepository.findAll().stream().map(
                        x -> {
                            CategoryDto a = toDto.map(x, CategoryDto.class);
                            if (x.getParentCategory() != null)
                                a.setParentCate(toDto.map(x.getParentCategory(), ParentCategory.class));
                            return a;
                        }
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CategoryDto> getOne(UUID id) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.findById(id).orElse(null), CategoryDto.class));
    }

    @Async
    public CompletableFuture<CategoryDto> create(CategoryCreateDto input) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Category category = categoryRepository.save(toDto.map(input, Category.class));
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.save(category), CategoryDto.class));
    }

    @Async
    public CompletableFuture<CategoryDto> update(UUID id, CategoryUpdateDto categories) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new NotFoundException("Unable to find category!");
//        BeanUtils.copyProperties(categories, existingCategory);
        toDto(categories, existingCategory);
        existingCategory.setUpdateAt(new Date(new java.util.Date().getTime()));
        existingCategory =  categoryRepository.save(existingCategory);
        CategoryDto a = toDto.map(existingCategory, CategoryDto.class);
        if (existingCategory.getAttributesByCategoryId() != null){
            a.setParentCate(toDto.map(categoryRepository.findById(existingCategory.getParentCategoryId()).orElse(null), ParentCategory.class));
        }
        return CompletableFuture.completedFuture(a);
    }

    @Async
    public CompletableFuture<PagedResultDto<CategoryDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        long total = categoryRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Category> features = new ApiQuery<>(request, em, Category.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> {
                    CategoryDto a = toDto.map(x, CategoryDto.class);
                    if (x.getParentCategory() != null)
                        a.setParentCate(toDto.map(x.getParentCategory(), ParentCategory.class));
                    return a;
                }).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find category!");
        existingCategory.setIsDeleted(true);
        categoryRepository.save(toDto.map(existingCategory, Category.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<CategoryDto>> getCategoryFeatures() {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Category> categories = categoryRepository.findFeatureCategories();

        return CompletableFuture.completedFuture(categories.stream().map(
                x -> toDto.map(x, CategoryDto.class)
        ).collect(Collectors.toList()));
    }
}

