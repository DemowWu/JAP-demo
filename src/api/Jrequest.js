import axios from 'axios'

const instance = axios.create({
    timeout:40000,
    crossDomain: true,
    withCredentials:true,
    baseURL:'http://127.0.0.1:8091/jap-demo/',
})

export function toIndex(){
    return instance({
        baseURL:'',
        method:'get'
    })
}
export function toPasswordB(params){
    return instance({
        url:'index-simple/test',
        method:'post',
        // params: {username:params.username,password:params.password},
        data:params,
    })
}
