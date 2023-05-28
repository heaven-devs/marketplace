##### <img src="http://alrepin.sytes.net/school/student/2/avatar/preview" width="50" height="50"/> Skypro от Skyeng
***


###  Платформа по перепродаже вещей
Демо: [marketplace.heaven.ga](http://marketplace.heaven.ga)  
Визитка команды: [heaven.ga](http://heaven.ga)  


#### Основная информация:
Бэк web-сервиса по перепродаже вещей на Java Spring Boot к предоставленному [FrontEnd](https://github.com/heaven-devs/front-react-avito) по имеющейся [спецификации](https://github.com/heaven-devs/front-react-avito/blob/main/openapi.yaml).  
##### Реализован следующий функционал:
_Срок разработки: 6 недель_
- Авторизация и аутентификация пользователей;
- Распределение ролей между пользователями: пользователь и администратор;
- CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только свои;
- Под каждым объявлением пользователи могут оставлять отзывы;
- В заголовке сайта можно осуществлять поиск объявлений по названию;
- Показывать и сохранять картинки объявлений.  

##### Архитектурные особенности:
<details>
  <summary>Схема базы данных</summary>

<img src="http://marketplace.backend.heaven.ga/img/59" />
</details>  

<details>
  <summary>Используемые зависимости</summary>

<img src="http://marketplace.backend.heaven.ga/img/60" />
</details>

##### Инструкции по развертыванию:
<details>
  <summary>Сборка и сохранение в архив Docker image Бэкенд-части</summary>

```
docker build -t backend-marketplace-heaven .
docker save "backend-marketplace-heaven:latest" | gzip -c  > "./marketplace-heaven-backend-image-`date +%Y-%m-%d_%H-%M`.tgz";
```
</details>

<details>
  <summary>Импорт Docker image Бэкенд-части</summary>

```
gunzip -c "./marketplace-heaven-backend-image-`date +%Y-%m-%d_%H-%M`.tgz" | docker load
```
</details>

##### Запуск в docker compose:
`COMPOSE_HTTP_TIMEOUT=600 docker compose up -d`
<details>
  <summary>листинг docker-compose.yml</summary>

```
version: "3.3"
services:
backend-marketplace-heaven:
image: backend-marketplace-heaven:latest
deploy:
resources:
limits:
cpus: '0.50'
memory: 200M
reservations:
cpus: '0.45'
memory: 180M
      container_name: backend-marketplace-heaven
      ports:
        - "8080:8080"
        - "9090:9090"
      environment:
        - "PUID=1000"
        - "PGID=1000"
      restart: 'no'
      volumes:
        - ./appdata/db:/src/main/resources/db:Z
        - ./appdata/root:/root:Z
        - /etc/timezone:/etc/timezone:ro
```
</details>

При первичном запуске инициализируется H2 БД (файлы расположены в ./appdata/db) и создается  
<details>
  <summary>учетная запись администратора</summary>

```
логин: admin@heaven.ga  
пароль: admin
```
</details>

***
