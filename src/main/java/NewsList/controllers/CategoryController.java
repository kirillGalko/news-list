package NewsList.controllers;

import NewsList.dto.CategoryDto;
import NewsList.services.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/categories")
@Tag(name = "Category controller", description = "Создание, вывод, изменение и удаление категорий новостей")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get category by id", description = "Вывод данных о категории по идентификатору")
    public ResponseEntity getCategoryById(
            @PathVariable @Parameter(description = "Уникальный идентификатор категории", example = "1") Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Вывод данных обо всех категориях")
    public ResponseEntity getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Создание новой категории")
    public ResponseEntity createCategory(
            @RequestBody @Parameter(description = "Экземпляр сущности категории") CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PutMapping(path = "/update/{id}")
    @Operation(summary = "Update category", description = "Обновление существующей категории")
    public ResponseEntity updateCategory(
            @PathVariable @Parameter(description = "Уникальный идентификатор категории", example = "1") Long id,
            @RequestBody @Parameter(description = "Экземпляр сущности категории") CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete category", description = "Удаление категории по уникальному идентификатору")
    public ResponseEntity deleteCategory(
            @PathVariable @Parameter(description = "Уникальный идентификатор категории", example = "1") Long id) {
        return categoryService.deleteById(id);
    }

}
