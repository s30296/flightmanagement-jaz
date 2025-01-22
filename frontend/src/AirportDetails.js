import React, { useState } from 'react';
import axios from 'axios';

function AirportDetails({ fetchAirportById, airport }) {
    const [id, setId] = useState('');

    const handleChange = (e) => {
        setId(e.target.value);
    };

    const handleFetchAirport = async () => {
        if (id) {
            await fetchAirportById(id);
        }
    };

    return (
        <div>
            <h2>Szukaj lotniska po ID</h2>
            <input
                type="text"
                value={id}
                onChange={handleChange}
                placeholder="Wprowadź ID lotniska"
            />
            <button onClick={handleFetchAirport}>Pobierz lotnisko</button>

            {airport && (
                <div>
                    <h3>Lotnisko: {airport.name}</h3>
                    <p>Miasto: {airport.cityId}</p>
                </div>
            )}
        </div>
    );
}

export default AirportDetails;
