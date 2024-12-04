import httpClient from "../http-common";

//--------------------------------------------------------------------//
const simularCredito = (P, r, n, V, tipo) => {
    return httpClient.get('/simula/simular', {
        params: { P, r, n, V, tipo }
    });
};

//--------------------------------------------------------------------//
export default {simularCredito};