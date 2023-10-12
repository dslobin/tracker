##### Table of Contents

- [Requirements](#requirements)
- [How to build project](#how-to-build-project)
- [Environment variables](#environment-variables)

### Requirements

- Java 17
- Maven 3.9.5+
- MongoDB 7.0.2+

### How to build project

1. Clone a project
2. `cd java-test-task`
3. Run `mvnw clean install`.

### How to run project

1. To run the application you need to specify environment variables: TG_BOT_NAME, TG_BOT_TOKEN.
2. Run `mvnw spring-boot:run'

### Environment variables

| Name                     | Description                                                                            | Default Value                            |
|--------------------------|----------------------------------------------------------------------------------------|------------------------------------------|
| MEXC_PRICES_URI          | URL to check prices for crypto                                                         | https://api.mexc.com/api/v3/ticker/price |
| PRICE_CHANGE_PERCENTAGE  | Notify user if cryptocurrency becomes more expensive or cheaper by more than N percent | 3                                        |
| PRICE_UPDATE_INTERVAL_MS | Interval of accessing the service with cryptocurrency prices                           | 6000                                     |
| TG_BOT_NAME              | Telegram bot name                                                                      |                                          |
| TG_BOT_TOKEN             | Telegram bot token                                                                     |                                          |
| TG_USER_LIMIT            | Maximum number of bot subscribers                                                      | 1                                        |
| DB_HOST                  | Database host name                                                                     | localhost                                |
| DB_PORT                  | Database port number                                                                   | 27017                                    |
| DB_NAME                  | Database name                                                                          | crypto-tracking                          |