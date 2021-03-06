# Отчёт по итогам автоматизации
Для автоматизации тестирования были запланированы сценарии успешной покупки тура по карте и в кредит, отклоненной покупки по карте с недостаточным балансом, ввода невалидных данных пользователем. Эти сценарии были автоматизированы, тесты проверяют работу сайта и баз данных. Также была запланирована автоматизация сценариев поведения системы при падении базы данных и при отсутствии связи с сервером. Данные кейсы не могут быть автоматизированны.
### Краткое описание
- В ходе автоматизации тестирования были реализованы позитивные и негативные сценарии заполнения тестовой страницы.
- Реализована поддержка двух БД - MySQL и PostgreSQL.
  Для написания и развертывания тестовых сценариев были использованы:
1. Intellij IDEA
1. Java 11
2. Docker для развертывания тестовой среды и упаковки готового тествого продукта
2. Junit 5
2. Selenide
2. Lombok для упрощения работы с классами и Faker для генерации данных
2. Allure для подготовки отчетности
2. MySql и PostgreSQL как заявленные поддерживаемые БД
2. Приложение было протестировано по всем запланированным позитивным и негативным сценариям.