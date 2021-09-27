import axios from "axios";
export function requestJfinal(config){
    const instance = axios.create({
        headers:{
            post:{'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'}	//post请求头
        },
        baseURL:"/Jfinal",
        timeout:5000,
        withCredentials: true //允许请求携带cookie信息
    })
    return instance(config)
}
