import React, { useState, useEffect } from 'react';

function PassengerDetails({ passenger, updatePassenger }) {
    const [updatedPassenger, setUpdatedPassenger] = useState(passenger || {});
    const [passengerId, setPassengerId] = useState('');
    const [newFirstName, setNewFirstName] = useState('');
    const [newLastName, setNewLastName] = useState('');
    const [newEmail, setNewEmail] = useState('');
    const [newFlights, setNewFlights] = useState([]);

    useEffect(() => {
        if (passenger) {
            setPassengerId(passenger.id || '');
            setNewFirstName(passenger.firstName || '');
            setNewLastName(passenger.lastName || '');
            setNewEmail(passenger.email || '');
            setNewFlights(passenger.flights || []);
        }
    }, [passenger]);

    const handleUpdate = () => {
        if (!passengerId) {
            alert('Proszę wprowadzić ID pasażera.');
            return;
        }

        const passengerRequest = {
            firstName: newFirstName,
            lastName: newLastName,
            email: newEmail,
            flights: newFlights
        };

        updatePassenger(passengerId, passengerRequest);
    };

    return (
        <div>
            <h2>Aktualizacja Pasażera</h2>
            <div>
                <label>Id Pasażera:</label>
                <input
                    type="text"
                    value={passengerId}
                    onChange={(e) => setPassengerId(e.target.value)}
                    placeholder="Wprowadź ID pasażera"
                />
            </div>
            <div>
                <label>Imię:</label>
                <input
                    type="text"
                    value={newFirstName}
                    onChange={(e) => setNewFirstName(e.target.value)}
                    placeholder="Wprowadź imię"
                />
            </div>
            <div>
                <label>Nazwisko:</label>
                <input
                    type="text"
                    value={newLastName}
                    onChange={(e) => setNewLastName(e.target.value)}
                    placeholder="Wprowadź nazwisko"
                />
            </div>
            <div>
                <label>Email:</label>
                <input
                    type="email"
                    value={newEmail}
                    onChange={(e) => setNewEmail(e.target.value)}
                    placeholder="Wprowadź email"
                />
            </div>
            <div>
                <label>Loty:</label>
                <input
                    type="text"
                    value={newFlights}
                    onChange={(e) => setNewFlights(e.target.value.split(',').map(Number))} // Ensure numbers
                    placeholder="Wprowadź numery lotów, oddzielone przecinkami"
                />
            </div>
            <button onClick={handleUpdate}>Zaktualizuj Pasażera</button>

            {passenger && (
                <div className="passenger-details">
                    <h3>Informacje o Pasażerze</h3>
                    <ul>
                        <li><strong>Imię:</strong> {passenger.firstName}</li>
                        <li><strong>Nazwisko:</strong> {passenger.lastName}</li>
                        <li><strong>Email:</strong> {passenger.email}</li>
                    </ul>
                    <h4>Loty:</h4>
                    {passenger.flights && passenger.flights.length > 0 ? (
                        <ul>
                            {passenger.flights.map((flight, index) => (
                                <li key={flight.id}>
                                    <strong>Numer lotu:</strong> {flight.flightNumber}<br />
                                    <strong>Odlot:</strong> {new Date(flight.departureTime).toLocaleString()}<br />
                                    <strong>Przylot:</strong> {new Date(flight.arrivalTime).toLocaleString()}<br />
                                    <strong>Lotnisko odlotu:</strong> {flight.departureAirport.name} ({flight.departureAirport.city.name}, {flight.departureAirport.city.country})<br />
                                    <strong>Lotnisko przylotu:</strong> {flight.arrivalAirport.name} ({flight.arrivalAirport.city.name}, {flight.arrivalAirport.city.country})<br />
                                    <strong>Status:</strong> {flight.status}<br />
                                    <strong>Wyniki pogody:</strong> {flight.weather.description} (Temp: {flight.weather.temperature}°C, Wiatr: {flight.weather.windSpeed} km/h)
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Brak lotów</p>
                    )}
                </div>
            )}
        </div>
    );
}

export default PassengerDetails;
