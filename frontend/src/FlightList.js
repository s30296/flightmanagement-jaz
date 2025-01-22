import React from 'react';

function FlightList({ flights }) {
    return (
        <div>
            <h3>Lista lotów</h3>
            <ul>
                {flights.map((flight) => (
                    <li key={flight.id}>{flight.flightNumber} - {flight.departureTime}</li>
                ))}
            </ul>
        </div>
    );
}

export default FlightList;
