import React, { useState } from 'react';
import axios from 'axios';

function FlightForm({ fetchFlights }) {
    const [flightNumber, setFlightNumber] = useState('');
    const [flightLength, setFlightLength] = useState('');
    const [departureTime, setDepartureTime] = useState('');
    const [departureAirportId, setDepartureAirportId] = useState('');
    const [arrivalAirportId, setArrivalAirportId] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/flights/create', {
                flightNumber,
                flightLength,
                departureTime,
                departureAirportId,
                arrivalAirportId
            });
            console.log("Lot utworzony:", response.data);
            fetchFlights();
        } catch (error) {
            console.error('Błąd tworzenia lotu:', error);
        }
    };

    return (
        <div>
            <h3>Dodaj nowy lot</h3>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Numer lotu"
                    value={flightNumber}
                    onChange={(e) => setFlightNumber(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="Czas trwania"
                    value={flightLength}
                    onChange={(e) => setFlightLength(e.target.value)}
                    required
                />
                <input
                    type="datetime-local"
                    value={departureTime}
                    onChange={(e) => setDepartureTime(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="ID lotniska odlotu"
                    value={departureAirportId}
                    onChange={(e) => setDepartureAirportId(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="ID lotniska przylotu"
                    value={arrivalAirportId}
                    onChange={(e) => setArrivalAirportId(e.target.value)}
                    required
                />
                <button type="submit">Dodaj lot</button>
            </form>
        </div>
    );
}

export default FlightForm;
