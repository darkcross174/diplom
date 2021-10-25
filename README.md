# Инструкция по запуску тестов #

1. Скопируйте проект
2. Запустите базы данных, выполнив команду `docker-compose up`. При этом у вас уже должен быть установлен Docker
3. В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

   + для базы данных MySQL:  
        
   + java -jar aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app

   + для базы данных PostgreSQL:  
      java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres
       

4. Приложение должно запуститься по адресу [http://localhost:8080/](http://localhost:8080/)

5. Запустите тесты командой:

- при работе с postgres: gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres -Dlogin=user -Dpassword=pass
  -Dapp.url=http://localhost:8080
- при работе с mySql: gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app -Dlogin=user -Dpassword=pass
  -Dapp.url=http://localhost:8080 .
  
6. Для создания отчета Allure запустить команду 'gradlew allureReport' и 'gradlew allureServe'
   для Windows.
