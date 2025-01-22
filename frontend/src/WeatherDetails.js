import React from 'react';

const WeatherDetails = ({ weatherByCity }) => {
    return (
        <div>
            {weatherByCity ? (
                <>
                    <h2>Szczegóły pogody dla {weatherByCity.cityName}</h2>
                    <p>Temperatura: {weatherByCity.temperature}°C</p>
                    <p>Warunki: {weatherByCity.condition}</p>
                </>
            ) : (
                <p>Wybierz miasto, aby zobaczyć szczegóły.</p>
            )}
        </div>
    );
};

export default WeatherDetails;
