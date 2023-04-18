

package src.service.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<CategoryDto>> getAll() {
        return CompletableFuture.completedFuture(
                categoryRepository.findAll().stream().map(
                        x -> toDto.map(x, CategoryDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CategoryDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.findById(id), CategoryDto.class));
    }

    @Async
    public CompletableFuture<CategoryDto> create(CategoryCreateDto input) {
        Category category = categoryRepository.save(toDto.map(input, Category.class));
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.save(category), CategoryDto.class));
    }

    @Async
    public CompletableFuture<CategoryDto> update(UUID id, CategoryUpdateDto categorys) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new NotFoundException("Unable to find category!");
        BeanUtils.copyProperties(categorys, existingCategory);
        existingCategory.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.save(existingCategory), CategoryDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<CategoryDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = categoryRepository.count();
                Pagination pagination = Pagination.create(total, skip, limit);

        ApiQuery<Category> features = new ApiQuery<>(request, em, Category.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CategoryDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find category!");
        existingCategory.setIsDeleted(true);
        existingCategory.setUpdateAt(new Date(new java.util.Date().getTime()));
        categoryRepository.save(toDto.map(existingCategory, Category.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<CategoryDto>> getCategoryFeatures(UUID parentCategoryId) {
        if (parentCategoryId == null) {
            throw new IllegalArgumentException("Parent category ID cannot be null");
        }

        List<Category> categories = categoryRepository.findAllByParentCategoryId(parentCategoryId);
        if (categories == null) {
            throw new NotFoundException("Unable to find categories with parent category ID " + parentCategoryId);
        }
        return CompletableFuture.completedFuture(categories.stream().map(
                x -> toDto.map(x, CategoryDto.class)
        ).collect(Collectors.toList()));
    }


}

