package NewsList.services;

import NewsList.dto.CategoryDto;
import NewsList.entities.Category;
import NewsList.errors.Error;
import NewsList.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ResponseEntity getById (Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            logger.error("Категория с id {} не найдена", id);
            Error error = new Error();
            error.setResult(false);
            error.setText("Категория с id " + id + " не найдена");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        Category category = categoryRepository.findById(id).orElseThrow();
        return new ResponseEntity (mapToDto(category), HttpStatus.OK);
    }

    public ResponseEntity getAll() {
        return new ResponseEntity(categoryRepository.findAll()
                .stream()
                .map(c -> mapToDto(c))
                .toList(), HttpStatus.OK);
    }

    public ResponseEntity create(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        logger.info("Создана новая запись с id {} ", category.getId());
        return new ResponseEntity(mapToDto(category), HttpStatus.CREATED);
    }

    public ResponseEntity update(Long id, CategoryDto categoryDto) {
        if(categoryRepository.findById(id).isEmpty()) {
            logger.error("Категория с id {} не найдена", id);
            Error error = new Error();
            error.setResult(false);
            error.setText("Категория с id " + id + " не найдена");
        }

        Category category = categoryRepository.findById(id).orElseThrow();
        category.setTitle(categoryDto.getTitle());
        categoryRepository.save(category);
        logger.info("Обновлена запись с id {} ", id);
        return new ResponseEntity(mapToDto(category), HttpStatus.OK);
    }

    public ResponseEntity deleteById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            logger.error("Категория с id {} не найдена", id);
            Error error = new Error();
            error.setResult(false);
            error.setText("Категория с id " + id + " не найдена");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        Error error = new Error();
        error.setResult(true);
        error.setText("Категория с id " + id + " успешно удалена");
        categoryRepository.deleteById(id);
        logger.info("Удалена запись с id {} ", id);
        return new ResponseEntity(error, HttpStatus.OK);
    }

    public static Category mapToEntity (CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNewsList(categoryDto.getNewsList()
                .stream()
                .map(NewsServiceImpl::mapToEntity)
                .toList());
        return category;
    }

    public static CategoryDto mapToDto (Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNewsList(category.getNewsList()
                .stream()
                .map(NewsServiceImpl::mapToDto)
                .toList());
        return categoryDto;
    }
}
