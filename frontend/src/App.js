import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CityForm from './CityForm';
import CityList from './CityList';
import CityDetails from './CityDetails';
import AirportForm from './AirportForm';
import AirportList from './AirportList';
import AirportDetails from './AirportDetails';
import FlightForm from './FlightForm';
import FlightList from './FlightList';
import FlightDetails from './FlightDetails';
import PassengerForm from './PassengerForm';
import PassengerList from './PassengerList';
import PassengerDetails from './PassengerDetails';
import './App.css';

function App() {
    const [cities, setCities] = useState([]);
    const [cityById, setCityById] = useState(null);
    const [airports, setAirports] = useState([]);
    const [airportById, setAirportById] = useState(null);
    const [flights, setFlights] = useState([]);
    const [flightById, setFlightById] = useState(null);
    const [passengers, setPassengers] = useState([]);
    const [passengerById, setPassengerById] = useState(null);
    const [cityMessage, setCityMessage] = useState('');
    const [airportMessage, setAirportMessage] = useState('');
    const [flightMessage, setFlightMessage] = useState('');
    const [passengerMessage, setPassengerMessage] = useState('');
    const [weather, setWeather] = useState(null);
    const [weatherMessage, setWeatherMessage] = useState('');
    const [cityInput, setCityInput] = useState('');

    useEffect(() => {
        fetchCities();
        fetchAirports();
        fetchFlights();
        fetchPassengers();
        fetchWeather();
    }, []);

    const fetchCities = async () => {
        try {
            const response = await axios.get('http://localhost:8080/cities/getAllCities');
            setCities(response.data);
        } catch (error) {
            console.error('Błąd pobierania miast:', error);
            setCityMessage('Nie udało się pobrać miast.');
        }
    };

    const fetchCityById = async (id) => {
        try {
            const response = await axios.get(`http://localhost:8080/cities/getCityById/${id}`);
            setCityById(response.data);
        } catch (error) {
            console.error('Błąd pobierania miasta:', error);
            setCityMessage('Nie udało się pobrać miasta.');
        }
    };

    const fetchAirports = async () => {
        try {
            const response = await axios.get('http://localhost:8080/airports/getAllAirports');
            setAirports(response.data);
        } catch (error) {
            console.error('Błąd pobierania lotnisk:', error);
            setAirportMessage('Nie udało się pobrać lotnisk.');
        }
    };

    const fetchAirportById = async (id) => {
        try {
            const response = await axios.get(`http://localhost:8080/airports/getAirportById/${id}`);
            setAirportById(response.data);
        } catch (error) {
            console.error('Błąd pobierania lotniska:', error);
            setAirportMessage('Nie udało się pobrać lotniska.');
        }
    };

    const fetchFlights = async () => {
        try {
            const response = await axios.get('http://localhost:8080/flights/getAllFlights');
            setFlights(response.data);
        } catch (error) {
            console.error('Błąd pobierania lotów:', error);
            setFlightMessage('Nie udało się pobrać lotów.');
        }
    };

    const fetchFlightById = async (id) => {
        try {
            const response = await axios.get(`http://localhost:8080/flights/getFlightById/${id}`);
            setFlightById(response.data);
        } catch (error) {
            console.error('Błąd pobierania lotu:', error);
            setFlightMessage('Nie udało się pobrać lotu.');
        }
    };

    const fetchFlightsByStatus = async (status) => {
        try {
            const response = await axios.get(`http://localhost:8080/flights/getFlightsByStatus/${status}`);
            setFlights(response.data);
        } catch (error) {
            console.error('Błąd pobierania lotów po statusie:', error);
            setFlightMessage('Nie udało się pobrać lotów po statusie.');
        }
    };

    const updateFlight = async (id, flightRequest) => {
        try {
            const response = await axios.put(`http://localhost:8080/flights/update/${id}`, flightRequest);
            setFlightById(response.data);
            alert('Lot zaktualizowany!');
        } catch (error) {
            console.error('Błąd podczas aktualizowania lotu:', error);
            alert('Nie udało się zaktualizować lotu.');
        }
    };

    const deleteFlight = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/flights/delete/${id}`);
            setFlightById(null);
            alert('Lot usunięty!');
            fetchFlights(); // Ponowne załadowanie listy lotów
        } catch (error) {
            console.error('Błąd podczas usuwania lotu:', error);
            alert('Nie udało się usunąć lotu.');
        }
    };

    const fetchPassengers = async () => {
        try {
            const response = await axios.get('http://localhost:8080/passengers/getAllPassengers');
            setPassengers(response.data);
        } catch (error) {
            console.error('Błąd pobierania pasażerów:', error);
            setPassengerMessage('Nie udało się pobrać pasażerów.');
        }
    };

    const fetchPassengerById = async (id) => {
        try {
            const response = await axios.get(`http://localhost:8080/passengers/getPassengerById/${id}`);
            setPassengerById(response.data);
        } catch (error) {
            console.error('Błąd pobierania pasażera:', error);
            setPassengerMessage('Nie udało się pobrać pasażera.');
        }
    };

    const createPassenger = async (passengerRequest) => {
        try {
            const response = await axios.post('http://localhost:8080/passengers/create', passengerRequest);
            alert('Pasażer został dodany!');
            fetchPassengers(); // Odświeżenie listy pasażerów po dodaniu
        } catch (error) {
            console.error('Błąd podczas dodawania pasażera:', error);
            alert('Nie udało się dodać pasażera.');
        }
    };

    const updatePassenger = async (id, passengerRequest) => {
        try {
            const response = await axios.put(`http://localhost:8080/passengers/update/${id}`, passengerRequest);
            alert('Pasażer został zaktualizowany!');
            fetchPassengerById(id); // Odświeżenie danych pasażera
        } catch (error) {
            console.error('Błąd podczas aktualizowania pasażera:', error);
            alert('Nie udało się zaktualizować pasażera.');
        }
    };

    const deletePassenger = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/passengers/delete/${id}`);
            alert('Pasażer został usunięty!');
            fetchPassengers(); // Odświeżenie listy po usunięciu
        } catch (error) {
            console.error('Błąd podczas usuwania pasażera:', error);
            alert('Nie udało się usunąć pasażera.');
        }
    };

    const fetchWeather = async () => {
        try {
            const response = await axios.get('http://localhost:8080/weather/getAllWeather');
            setWeather(response.data);
        } catch (error) {
            console.error('Błąd pobierania pogody:', error);
            setWeatherMessage('Nie udało się pobrać danych pogodowych.');
        }
    };

    const fetchLatestWeather = async (city) => {
        try {
            const response = await axios.get(`http://localhost:8080/weather/getLatestWeather/${city}`);
            setWeather(response.data);
        } catch (error) {
            console.error('Błąd pobierania najnowszej pogody:', error);
            setWeatherMessage('Nie udało się pobrać najnowszej pogody.');
        }
    };

    const fetchWeatherByCity = async (city) => {
        try {
            const response = await axios.get(`http://localhost:8080/weather/${city}`);
            setWeather(response.data);
        } catch (error) {
            console.error('Błąd pobierania pogody miasta:', error);
            setWeatherMessage('Nie udało się pobrać pogody miasta.');
        }
    };

    const handleCityInputChange = (e) => {
        setCityInput(e.target.value);
    };

    const handleFetchWeather = () => {
        if (cityInput) {
            fetchWeatherByCity(cityInput);
        } else {
            setWeatherMessage('Proszę podać nazwę miasta.');
        }
    };

    return (
        <div className="App">
            <div className="left-column">
                <h1>Miasta</h1>
                {cityMessage && <p>{cityMessage}</p>}
                <CityList cities={cities} />
                <CityForm fetchCities={fetchCities} />
                <CityDetails fetchCityById={fetchCityById} city={cityById} />
            </div>

            <div className="right-column">
                <h1>Lotniska</h1>
                {airportMessage && <p>{airportMessage}</p>}
                <AirportList airports={airports} />
                <AirportForm fetchAirports={fetchAirports} />
                <AirportDetails fetchAirportById={fetchAirportById} airport={airportById} />
            </div>

            <div className="bottom-row">
                <h1>Loty</h1>
                {flightMessage && <p>{flightMessage}</p>}
                <FlightList flights={flights} />
                <FlightForm fetchFlights={fetchFlights} />
                <FlightDetails
                    fetchFlightById={fetchFlightById}
                    flight={flightById}
                    updateFlight={updateFlight}
                    deleteFlight={deleteFlight}
                    fetchFlightsByStatus={fetchFlightsByStatus}
                />
            </div>

            <div className="bottom-row">
                <h1>Pasażerowie</h1>
                {passengerMessage && <p>{passengerMessage}</p>}
                <PassengerList passengers={passengers} fetchPassengerById={fetchPassengerById} deletePassenger={deletePassenger} />
                <PassengerForm createPassenger={createPassenger} />
                <PassengerDetails passenger={passengerById} updatePassenger={updatePassenger} />
            </div>

            <div className="bottom-row">
                <h1>Pogoda</h1>
                {weatherMessage && <p>{weatherMessage}</p>}
                <div className="weather-section">
                    <input
                        type="text"
                        placeholder="Wpisz miasto"
                        value={cityInput}
                        onChange={handleCityInputChange}
                    />
                    <button onClick={handleFetchWeather}>Pogoda dla miasta</button>
                </div>

                {weather && (
                    <div className="weather-table">
                        <h2>Aktualna pogoda:</h2>
                        <table style={{width: '100%', borderCollapse: 'collapse'}}>
                            <thead>
                            <tr>
                                <th style={{border: '1px solid #ddd', padding: '8px'}}>Klucz</th>
                                <th style={{border: '1px solid #ddd', padding: '8px'}}>Wartość</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Miasto</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.cityName}</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Kraj</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.countryName}</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Opis</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.description}</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Temperatura</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.temperature}°C</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Wilgotność</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.humidity}%</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Ciśnienie</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.pressure} hPa</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Prędkość wiatru</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.windSpeed} m/s</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Kierunek wiatru</td>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>{weather.windDegTitle}</td>
                            </tr>
                            <tr>
                                <td style={{border: '1px solid #ddd', padding: '8px'}}>Data pomiaru</td>
                                <td style={{
                                    border: '1px solid #ddd',
                                    padding: '8px'
                                }}>{new Date(weather.timestamp).toLocaleString()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                )}
            </div>


        </div>
    );
}

export default App;
