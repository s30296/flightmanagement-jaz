import React from 'react';

function CityList({ cities }) {
    return (
        <div>
            <h2>Lista miast</h2>
            <ul>
                {cities.map((city) => (
                    <li key={city.id}>
                        {city.name}, {city.country} - Lat: {city.latitude}, Long: {city.longitude}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default CityList;
