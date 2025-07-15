# Тестирование микросервисов ShareIt

## Структура проекта

Проект разделен на два микросервиса:

### 1. Gateway (порт 8080)
- **Назначение**: Прокси-шлюз с валидацией
- **Контроллеры**: 
  - `UserController` - управление пользователями
  - `ItemController` - управление предметами
  - `BookingController` - управление бронированиями
  - `RequestController` - управление запросами
- **Валидация**: Все входные данные валидируются здесь
- **Клиенты**: Отправляют запросы к server

### 2. Server (порт 9090)
- **Назначение**: Основная бизнес-логика
- **Контроллеры**: Те же эндпоинты, но без валидации
- **Сервисы**: Вся бизнес-логика
- **Репозитории**: Работа с базой данных

## Запуск для тестирования

### 1. Запуск Server
```bash
cd server
mvn spring-boot:run
```
Server запустится на порту 9090

### 2. Запуск Gateway
```bash
cd gateway
mvn spring-boot:run
```
Gateway запустится на порту 8080

### 3. Тестирование через Gateway

Все запросы должны идти через gateway (порт 8080):

#### Создание пользователя
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com"
  }'
```

#### Создание предмета
```bash
curl -X POST http://localhost:8080/items \
  -H "Content-Type: application/json" \
  -H "X-Sharer-User-Id: 1" \
  -d '{
    "name": "Drill",
    "description": "Power tool",
    "available": true
  }'
```

#### Создание бронирования
```bash
curl -X POST http://localhost:8080/bookings \
  -H "Content-Type: application/json" \
  -H "X-Sharer-User-Id: 1" \
  -d '{
    "itemId": 1,
    "start": "2024-01-15T10:00:00",
    "end": "2024-01-15T18:00:00"
  }'
```

## Валидация в Gateway

Gateway проверяет:
- Обязательные поля (name, email, description)
- Формат email
- Даты бронирования (start < end)
- Положительные значения для пагинации

## Обработка ошибок

- **400 Bad Request**: Некорректные данные (валидация в gateway)
- **404 Not Found**: Ресурс не найден
- **500 Internal Server Error**: Ошибка сервера

## Логирование

Gateway логирует все входящие запросы:
```
Creating user UserRequestDto(id=null, name=John Doe, email=john@example.com)
Creating item ItemRequestDto(id=null, name=Drill, description=Power tool, available=true, requestId=null), userId=1
```

## Архитектурные принципы

1. **Разделение ответственности**: Gateway - валидация, Server - бизнес-логика
2. **Единая точка входа**: Все запросы через gateway
3. **Масштабируемость**: Gateway можно масштабировать независимо
4. **Кэширование**: Gateway может кэшировать ответы (планируется) 