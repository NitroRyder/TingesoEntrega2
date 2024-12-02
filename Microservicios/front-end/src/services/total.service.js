import httpClient from "../http-common";

//--------------------------------------------------------------------//
const calcularCostosTotales = (userId, creditId) => {
    return httpClient.get('/total/calcularCostosTotales', {
        params: { userId, creditId }
    });
};
//--------------------------------------------------------------------//
export default {calcularCostosTotales};