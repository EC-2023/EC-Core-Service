

package src.service.Category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Category;
import src.repository.ICategoryRepository;
import src.service.Category.Dtos.CategoryCreateDto;
import src.service.Category.Dtos.CategoryDto;
import src.service.Category.Dtos.CategoryUpdateDto;

import java.util.List;
import java.util.UUID;
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
    public CompletableFuture<CategoryDto> update(UUID id, CategoryUpdateDto category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(categoryRepository.save(toDto.map(category, Category.class)), CategoryDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingCategory.setDeleted(true);
        categoryRepository.save(toDto.map(existingCategory, Category.class));
        return CompletableFuture.completedFuture(null);
    }
}

