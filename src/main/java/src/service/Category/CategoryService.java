

package src.service.Category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.exception.NotFoundException;
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

    @Async
    public CompletableFuture<List<CategoryDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<CategoryDto>) categoryRepository.findAll().stream().map(
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
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.save(existingCategory), CategoryDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingCategory.setIsDeleted(true);
        categoryRepository.save(toDto.map(existingCategory, Category.class));
        return CompletableFuture.completedFuture(null);
    }

    // tìm kiếm Category theo name
    @Async
    public CompletableFuture<List<CategoryDto>> findByName(String name) {
        Collection<Object> categorys = categoryRepository.findByNameContainingIgnoreCase(name);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Object category : categorys) {
            CategoryDto categoryDto = toDto.map(category, CategoryDto.class);
            if (categoryDto.getName().equalsIgnoreCase(name)) {
                categoryDtos.add(categoryDto);
            }
        }
        if (categoryDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find any categorys with name: " + name);
        }
        return CompletableFuture.completedFuture(categoryDtos);
    }


    // sắp xếp category theo name
    @Async
    public CompletableFuture<List<CategoryDto>> getAllSortedByName() {
        List<CategoryDto> categoryDtos = categoryRepository.findAll().stream()
                .map(category -> toDto.map(category, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(categoryDtos);
    }
}

