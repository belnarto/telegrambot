Задача:

Необходимо создать web приложение по управлению собственным туристическим телеграм ботом.
    
    1) Телеграм бот выдает пользователю справочную информацию о введенном городе.
    Например, пользователь вводит: «Москва», чат-бот отвечает:
    «Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))».
    2) Данные о городах должны храниться в базе данных.
    3) Управлять данными о городах (добавлять новые города и информацию о них,
    изменять и удалять любую информацию) необходимо через REST WebService.
    4) Используемые технологии: SpringBoot, SpringMVC, SpringData, Hibernate, Java не ниже 1.8.
    Для сборки проекта использовать Maven.
    5) Исходный код приложения должен быть залит на GitHub, в файле readme указать,
    что необходимо для запуска (в том числе имя телеграм бота и его токен).

Для запуска необходимо:

    1) Скачать и запустить telegrambot-1.4.jar
    2) Найти в телеграмме бота с именем cityHelperBelnartoBot.
    Воспользоваться им.
    Токен бота: 978393601:AAGAdOSAbnsQYyVHA7pi-dZ9UZGHTvj46Sc
    3) Опционально: для проверки REST использовать telegrambot.postman_collection.json
    открыть его в POSTMAN и прогнать тесты.

REST(without jwt token) spec:

| URL                       | Method    | Resources             |
| ---------                 | -----     | --------              |
| /api/v1/cities            | GET       | get all cities        |
| /api/v1/cities/           | POST      | create city           |
| /api/v1/cities/{cityId}   | GET       | get specific city     |
| /api/v1/cities/{cityId}   | PUT       | update specific city  |
| /api/v1/cities/{cityId}   | DELETE    | delete specific city  |