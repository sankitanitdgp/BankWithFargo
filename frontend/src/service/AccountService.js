import axios from "axios";

const BASE_URL = "http://localhost:8080";

const OpenAccountService = {
    async openAccount(userData){
        try{
            const response=await axios.post(`${BASE_URL}/openAccount`, userData);
            return response.data;
        } catch(error){
            throw error;
        }
    }
};



export {OpenAccountService};

