import axios from "axios";

const BASE_URL = "http://localhost:8080";

const UserService = {
    async login(userData){
        try{
            const response=await axios.post(`${BASE_URL}/verifyUser`, userData);
            return response.data;
        } catch(error){
            throw error;
        }
    }
};

export default UserService;