import httpClient from "../http-common";

// BASE DE LA DIRECCIÓN = /usuario
//--------------------------------------------------------------------//
const getAll = () => {
    return httpClient.get('/usuario');
}
//--------------------------------------------------------------------//
const getById = id => {
    return httpClient.get(`/usuario/${id}`);
}
//--------------------------------------------------------------------//
const save = async (id, userData) => {
    return httpClient.post('/usuario', null, {
        params: { id, ...userData }
    });
};
//--------------------------------------------------------------------//
const getAhorros = usuarioId => {
    return httpClient.get(`/usuario/ahorros/${usuarioId}`);
};
//--------------------------------------------------------------------//
const getCreditos = usuarioId => {
    return httpClient.get(`/usuario/creditos/${usuarioId}`);
};
//--------------------------------------------------------------------//
const saveAhorro = (usuarioId, ahorro) => {
    return httpClient.post(`/usuario/saveahorro/${usuarioId}`, ahorro);
};
//--------------------------------------------------------------------//
const saveCredito = (usuarioId, credito) => {
    return httpClient.post(`/usuario/savecredito/${usuarioId}`, credito);
};
//--------------------------------------------------------------------//
const addNotification = (userId, notification) => {
    return httpClient.post(`/usuario/addnotification/${userId}`, notification);
};
//--------------------------------------------------------------------//
const getNotifications = (userId) => {
    return httpClient.get(`/usuario/notifications/${userId}`);
};
//--------------------------------------------------------------------//
const deleteUsuario = id => {
    return httpClient.delete(`/usuario/${id}`);
};
//--------------------------------------------------------------------//
const clearNotifications = userId => {
    return httpClient.delete(`/usuario/clearNotifications/${userId}`);
};
//--------------------------------------------------------------------//
const registerUsuario = usuario => {
    return httpClient.post('/usuario/register', usuario);
};
//--------------------------------------------------------------------//
export default {getAll, getById, save, getAhorros, getCreditos, saveAhorro, saveCredito, addNotification, getNotifications, deleteUsuario, clearNotifications, registerUsuario};