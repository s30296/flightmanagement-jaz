import React, { useState } from 'react';
import axios from 'axios';

function CityDetails({ setCityById }) {
    const [id, setId] = useState('');
    const [city, setCity] = useState(null);
    const [cityNotFound, setCityNotFound] = useState(false);

    const handleChange = (e) => {
        setId(e.target.value);
    };

    const handleFetchCity = async () => {
        console.log("Button clicked, fetching city with ID:", id);
        if (id) {
            try {
                const response = await axios.get(`http://localhost:8080/cities/getCityById/${id}`);
                console.log("City fetched:", response.data);
                setCity(response.data);
                setCityNotFound(false);
            } catch (error) {
                console.error('Błąd podczas pobierania miasta:', error);
                setCityNotFound(true);
            }
        }
    };

    return (
        <div>
            <h2>Szukaj miasta po ID</h2>
            <input
                type="text"
                value={id}
                onChange={handleChange}
                placeholder="Wprowadź ID miasta"
            />
            <button onClick={handleFetchCity}>Pobierz miasto</button>

            {city && !cityNotFound && (
                <div>
                    <h3>Miasto: {city.name}</h3>
                    <p>{city.name}, {city.country}</p>
                    <p>Lat: {city.latitude}, Long: {city.longitude}</p>
                </div>
            )}

            {cityNotFound && (
                <p>Nie znaleziono miasta o tym ID</p>
            )}
        </div>
    );
}

export default CityDetails;
