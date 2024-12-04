import httpClient from "../http-common";

//--------------------------------------------------------------------//
const followCredito = (userId) => {
    return httpClient.get('/seguimiento/followCredito', {
        params: { userId }
    });
};
//--------------------------------------------------------------------//
export default {followCredito};