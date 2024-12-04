import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import evaluaService from '../services/evalua.service';

function updateState() {
    const [userId, setUserId] = useState('');
    const [creditId, setCreditId] = useState('');
    const [state, setState] = useState('');
    const navigate = useNavigate();

    const handleUserIdChange = (e) => {
        setUserId(e.target.value);
    };

    const handleCreditIdChange = (e) => {
        setCreditId(e.target.value);
    };

    const handleStateChange = (e) => {
        setState(e.target.value);
    };

    const stateDescriptions = [
        "Estado: EN REVISIÓN INICIAL",
        "Estado: PENDIENTE DE DOCUMENTACIÓN",
        "Estado: EN EVALUACIÓN",
        "Estado: PRE-APROBADA",
        "Estado: EN APROBACIÓN FINAL",
        "Estado: APROBADA",
        "Estado: RECHAZADA",
        "Estado: CANCELADA POR EL CLIENTE",
        "Estado: EN DESEMBOLSO",
        "Estado: PENDIENTE"
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await evaluaService.updateState(userId, creditId, state);
            if (response.data === "Evaluación actualizada") {
                alert('ESTADO ACTUALIZADO EXITOSAMENTE');
                navigate('/home/Client');
            } else {
                alert('Error al actualizar la evaluación');
            }
        } catch (error) {
            alert('Error al actualizar el estado: ' + (error.response?.data || error.message));
        }
    };

    return (
        <div className="container">
            <h2>Actualizar Estado del Usuario</h2>
            <form onSubmit={handleSubmit}>
            {/*---------------------------------------------------------------------------------------------*/}                
                <div className="mb-3">
                    <label>User ID:</label>
                    <input type="number" id="userId" value={userId} onChange={handleUserIdChange} className="form-control" placeholder="Ejemplo: 1" required />
                    <small className="form-text text-muted">
                        Ingrese el ID de su cuenta. Ej 102
                    </small>
                </div>
            {/*---------------------------------------------------------------------------------------------*/}                
                <div className="mb-3">
                    <label>Credit ID:</label>
                    <input type="number" id="creditId" value={creditId} onChange={handleCreditIdChange} className="form-control" placeholder="Ejemplo: 1" required />
                    <small className="form-text text-muted">
                        Ingrese el ID del crédito. Ej 202
                    </small>
                </div>
            {/*---------------------------------------------------------------------------------------------*/}                
                <div className="mb-3">
                    <label>Nuevo Estado:</label>
                    <select value={state} onChange={handleStateChange} className="form-control" required>
                        <option value="" disabled>SELECCIONE UN ESTADO PARA LA SOLICITUD</option>
                        {stateDescriptions.map((description, index) => (
                            <option key={index + 1} value={index + 1} title={description}>
                                {index + 1} - {description}
                            </option>
                        ))}
                    </select>
                </div>
            {/*---------------------------------------------------------------------------------------------*/}
                <div className="d-grid gap-2">
                    <button type="submit" className="btn btn-primary btn-lg">Actualizar Estado</button>
                    <button type="button" onClick={() => navigate('/home/Client')} className="btn btn-danger btn-lg">Regresar a Operaciones del Cliente</button>
                </div>
            </form>
            {/*---------------------------------------------------------------------------------------------*/}
            <div className="mt-3 d-grid gap-2">
                <button 
                className="btn btn-dark btn-lg"
                onClick={() => navigate('/home/Client')}
                >
                Regresar a Operaciones del Cliente
                </button>
            </div>
            {/*---------------------------------------------------------------------------------------------*/}            
        </div>
    );
}

export default updateState;