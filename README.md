# FarmAPI

Сервер реализован как монолит и готов к развертыванию в Docker после предварительной настройки переменных среды (URL, логин и пароль БД, почта отправителя и получателя отчётов).

## Использованные технологии

- Java 17
- Spring
- Spring Boot 3
- Spring Security
- Spring Data
- Hibernate
- Jakarta mail
- - Технология хоть и не числится в списке рекомендованных, но необходима для выполнения дополнительного условия заказчика по рассылке ежедневного вечернего отчёта.
- PostgreSQL
- JWT
- Docker
- JSON
- Нормализация БД (6 НФ)

## Возможности

Помимо основных требований заказчика, были удовлетворены также и дополнительные (квоты, оценки, отчёты).

- Управление продуктами
- Управление единицами измерения количества продукта
- Управление типами продуктов
- Получение разнообразной статистики по производству (в целом, по фермеру, по дням, по неделям, по месяцам, по произвольному периоду, а также прочие для удобства)
- Управление безопасностью (через JWT, реализованы роли фермера и администратора)
- Управление сотрудниками
- Получение ежедневных вечерних отчётов
- Управление оценками сотрудников
- Управление нормами (квотами) сбора урожая

Для использования этих возможностей необходимо в начале получить JWT через `/auth/sign-up` или `/auth-sign-in`, а затем сопровождать каждый запрос полученным токеном. Время жизни токена - 60 минут. В противном случае сервер будет возвращать 403.

## База данных

![dbscheme](https://github.com/Lizurt/FarmAPI/blob/master/docs/resources/FarmAPIDB.png?raw=true)

Примечание: пароли не хранятся на сервере напрямую. Вместо этого хранятся их зашифрованные версии.

## Эндпоинты

Каждая сущность поддерживает все базовые CRUD операции (Create и Update объединены в PUT запросы). Помимо базовых CRUD операций есть возможность:

- Получить все актуальные квоты для фермера (`/harvest-quota/get/of-farmer/{id}/relevant`)
- Получить продукты (`/product/get/**`) по типу, по периоду, по периоду с типом, по периоду от фермера
- Получить статистику по продуктам (`/product/get-statistics/**`) общую, по типу, по фермеру, по месяцу, по неделе, по дню, по произвольному периоду, по фермеру за период
- Получить оценки по фермеру (`/get/of-farmer/{id}`)

Для всех запросов необходимо прикладывать JWT, за исключением `/auth/sign-in`. Регистрацией новых сотрудников занимается администратор.

Доступ к эндпоинтам предоставляется следующим образом:

- Попытаться войти в аккаунт может любой пользователь
- Добавить продукт может как фермер, так и администратор
- Узнать оценки может как фермер, так и администратор
- Узнать квоту может как фермер, так и администратор
- Получить статистику (сколько чего собрал) <ins>конкретного</ins> фермера может как этот самый фермер, так и администратор
- - Доступ фермеру необходим для реализации квот (норм) по сбору урожая - фермер по условию должен видеть сколько ему нужно собрать и сколько осталось. Из этой информации фермер и так может узнать сколько он собрал
- Для остальных не описанных запросов (включая всю прочую статистику) необходим доступ администратора

По умолчанию, при регистрации, в качестве имени пользователя используется указанный email, но есть возможность задать произвольное имя пользователя.

## Примеры REST-запросов

Примеры с учётом, что сервер с портом 8080 открыт там же, где и производятся запросы.

### Вход в аккаунт

#### Запрос

```shell
curl --location 'http://localhost:8080/auth/sign-in' \
--header 'Content-Type: application/json' \
--data '{
    "username": "artyom",
    "password": "strongpass"
}'
```

#### Ответ

```shell
HTTP/1.1 200 OK
... 
{"token":"eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1haWwiOiJhcnRlbWthLXNhcGVsa2luQG1haWwucnUiLCJzdWIiOiJhcnR5b20iLCJpYXQiOjE3MTA0MjU5ODMsImV4cCI6MTcxMDQyOTU4M30.aNGDP7igBtonPCBYDos26L1zHorZBxw7zngsggH0UN1-Ft9MBuYiSvxkgQWAzgqaXDzXclbMGTD1ocm3RH4R3g"}
```

### Получение списка типов продуктов

#### Запрос

```shell
curl --location 'http://localhost:8080/product-type/get' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1haWwiOiJhcnRlbWthLXNhcGVsa2luQG1haWwucnUiLCJzdWIiOiJhcnR5b20iLCJpYXQiOjE3MTA0MjU5ODMsImV4cCI6MTcxMDQyOTU4M30.aNGDP7igBtonPCBYDos26L1zHorZBxw7zngsggH0UN1-Ft9MBuYiSvxkgQWAzgqaXDzXclbMGTD1ocm3RH4R3g'
```

#### Ответ

```shell
HTTP/1.1 200 OK
... 
[{"id":1,"name":"Рис","units":1},{"id":2,"name":"Молоко","units":3}]
```

### Выставление оценки фермеру

#### Запрос

```shell
curl --location --request PUT 'http://localhost:8080/rating/save' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1haWwiOiJhcnRlbWthLXNhcGVsa2luQG1haWwucnUiLCJzdWIiOiJhcnR5b20iLCJpYXQiOjE3MTA0MjU5ODMsImV4cCI6MTcxMDQyOTU4M30.aNGDP7igBtonPCBYDos26L1zHorZBxw7zngsggH0UN1-Ft9MBuYiSvxkgQWAzgqaXDzXclbMGTD1ocm3RH4R3g' \
--data '{
    "rating": 5,
    "farmerId": 5
}'
```

#### Ответ

```shell
HTTP/1.1 200 OK
...
{"id":3,"rating":5.0,"date":"2024-03-14T14:23:11.118+00:00","farmer":5}
```

### Удаление оценки

#### Запрос

```shell
curl --location --request DELETE 'http://localhost:8080/rating/delete/3' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1haWwiOiJhcnRlbWthLXNhcGVsa2luQG1haWwucnUiLCJzdWIiOiJhcnR5b20iLCJpYXQiOjE3MTA0MjU5ODMsImV4cCI6MTcxMDQyOTU4M30.aNGDP7igBtonPCBYDos26L1zHorZBxw7zngsggH0UN1-Ft9MBuYiSvxkgQWAzgqaXDzXclbMGTD1ocm3RH4R3g'
```

#### Ответ

```shell
 
HTTP/1.1 200 OK
...
```

### Получение статистики производства за март 2024

#### Запрос

```shell
curl --location 'http://localhost:8080/product/get-statistics/of-month/2024-03' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwiZW1haWwiOiJhcnRlbWthLXNhcGVsa2luQG1haWwucnUiLCJzdWIiOiJhcnR5b20iLCJpYXQiOjE3MTA0MjU5ODMsImV4cCI6MTcxMDQyOTU4M30.aNGDP7igBtonPCBYDos26L1zHorZBxw7zngsggH0UN1-Ft9MBuYiSvxkgQWAzgqaXDzXclbMGTD1ocm3RH4R3g'
```

#### Ответ

```shell
HTTP/1.1 200 OK
...
[{"productTypeId":1,"producedAmount":20.0},{"productTypeId":2,"producedAmount":31.0}]

```
