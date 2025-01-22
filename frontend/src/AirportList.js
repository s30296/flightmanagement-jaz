import React from 'react';

function AirportList({ airports }) {
    return (
        <div>
            <h2>Lista Lotnisk</h2>
            <ul>
                {airports.map((airport) => (
                    <li key={airport.id}>
                        {airport.name} (ID: {airport.id})
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default AirportList;
