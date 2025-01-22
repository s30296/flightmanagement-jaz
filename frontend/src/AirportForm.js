import React, { useState } from 'react';
import axios from 'axios';

function AirportForm({ fetchAirports }) {
    const [name, setName] = useState('');
    const [cityId, setCityId] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/airports/create', { name, cityId });
            console.log("Lotnisko utworzone:", response.data);
            fetchAirports();
        } catch (error) {
            console.error('Błąd podczas tworzenia lotniska:', error);
        }
    };

    return (
        <div>
            <h2>Dodaj nowe lotnisko</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Nazwa lotniska"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="ID miasta"
                    value={cityId}
                    onChange={(e) => setCityId(e.target.value)}
                    required
                />
                <button type="submit">Dodaj lotnisko</button>
            </form>
        </div>
    );
}

export default AirportForm;
