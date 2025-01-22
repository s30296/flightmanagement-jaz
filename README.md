# JAZ-PROJEKT
**JAZ Projekt - flightmanagement**

*Katalog projektu po pobraniu z GitHub może mieć nieco inną nazwę np. flightmanagement-main, w takim przypadku trzeba zmienić nazwę na `flightmanagement`*

Założenie projektu polega na możliwości tworzenia lotów i automatycznego dostosowania jego zależności na podstawie aktualnej pogody, jak również czasu lotu. Pozwala również na więcej czynności w tym na operacje `CRUD` pasażerów lotów.

Projekt podzieliłem na 3 moduły `model`, `service`, `web`.
Jest rozdzielony na warstwy oraz 5 modeli, znajdują się w nim relacje bazodanowe, które wykorzystują `Derived Query Methods`. W projekcie zastosowany jest `MariaDB`.
Używam `MapStruct` do mapowania Request na Response.
Podłączyłem się do zewnętrznego serwisu `OpenWeatherMap API`, by móc pobierać aktualne dane pogodowe dla danego miasta.
Endpointy projektu, pozwalają wyświetlać, tworzyć oraz usuwać dane z bazy danych za pomocą `GET`, `POST`, `PUT`, `DELETE`. Używam również `AOP` do przechwytywania błędów.
Projekt wykorzystuje bibliotekę `REACT` do stworzenia frontendu, który wyświetla część endpointów zapewnionych przez backend.
Wykorzystany jest również `Log4j2` do wyświetlania oraz zapisu logów działania aplikacji do pliku.
A także dodałem kilka endpointów z możliwością wyświetlenia wyniku z *cache* dzięki bibliotece `Caffeine Cache`.

- **Ustawienie bazy danych**
    1. **Login**: `flightmanagement`
    2. **Hasło**: `password`

```bash
docker pull mariadb
docker run -d --name flightmanagement-container -e MARIADB_ROOT_PASSWORD=password -e MARIADB_DATABASE=flightmanagement -e MARIADB_USER=flightmanagement -e MARIADB_PASSWORD=password -p 3306:3306 mariadb
```

- **Uruchomienie frontendu**:
```
cd frontend
npm start
```

- **Ustawienie Headers dla Postmana**:
```
Key = Content-Type
Value = application/json
```

- **Przykładowe Body dla Request**:
    1. Tworzenie miasta (POST) - `http://localhost:8080/cities/create`
        ```json
        {
            "name": "Warsaw",
            "country": "Poland",
            "latitude": 52.2297,
            "longitude": 21.0122
        }
        ```
    2. Tworzenie lotniska (POST) - `http://localhost:8080/airports/create`
        ```json
        {
            "name": "WAW",
            "cityId": 1
        }
        ```
    3. Tworzenie lotu (POST) - `http://localhost:8080/flights/create`
        ```json
        {
            "flightNumber": "ABCXYZ1234",
            "flightLength": 2,
            "departureTime": "2025-01-05T10:30:00",
            "departureAirportId": 1,
            "arrivalAirportId": 2
        }
        ```
    4. Tworzenie/Edycja pasażera (POST/PUT) - `http://localhost:8080/passengers/create` lub `http://localhost:8080/passengers/update`
        ```json
        {
            "email": "jackblack@gmail.com",
            "firstName": "Jack",
            "lastName": "Black",
            "flights": [1, 2]
        }
        ```
    5. Wydobycie aktualnych danych pogodowych dla miasta z API (POST) - `http://localhost:8080/weather/fetch`
        ```json
        {
            "cityName": "Warsaw"
        }
        ```
