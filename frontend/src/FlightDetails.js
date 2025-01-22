import React, { useState, useEffect } from 'react';
import axios from 'axios';

function FlightDetails({ flight, fetchFlightById, updateFlight, deleteFlight, fetchFlights }) {
    const [id, setId] = useState('');
    const [notFound, setNotFound] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(''); // Stan na przechowywanie błędów
    const [updatedFlight, setUpdatedFlight] = useState({
        flightNumber: '',
        flightLength: '',
        departureTime: '',
        departureAirportId: '',
        arrivalAirportId: ''
    });

    const [deleteId, setDeleteId] = useState('');

    const handleChange = (e) => {
        setId(e.target.value);
        setNotFound(false);
        setError('');
    };

    const handleDeleteChange = (e) => {
        setDeleteId(e.target.value);
        setError('');
    };

    const handleUpdateChange = (e) => {
        const { name, value } = e.target;
        setUpdatedFlight({
            ...updatedFlight,
            [name]: value
        });
    };

    const handleFetchFlight = async () => {
        const parsedId = parseInt(id, 10);

        if (!id || isNaN(parsedId)) {
            setError('Podaj poprawne ID lotu');
            return;
        }

        setNotFound(false);
        setLoading(true);

        try {
            const response = await fetchFlightById(parsedId);

            if (response && response.id) {
                setUpdatedFlight({
                    flightNumber: response.flightNumber,
                    flightLength: response.flightLength,
                    departureTime: response.departureTime,
                    departureAirportId: response.departureAirportId,
                    arrivalAirportId: response.arrivalAirportId
                });
            } else {
                setNotFound(true);
            }
        } catch (error) {
            setNotFound(true);
            setError('Wystąpił błąd przy pobieraniu danych lotu');
        }

        setLoading(false);
    };

    const handleUpdateFlight = async () => {
        const parsedId = parseInt(id, 10);
        if (isNaN(parsedId)) {
            setError('Podaj poprawne ID lotu do aktualizacji');
            return;
        }

        console.log('Updated Flight:', updatedFlight);

        if (!updatedFlight.flightNumber || !updatedFlight.departureTime || !updatedFlight.arrivalAirportId || !updatedFlight.departureAirportId) {
            setError('Wszystkie pola muszą być wypełnione.');
            return;
        }

        console.log('Aktualizowanie lotu o ID:', parsedId);
        console.log('Dane lotu:', updatedFlight);

        try {
            const response = await updateFlight(parsedId, updatedFlight);
            console.log('Odpowiedź z serwera:', response);
            setError('');
            alert('Lot został zaktualizowany');

            window.location.reload();

            console.log('Lista lotów została zaktualizowana');
        } catch (error) {
            console.error('Błąd podczas aktualizacji:', error);
            setError('Wystąpił błąd podczas aktualizacji lotu');
        }
    };

    const handleDeleteFlight = async () => {
        const parsedId = parseInt(deleteId, 10);
        if (isNaN(parsedId)) {
            setError('Podaj poprawne ID lotu do usunięcia');
            return;
        }

        try {
            await deleteFlight(parsedId);
            alert('Lot został pomyślnie usunięty');
            setDeleteId('');
            setError('');
        } catch (error) {
            setError('Wystąpił błąd podczas usuwania lotu');
            console.error(error);
        }
    };

    useEffect(() => {
        if (flight) {
            setUpdatedFlight({
                flightNumber: flight.flightNumber,
                flightLength: flight.flightLength,
                departureTime: flight.departureTime,
                departureAirportId: flight.departureAirportId,
                arrivalAirportId: flight.arrivalAirportId
            });
        }
    }, [flight]);

    return (
        <div>
            <h2>Szukaj lotu po ID</h2>
            <input
                type="text"
                value={id}
                onChange={handleChange}
                placeholder="Wprowadź ID lotu"
            />
            <button onClick={handleFetchFlight}>Pobierz lot</button>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div>
                <h3>Usuń lot</h3>
                <input
                    type="text"
                    value={deleteId}
                    onChange={handleDeleteChange}
                    placeholder="Wprowadź ID lotu do usunięcia"
                />
                <button onClick={handleDeleteFlight} disabled={!deleteId || loading}>
                    Usuń lot
                </button>
            </div>

            {flight && (
                <div>
                    <h3>Lot: {flight.flightNumber}</h3>
                    <p>Wylot: {flight.departureTime}</p>
                    <p>Status: {flight.status}</p>
                    <p>Lotnisko odlotu: {flight.departureAirport ? `${flight.departureAirport.name}, ${flight.departureAirport.city.name}, ${flight.departureAirport.city.country}` : 'Ładowanie...'}</p>
                    <p>Lotnisko przylotu: {flight.arrivalAirport ? `${flight.arrivalAirport.name}, ${flight.arrivalAirport.city.name}, ${flight.arrivalAirport.city.country}` : 'Ładowanie...'}</p>

                    {flight.weather && (
                        <div>
                            <h4>Pogoda na lotnisku odlotu ({flight.weather.cityName}):</h4>
                            <p><strong>Opis:</strong> {flight.weather.description}</p>
                            <p><strong>Temperatura:</strong> {flight.weather.temperature}°C</p>
                            <p><strong>Wiatr:</strong> {flight.weather.windSpeed} m/s, Kierunek: {flight.weather.windDegTitle}</p>
                            <p><strong>Wilgotność:</strong> {flight.weather.humidity}%</p>
                            <p><strong>Ciśnienie:</strong> {flight.weather.pressure} hPa</p>
                        </div>
                    )}
                </div>
            )}

            <h3>Aktualizuj lot</h3>
            <input
                type="text"
                value={id}
                onChange={handleChange}
                placeholder="Wprowadź ID lotu"
            />
            <input
                type="text"
                name="flightNumber"
                value={updatedFlight.flightNumber}
                onChange={handleUpdateChange}
                placeholder="Numer lotu"
            />
            <input
                type="number"
                name="flightLength"
                value={updatedFlight.flightLength}
                onChange={handleUpdateChange}
                placeholder="Długość lotu (godziny)"
            />
            <input
                type="datetime-local"
                name="departureTime"
                value={updatedFlight.departureTime}
                onChange={handleUpdateChange}
                placeholder="Czas wylotu"
            />
            <input
                type="number"
                name="departureAirportId"
                value={updatedFlight.departureAirportId}
                onChange={handleUpdateChange}
                placeholder="ID lotniska odlotu"
            />
            <input
                type="number"
                name="arrivalAirportId"
                value={updatedFlight.arrivalAirportId}
                onChange={handleUpdateChange}
                placeholder="ID lotniska przylotu"
            />
            <button onClick={handleUpdateFlight}>Zaktualizuj lot</button>
        </div>
    );
}

export default FlightDetails;
