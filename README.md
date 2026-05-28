Отчет по лаботаротоной работе №2. Конфигурирование приложений Spring.

Цель работы: 
изучение и практическое освоение механизмов автоматического внедрения зависимостей через аннотации Spring Framework.

Выполнение работы:
1) Добавил зависимость spring-aspects в build.gradle, чтобы заработала поддержка аспектов.
2) Создал файл настроек application.properties.
3) Обновил AppConfig.java, чтобы программа автоматически искала классы с @Component.
4) Подредактировал файлы: CarServiceMessageProvider, OutMessageRenderer (Изменил бины).
5) Создал новый класс HtmlMessageRenderer. Он нужен для преобразования текста в HTML таблицу.
6) Создал новый класс ProfilingAspect для замера времени работы метода getMessage().
7) Создал диаграмму Mermaid

Выводы: В ходе лабораторной работы были успешно освоены продвинутые концепции экосистемы Spring Framework.
Реализован гибкий механизм слабой связанности компонентов за счет автоматического внедрения зависимостей (DI)
вместо ручного связывания объектов.

Диаграмма Mermaid:

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }

    class AppConfig {
        <<Configuration>>
    }

    class MessageProvider {
        <<interface>>
        +getMessage() String
    }

    class MessageRenderer {
        <<interface>>
        +render() void
        +setMessageProvider(MessageProvider provider) void
        +getMessageProvider() MessageProvider
    }

    class CarServiceMessageProvider {
        <<Component>>
        -String fileName
        +init() void
        +getMessage() String
    }

    class OutMessageRenderer {
        <<Component>>
        -MessageProvider messageProvider
        +render() void
        +setMessageProvider(MessageProvider provider) void
        +getMessageProvider() MessageProvider
    }

    class HtmlMessageRenderer {
        <<Component>>
        -MessageProvider messageProvider
        +render() void
        +setMessageProvider(MessageProvider provider) void
        +getMessageProvider() MessageProvider
    }

    class ProfilingAspect {
        <<Aspect>>
        +profileCsvParsing(ProceedingJoinPoint jp) Object
    }

    %% Чистые UML-связи
    Main ..> AppConfig
    Main ..> MessageRenderer
    
    AppConfig ..> CarServiceMessageProvider
    AppConfig ..> HtmlMessageRenderer
    AppConfig ..> OutMessageRenderer
    AppConfig ..> ProfilingAspect

    CarServiceMessageProvider ..|> MessageProvider
    OutMessageRenderer ..|> MessageRenderer
    HtmlMessageRenderer ..|> MessageRenderer

    HtmlMessageRenderer --> MessageProvider
    OutMessageRenderer --> MessageProvider
    
    ProfilingAspect ..> CarServiceMessageProvider
