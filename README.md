# News List

Простое веб-приложение для управления новостной лентой. Позволяет создавать и редактировать новости и категории новостей

## Стэк
* Java 18
* SpringBoot
* PostgreSQL 16
* SL4J
* Lombok

## Инструкция
### Заполнение application.yaml
* Заполнить поля для подключения к БД в application.yaml

![img.png](img/img_1.png)

### Сборка
```shell
mvn install
```

### Использование приложения
При локальном запуске приложение стартует на http://localhost:8080

### Документация API
После запуска приложения документация по API доступна по адресу http://localhost:8080/swagger-ui/index.html

