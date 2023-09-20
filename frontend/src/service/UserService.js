import axios from "axios";

const BASE_URL = "http://localhost:8080";

const UserService = {
    async login(userData){
        try{
            const response=await axios.post(`${BASE_URL}/loginUser`, userData);
            return response.data;
        } catch(error){
            throw error;
        }
    },
    async register(userData){
        try{
            const response=await axios.post(`${BASE_URL}/addUser`, userData);
            return response.data;
        } catch(error){
            throw error;
        }
    },
    async getAllUsers(config){
        try{
            const response=await axios.post(`${BASE_URL}/getAllUsers`, null, config);
            return response.data;
        } catch(error){
            throw error;
        }
    }
};


export {UserService};

