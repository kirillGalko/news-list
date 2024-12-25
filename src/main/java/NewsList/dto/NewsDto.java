package NewsList.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
@Schema(description = "Сущность новости")
public class NewsDto {
    @Schema(description = "Уникальный идентификатор новости", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Заголовок новости")
    private String title;
    @Schema(description = "Текст новости")
    private String text;
    @Schema(description = "Дата публикации новости", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant publicationDate;
    @Schema(description = "Уникальный идентификатор категории новости", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long categoryId;
    @Schema(description = "Наименование категории новости", example = "Новости экономики")
    private String categoryTitle;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setCategoryId (Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryTitle (String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
