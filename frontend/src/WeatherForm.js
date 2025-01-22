import React, { useState } from 'react';
import axios from 'axios';

const WeatherForm = ({ fetchWeather }) => {
    const [cityName, setCityName] = useState('');
    const [temperature, setTemperature] = useState('');
    const [condition, setCondition] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const weatherData = {
            cityName,
            temperature,
            condition,
        };

        try {
            await axios.post('http://localhost:8080/weather/create', weatherData);
            fetchWeather();
            setCityName('');
            setTemperature('');
            setCondition('');
        } catch (error) {
            console.error('Błąd podczas dodawania pogody:', error);
        }
    };

    return (
        <div>
            <h2>Dodaj prognozę pogody</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Miasto:
                    <input
                        type="text"
                        value={cityName}
                        onChange={(e) => setCityName(e.target.value)}
                        required
                    />
                </label>
                <label>
                    Temperatura (°C):
                    <input
                        type="number"
                        value={temperature}
                        onChange={(e) => setTemperature(e.target.value)}
                        required
                    />
                </label>
                <label>
                    Warunki:
                    <input
                        type="text"
                        value={condition}
                        onChange={(e) => setCondition(e.target.value)}
                        required
                    />
                </label>
                <button type="submit">Dodaj pogodę</button>
            </form>
        </div>
    );
};

export default WeatherForm;
