import React, { useState } from 'react';
import axios from 'axios';

function CityForm({ fetchCities }) {
    const [city, setCity] = useState({
        name: '',
        country: '',
        latitude: '',
        longitude: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCity((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/cities/create', city);
            fetchCities();
            setCity({ name: '', country: '', latitude: '', longitude: '' });
        } catch (error) {
            console.error('Błąd podczas tworzenia miasta:', error);
        }
    };

    return (
        <div>
            <h2>Dodaj Miasto</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="name"
                    value={city.name}
                    onChange={handleChange}
                    placeholder="Nazwa miasta"
                />
                <input
                    type="text"
                    name="country"
                    value={city.country}
                    onChange={handleChange}
                    placeholder="Kraj"
                />
                <input
                    type="number"
                    name="latitude"
                    value={city.latitude}
                    onChange={handleChange}
                    placeholder="Szerokość geograficzna"
                />
                <input
                    type="number"
                    name="longitude"
                    value={city.longitude}
                    onChange={handleChange}
                    placeholder="Długość geograficzna"
                />
                <button type="submit">Dodaj miasto</button>
            </form>
        </div>
    );
}

export default CityForm;
