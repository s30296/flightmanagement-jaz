// PassengerForm.js
import React, { useState } from 'react';

function PassengerForm({ createPassenger }) {
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [flights, setFlights] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const passengerRequest = {
            email,
            firstName,
            lastName,
            flights: flights.split(',').map(flight => parseInt(flight.trim()))
        };
        await createPassenger(passengerRequest);
        setEmail('');
        setFirstName('');
        setLastName('');
        setFlights('');
    };

    return (
        <div>
            <h2>Dodaj pasażera</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Email"
                    required
                />
                <input
                    type="text"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder="Imię"
                    required
                />
                <input
                    type="text"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder="Nazwisko"
                    required
                />
                <input
                    type="text"
                    value={flights}
                    onChange={(e) => setFlights(e.target.value)}
                    placeholder="Numer lotu (oddzielone przecinkami)"
                    required
                />
                <button type="submit">Dodaj</button>
            </form>
        </div>
    );
}

export default PassengerForm;
