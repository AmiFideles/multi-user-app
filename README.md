# Multi-User-App
Java(using Multithreading), PostgreSQL, Hibernate

## Требования
* [Java 8 и выше](https://www.oracle.com/java/technologies/downloads/)
* [Maven](https://maven.apache.org/)
* [PostgreSQL](https://www.postgresql.org/)
---
## Использование
&emsp; ___Для миграции используется Flyway. Необходимо задать параметры для подключения к бд___
```properties
flyway.url = url
flyway.username = username
flyway.password = password
```

&emsp; ___Внутри конфигурационного файла hibernate.cfg.xml задать параметры для подключения в бд___
```xml
<property name="connection.url">url</property>
<property name="connection.username">username</property>
<property name="connection.password">password</property>
```
&emsp; ___Задать порт, на котором будет запускаться сервер___
```properties
server.port = port
```
&emsp; ___Задать необходимые параметры внутри клиентской части приложения___
```properties
remote.server.port=port
remote.server.address=host
client.reconnectionTime=time
```
### &emsp; Запуск сервера:
```bash
    cd server/target
    java -jar server-1.0.jar
```

### &emsp; Запуск клиента:
```bash
    cd client/target
    java -jar client-1.0.jar clientPort
```

## Описание задания
* ___Разработанная программа должна удовлетворять следующим  требованиям:___
  * Организовать хранение коллекции в реляционной СУБД (__PostgresQL__). Убрать хранение коллекции в файле.
  * Для генерации поля id использовать средства базы данных (sequence).
  * Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД
  * Все команды получения данных должны работать с коллекцией в памяти, а не в БД
  * Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.
  * Пароли при хранении хэшировать алгоритмом __SHA-512__
  * Запретить выполнение команд не авторизованным пользователям.
  * При хранении объектов сохранять информацию о пользователе, который создал этот объект.
  * Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.
  * Для идентификации пользователя отправлять логин и пароль с каждым запросом.
* ___Необходимо реализовать многопоточную обработку запросов.___
  * Для многопоточного чтения запросов использовать __Fixed thread pool__
  * Для многопотчной обработки полученного запроса использовать __ForkJoinPool__
  * Для многопоточной отправки ответа использовать __Cached thread pool__
  * Для синхронизации доступа к коллекции использовать синхронизацию чтения и записи с помощью __synchronized__

## Вопросы для подготовки
1. Многопоточность. Класс Thread, интерфейс Runnable. Модификатор synchronized.
2. Методы wait(), notify() класса Object, интерфейсы Lock и Condition.
3. Классы-сихронизаторы из пакета java.util.concurrent.
4. Модификатор volatile. Атомарные типы данных и операции.
5. Коллекции из пакета java.util.concurrent.
6. Интерфейсы Executor, ExecutorService, Callable, Future
7. Пулы потоков
8. JDBC. Порядок взаимодействия с базой данных. Класс DriverManager. Интерфейс Connection
9. Интерфейсы Statement, PreparedStatement, ResultSet, RowSet
10. Шаблоны проектирования.