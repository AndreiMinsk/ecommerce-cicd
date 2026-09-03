E-commerce корзина (микросервисы)
Описание
Два микросервиса: product-service и order-service, общающиеся через REST, с Discovery и событиями через Kafka.

Что нового
Spring Cloud (Eureka, Gateway), Feign, Kafka, Docker Compose.

Чек-лист

□ product-service – каталог товаров, order-service – управление заказами.
□ order-service обращается к product-service (Feign) для проверки наличия.
□ Общая регистрация в Eureka.
□ При создании заказа – отправка события в Kafka для уведомлений.
□ Docker Compose поднимает всё (сервисы, БД, Kafka, Zookeeper).


что нужно сделать


## 🧩 Задача 19: E-commerce корзина (микросервисы)

### 📌 Что нужно сделать:
Создать **два микросервиса**, которые общаются друг с другом, регистрируются в Eureka, обмениваются событиями через Kafka, и всё это поднимается через Docker Compose.

---

### 🔧 Детали:

#### 1. **product-service**
- Каталог товаров.
- Хранит: `id`, `name`, `price`, `stock`.
- Эндпоинты:
    - `GET /products` — список всех товаров.
    - `GET /products/{id}` — получить товар по id.
    - `POST /products` — добавить товар (опционально).

#### 2. **order-service**
- Управление заказами.
- Хранит: `id`, `productId`, `quantity`, `status`.
- Эндпоинты:
    - `POST /orders` — создать заказ.
        - Проверяет наличие товара через **Feign** → обращается к `product-service`.
        - Если товара нет → ошибка.
        - Если есть → создаёт заказ и **отправляет событие в Kafka**.
    - `GET /orders` — список заказов.

#### 3. **Общая регистрация в Eureka**
- Оба сервиса регистрируются в **Eureka Server**.
- Eureka поднимается отдельным сервисом (или в Docker Compose).

#### 4. **Kafka**
- При создании заказа отправляется событие:
  ```json
  {
    "orderId": 1,
    "productId": 123,
    "quantity": 2,
    "status": "CREATED"
  }
  ```
- Можно добавить **consumer** (например, notification-service), который слушает это событие и логирует/отправляет уведомление.

#### 5. **Docker Compose**
Поднимает всё:
- Eureka Server
- product-service
- order-service
- Kafka + Zookeeper
- PostgreSQL (для каждого сервиса своя БД или общая — на выбор)

---

### 🧱 Структура проекта (монорепозиторий или отдельные папки):

```
ecommerce/
├── eureka-server/
├── product-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── order-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml
└── README.md
```

---

### ✅ Чек-лист (что проверить после реализации):

- [ ] `product-service` работает и отдаёт товары.
- [ ] `order-service` создаёт заказ и проверяет наличие через Feign.
- [ ] Оба сервиса зарегистрированы в Eureka.
- [ ] При создании заказа в Kafka уходит событие.
- [ ] Всё поднимается через `docker-compose up`.

---

### ⏱️ Время:
**~6–10 часов**, если делаешь с нуля.  
Сложность: высокая. Но ты уже готов к этому! 💪