package NewsList.services;

import NewsList.dto.NewsDto;
import NewsList.entities.Category;
import NewsList.entities.News;
import NewsList.errors.Error;
import NewsList.repositories.CategoryRepository;
import NewsList.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    public ResponseEntity getById (Long id) {
        if (newsRepository.findById(id).isEmpty()) {
            logger.error("Новость с id {} не найдена", id);
            Error error = new Error();
            error.setResult(false);
            error.setText("Новости с id " + id + " не существует");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        News news = newsRepository.findById(id).orElseThrow();
        return new ResponseEntity(mapToDto(news), HttpStatus.OK);
    }

    public ResponseEntity getByCategory(Long categoryId){

        if (categoryRepository.findById(categoryId).isEmpty()){
            logger.error("Категория с id {} не найдена", categoryId);
            Error error = new Error();
            error.setResult(false);
            error.setText("Категория c ID" + categoryId + " не найдена");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return new ResponseEntity(CategoryServiceImpl.mapToDto(category), HttpStatus.OK);
    }

    public ResponseEntity getAll(){
        return new ResponseEntity(newsRepository.findAll()
                .stream()
                .map(NewsServiceImpl::mapToDto)
                .toList(), HttpStatus.OK);
    }

    public ResponseEntity create (NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        if(newsDto.getCategoryTitle() != null) {
            String categoryTitle = newsDto.getCategoryTitle();
            if(categoryRepository.findByTitle(categoryTitle) == null){
                logger.error("Категория с наименованием {} не найдена", categoryTitle);
                Error error = new Error();
                error.setResult(false);
                error.setText("Категория " + categoryTitle + " не найдена");
                return new ResponseEntity(error, HttpStatus.NOT_FOUND);
            }
            Category category = categoryRepository.findByTitle(categoryTitle);
            news.setCategory(category);
        }
        newsRepository.save(news);
        logger.info("Создана новость с id {} ", news.getId());
        return new ResponseEntity(mapToDto(news), HttpStatus.CREATED);
    }

    public ResponseEntity update (Long id, NewsDto newsDto) {
        if(newsRepository.findById(id).isEmpty()){
            logger.error("Новость с id {} не найдена", id);
            Error error = new Error();
            error.setResult(false);
            error.setText("Новость с id " + id + " не найдена");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        News news = newsRepository.findById(id).orElseThrow();
        if(newsDto.getTitle() != null) {
            news.setTitle(newsDto.getTitle());
        }
        if(newsDto.getText() != null) {
            news.setText(newsDto.getText());
        }
        if(newsDto.getCategoryTitle() != null) {
            String categoryTitle = newsDto.getCategoryTitle();
            if(categoryRepository.findByTitle(categoryTitle) == null){
                logger.error("Категория с наименованием {} не найдена", categoryTitle);
                Error error = new Error();
                error.setResult(false);
                error.setText("Категория " + categoryTitle + " не найдена");
                return new ResponseEntity(error, HttpStatus.NOT_FOUND);
            }
            Category category = categoryRepository.findByTitle(categoryTitle);
            news.setCategory(category);
        }
        newsRepository.save(news);
        logger.info("Обновлена новость с id {} ", id);
        return new ResponseEntity(mapToDto(news), HttpStatus.OK);
    }

    public ResponseEntity deleteById (Long id) {
        if (newsRepository.findById(id).isEmpty()) {
            Error error = new Error();
            error.setResult(false);
            error.setText("Новость с id " + id + " не найдена");
            return new ResponseEntity(error, HttpStatus.NOT_FOUND);
        }
        Error error = new Error();
        error.setResult(true);
        error.setText("Новость с id " + id + " успешно удалена");
        newsRepository.deleteById(id);
        logger.info("Удалена новость с id {} ", id);
        return new ResponseEntity(error, HttpStatus.OK);
    }

    public static News mapToEntity(NewsDto newsDto){
        News news = new News();
        news.setId(news.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        return news;
    }

    public static NewsDto mapToDto (News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setPublicationDate(news.getPublicationDate());
        newsDto.setCategoryId(news.getCategory().getId());
        newsDto.setCategoryTitle(news.getCategory().getTitle());
        return newsDto;
    }
}
