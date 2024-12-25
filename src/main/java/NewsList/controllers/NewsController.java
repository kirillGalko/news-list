package NewsList.controllers;

import NewsList.dto.NewsDto;
import NewsList.services.NewsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/news")
@Tag(name = "News controller", description = "Создание, вывод, изменение и удаление новостей")
public class NewsController {
    private final NewsServiceImpl newsService;

    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get news by id", description = "Вывод информации о новости по уникальному идентификатору")
    public ResponseEntity getNewsById(
            @PathVariable @Parameter(description = "Уникальный идентификатор новости", example = "1") Long id) {
        return newsService.getById(id);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get news by category", description = "Вывод списка новостей по категории")
    public ResponseEntity getByCategory(
            @PathVariable @Parameter(description = "Уникальный идентификатор категории", example = "1") Long categoryId) {
        return newsService.getByCategory(categoryId);
    }

    @GetMapping
    @Operation(summary = "Get all news", description = "Вывод информации по всем новостям")
    public ResponseEntity getAllNews() {
        return newsService.getAll();
    }

    @PostMapping()
    @Operation(summary = "Create news", description = "Создание новой новости")
    public ResponseEntity createNews(
            @RequestBody @Parameter(description = "Экземпляр сущности новости") NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @PutMapping(path = "/change/{id}")
    @Operation(summary = "Change news")
    public ResponseEntity changeNews(
            @PathVariable @Parameter(description = "Уникальный идентификатор новости", example = "1") Long id,
            @RequestBody @Parameter(description = "Экземпляр сущности новости") NewsDto newsDto) {
        return newsService.update(id, newsDto);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete news", description = "Удаление новости по уникальному идентификатору")
    public ResponseEntity deleteNews(
            @PathVariable @Parameter(description = "Уникальный идентификатор новости", example = "1") Long id) {
        return newsService.deleteById(id);
    }
}
