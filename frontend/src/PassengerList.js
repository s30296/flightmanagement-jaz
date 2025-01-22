import React from 'react';

function PassengerList({ passengers, fetchPassengerById, deletePassenger }) {
    return (
        <div>
            <h2>Lista pasażerów</h2>
            <ul>
                {passengers.map(passenger => (
                    <li key={passenger.id}>
                        {passenger.firstName} {passenger.lastName}
                        <button onClick={() => fetchPassengerById(passenger.id)}>Szczegóły</button>
                        <button onClick={() => deletePassenger(passenger.id)}>Usuń</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PassengerList;
