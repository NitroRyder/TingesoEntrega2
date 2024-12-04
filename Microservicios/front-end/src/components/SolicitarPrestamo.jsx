import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import creditoService from '../services/credito.service';

const registrarCredito = () => {
  const [formData, setFormData] = useState({
    montop: '',
    plazo: '',
    intanu: '',
    intmen: '',
    segudesg: '',
    seguince: '',
    comiad: '',
    usuarioId: ''
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const {montop, plazo, intanu, intmen, segudesg, seguince, comiad, usuarioId} = formData;
    if(usuarioId === '' || montop === '' || plazo === '' || intanu === '' || intmen === '' || segudesg === '' || seguince === '' || comiad === '') {
      alert("ERROR: DEBE INGRESAR TODOS LOS DATOS");
      return;
    }
    if (montop <= 0) {
      alert("ERROR: EL MONTO DEL PRÉSTAMO DEBE SER MAYOR A 0");
      return;
    }
    if (plazo <= 0) {
      alert("ERROR: EL PLAZO DEL PRÉSTAMO DEBE SER MAYOR A 0");
      return;
    }
    if (intanu <= 0) {
      alert("ERROR: LA TASA DE INTERÉS ANUAL DEBE SER MAYOR A 0");
      return;
    }
    if (intmen <= 0) {
      alert("ERROR: LA TASA DE INTERÉS MENSUAL DEBE SER MAYOR A 0");
      return;
    }
    if (segudesg <= 0) {
      alert("ERROR: EL SEGURO DE DESGRAVAMEN DEBE SER MAYOR A 0");
      return;
    }
    if (seguince <= 0) {
      alert("ERROR: EL SEGURO DE INCENDIO DEBE SER MAYOR A 0");
      return;
    }
    if (comiad <= 0) {
      alert("ERROR: LA COMISIÓN ADMINISTRATIVA DEBE SER MAYOR A 0");
      return;
    }
    if(usuarioId <= 0) {
      alert("ERROR: EL USER ID DEBE SER MAYOR A 0");
      return;
    }
    try {
      const response = await creditoService.registrarCredito(formData);
      if (response.data === -2) {
        alert('ERROR: EL USER ID INGRESADO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA, POR FAVOR INGRESAR UN USER ID REGISTRADO O REGISTRARSE EN EL SISTEMA.');
      } else {
        alert('Solicitud de crédito creada o actualizada correctamente');
        navigate('/documento/register');
      }
    } catch (error) {
      alert('Error al crear la solicitud de crédito: ' + error.response.data);
    }
  };

  return (
    <div className="container">
      <h2>Solicitar Préstamo</h2>
      <form onSubmit={handleSubmit}>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Monto del préstamo:</label>
          <input type="number" name="montop" value={formData.montop} onChange={handleChange} className="form-control" placeholder="Ejemplo: 100000" required />
          <small className="form-text text-muted">
            Ingrese el monto del prestamo que necesita. Ej: 100000 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Plazo (años):</label>
          <input type="number" name="plazo" value={formData.plazo} onChange={handleChange} className="form-control" placeholder="Ejemplo: 20" required />
          <small className="form-text text-muted">
            Ingrese la cantidad de años que desea que dure el pago préstamo. Ej: 20 años.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Tasa de interés anual:</label>
          <input type="number" step="0.01" name="intanu" value={formData.intanu} onChange={handleChange} className="form-control" placeholder="Ejemplo: 0.05" required />
          <small className="form-text text-muted">
            Ingrese la tasa de interes anual. Ej: 0.05 para 5%.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Tasa de interés mensual:</label>
          <input type="number" step="0.01" name="intmen" value={formData.intmen} onChange={handleChange} className="form-control" placeholder="Ejemplo: 0.04" required />
          <small className="form-text text-muted">
            Ingrese la tasa de interes mensual. Ej: 0.04 para 4%.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Seguro de desgravamen:</label>
          <input type="number" step="0.01" name="segudesg" value={formData.segudesg} onChange={handleChange} className="form-control" placeholder="Ejemplo: 200" required />
          <small className="form-text text-muted">
            Ingrese el valor del seguro de desgravamen. Ej: 200 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Seguro de incendio:</label>
          <input type="number" step="0.01" name="seguince" value={formData.seguince} onChange={handleChange} className="form-control" placeholder="Ejemplo: 150" required />
          <small className="form-text text-muted">
            Ingrese el valor del seguro de incendio. Ej: 150 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}  
        <div className="mb-3">
          <label>Comisión administrativa:</label>
          <input type="number" step="0.01" name="comiad" value={formData.comiad} onChange={handleChange} className="form-control" placeholder="Ejemplo: 50" required />
          <small className="form-text text-muted">
            Ingrese el valor de la comisión administrativa. Ej: 50 pesos Chilenos.
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label>User ID:</label>
          <input type="number" name="usuarioId" value={formData.usuarioId} onChange={handleChange} className="form-control" placeholder="Ejemplo: 102" required />
          <small className="form-text text-muted">
            Ingrese el ID de su cuenta. Ej 102
          </small>
        </div>
        {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary btn-lg">Solicitar Préstamo</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}  
      {/*
      <div className="mt-3 d-grid gap-2">
          <button 
            className="btn btn-danger btn-lg" 
            onClick={() => navigate('/documento/register')}
          >
            Ingresar Documentación
          </button>
      </div>
      */}
      {/*---------------------------------------------------------------------------------------------*/}        
      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-dark btn-lg" 
          onClick={() => navigate('/home/Client')}
        >
          Regresar a Operaciones del Cliente
        </button>
      </div>
    </div>
  );
};

export default registrarCredito;