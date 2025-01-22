import React from 'react';

const WeatherList = ({ weather }) => {
    return (
        <div>
            <h2>Lista pogody</h2>
            <ul>
                {weather.map((weatherItem) => (
                    <li key={weatherItem.id}>
                        {weatherItem.cityName}: {weatherItem.temperature}°C, {weatherItem.condition}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default WeatherList;
