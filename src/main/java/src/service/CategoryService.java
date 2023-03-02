
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Category;
import src.repository.ICategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private  ICategoryRepository categoryRepository;
    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Optional<Category> getOne(UUID id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(UUID id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {

            categoryRepository.save(existingCategory);
        }
        return existingCategory;
    }

    public void remove(UUID id) {
        categoryRepository.deleteById(id);
    }
}
