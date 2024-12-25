package NewsList.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Сущность категории")
public class CategoryDto {
    @Schema(description = "Уникальный идентификатор категории", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Наимнование категории", example = "Новости экономики")
    private String title;
    @Schema(description = "Список новостей, принадлежащих этой категории", accessMode = Schema.AccessMode.READ_ONLY)
    private List<NewsDto> newsList = new ArrayList<>();
}
