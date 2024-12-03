import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import usuarioServices from '../services/usuario.service';
{/*---------------------------------------------------------------------------------------------------*/}  
function registerUsuario() {
  const [usuario, setUsuario] = useState({
    rut: '',
    name: '',
    age: '',
    workage: '',
    houses: '',
    valorpropiedad: '',
    ingresos: '',
    sumadeuda: '',
    objective: '',
    independiente: ''
  });
{/*---------------------------------------------------------------------------------------------------*/}  
  const [showModal, setShowModal] = useState(false);
  const [showCreditoModal, setShowCreditoModal] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUsuario({ ...usuario, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
{/*---------------------------------------------------------------------------------------------------*/}  
    const rut = usuario.rut;
    if (rut.length < 7 || rut.length > 12) {
      alert('ERROR: EL RUT INGRESADO NO ES VÁLIDO. POR FAVOR INGRESAR UN RUT VÁLIDO.');
      return;
    }
    if (rut[rut.length - 2] !== '-') {
      alert('ERROR: EL RUT DEBE CONTENER UN "-" EN EL PENÚLTIMO CARÁCTER.');
      return;
    }
    const { name, age, workage, houses, valorpropiedad, ingresos, sumadeuda, objective, independiente} = usuario;
    if(age < 18){
      alert('ERROR: DEBE SER MAYOR DE EDAD PARA REGISTRARSE.');
      return;
    }
    if(workage < 0 || houses < 0 || valorpropiedad < 0 || ingresos < 0 || sumadeuda < 0){
      alert('ERROR: LOS VALORES INGRESADOS NO PUEDEN SER NEGATIVOS O IGUAL A 0.');
      return;
    }
    if(objective === '' || independiente === ''){ 
      alert('ERROR: DEBE LLENAR LOS CAMPOS DE Objetivo y Tipo de trabajador.');
      return;
    }
    if(objective !== 'REMODELACION' && objective !== 'PROPIEDAD COMERCIAL' && objective !== 'PRIMERA VIVIENDA' && objective !== 'SEGUNDA VIVIENDA'){
      alert('ERROR: EL OBJETIVO DEBE SER REMODELACION, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.');
      return;
    }
    if(independiente !== 'INDEPENDIENTE' && independiente !== 'ASALARIADO'){
      alert('ERROR: EL TIPO DE TRABAJADOR DEBE SER INDEPENDIENTE O ASALARIADO.');
      return;
    }
{/*---------------------------------------------------------------------------------------------------*/}      
    try {
      const response = await usuarioServices.register({ ...usuario });
      if(response.data === -2){
        alert('ERROR: EL RUT INGRESADO YA SE ENCUENTRA REGISTRADO EN EL SISTEMA.');
      }else{
        alert('Usuario registrado con éxito');
        alert('ATENCIÓN, el ID del usuario es: ' + response.data.id);
        navigate('/home/Client');
      }

    } catch (error) {
      alert('ERROR AL REGISTRAR UN USUARIO DEL TIPO: ' + error.response.data);
    }
  };
{/*---------------------------------------------------------------------------------------------------*/}  
      {/*---------------------------------------------------------------------------------------------*/}
  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const handleShowCreditoModal = () => setShowCreditoModal(true);
  const handleCloseCreditoModal = () => setShowCreditoModal(false);

  return (
    <div className="container mt-5">
      <h2>Registrar Usuario</h2>
      <form onSubmit={handleSubmit}>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">RUT:</label>
          <input type="text" className="form-control" name="rut" value={usuario.rut} onChange={handleChange} placeholder="Ejemplo: 12345678-9" required />
          <small className="form-text text-muted">
            Formato: 12345678-9
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Nombre:</label>
          <input type="text" className="form-control" name="name" value={usuario.name} onChange={handleChange} placeholder="Ejemplo: Elsa Payo" required />
          <small className="form-text text-muted">
            Ingrese el nombre y apellido, separados por un espacio. Ej: Elsa Payo.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Edad:</label>
          <input type="number" className="form-control" name="age" value={usuario.age} onChange={handleChange} placeholder="Ejemplo: 20" required />
          <small className="form-text text-muted">
            Ingrese su edad actual, para poder registrarse, tiene que ser mayor de 18 años.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Años de Trabajo:</label>
          <input type="number" className="form-control" name="workage" value={usuario.workage} onChange={handleChange} placeholder="Ejemplo: 30" required />
          <small className="form-text text-muted">
            Ingrese los años que lleva empleado en su trabajo mas reciente. Ej: 3 años.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Número de Casas:</label>
          <input type="number" className="form-control" name="houses" value={usuario.houses} onChange={handleChange} placeholder="Ejemplo: 1" required />
          <small className="form-text text-muted">
            Ingrese la cantidad de propiedades que posea en este momento. Ej: 1 casa.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Valor de Propiedad:</label>
          <input type="number" className="form-control" name="valorpropiedad" value={usuario.valorpropiedad} onChange={handleChange} placeholder="Ejemplo: 1000000" required/>
          <small className="form-text text-muted">
            Ingrese el valor de su propiedad. Ej: 1000000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Ingresos Mensuales:</label>
          <input type="number" className="form-control" name="ingresos" value={usuario.ingresos} onChange={handleChange} placeholder="Ejemplo: 500000" required />
          <small className="form-text text-muted">
            Ingrese el valor de sus ingresos mensuales. Ej: 500000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}        
        <div className="mb-3">
          <label className="form-label">Deuda Total:</label>
          <input type="number" className="form-control" name="sumadeuda" value={usuario.sumadeuda} onChange={handleChange} placeholder="Ejemplo: 240000" required/>
          <small className="form-text text-muted">
            Ingrese el valor de la suma de todas sus deudas actuales. Ej: 240000 pesos Chilenos.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Objetivo:</label>
          <input type="text" className="form-control" name="objective" value={usuario.objective} onChange={handleChange} placeholder="ESCOGER ENTRE: REMODELACION | PROPIEDAD COMERCIAL | PRIMERA VIVIENDA | SEGUNDA VIVIENDA" required/>
          <small className="form-text text-muted">
            Ingrese el motivo por el cual va a querer registrarse. Ej; REMODELACION, PROPIEDAD COMERCIAL, PRIMERA VIVIENDA O SEGUNDA VIVIENDA.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="mb-3">
          <label className="form-label">Tipo de trabajador:</label>
          <input type="text" className="form-control" name="independiente" value={usuario.independiente} onChange={handleChange} placeholder="ESCOGER ENTRE: INDEPENDIENTE | ASALARIADO" required />
          <small className="form-text text-muted">
            Ingrese si es un trabajador independiente o asalariado. Ej: INDEPENDIENTE, ASALARIADO.
          </small>
        </div>
      {/*---------------------------------------------------------------------------------------------*/}
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary mt-3 btn-lg">Registrar</button>
        </div>
      </form>
      {/*---------------------------------------------------------------------------------------------*/}
      <div className="mt-3 d-grid gap-2">
        <button 
          className="btn btn-danger btn-lg" 
          onClick={() => navigate('/ahorro/solicitar')}
        >
          Agregar Ahorros a un Usuario
        </button>
      </div>
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
}
export default registerUsuario;