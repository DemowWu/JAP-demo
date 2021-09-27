import axios from "axios";
export function requestActframework(config){
    const instance = axios.create({
        headers:{
            post:{'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'}	//post请求头
        },
        baseURL:"/ActFramework",
        timeout:5200,
        withCredentials: true //允许请求携带cookie信息
    })
    return instance(config)
}
