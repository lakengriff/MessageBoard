import axios from "axios";
import store from '../store/index'

const http = axios.create({
    baseURL: "http://localhost:9000"
})
 axios.defaults.headers.common['Authorization'] = 'Bearer ' + store.state.token;

export default {
    getForumList(){
        return http.get('/forums');
    },
    getForum(id){
        return http.get(`/forums/${id}`);
    },
    getMods(id){
        return http.get(`/forums/${id}/mods`)
    },
    createForum(forum){
        return http.post('/forums', forum);
    },
    promoteUserToMod(id, user){
        return http.post(`/forums/${id}/mods`, user)
    }
}