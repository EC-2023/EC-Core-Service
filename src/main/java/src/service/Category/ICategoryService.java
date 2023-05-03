
package src.service.Category;

import src.service.Category.Dtos.CategoryCreateDto;
import src.service.Category.Dtos.CategoryDto;
import src.service.Category.Dtos.CategoryUpdateDto;
import src.service.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategoryService extends IService<CategoryDto, CategoryCreateDto, CategoryUpdateDto> {
    //    public CompletableFuture<List<CategoryDto>> get(UUID parentCategoryId);
    public CompletableFuture<List<CategoryDto>> getCategoryFeatures();
}
