import axios from 'axios';

// 요청 헤더에 accessToken 세팅하는 axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8088',
});

// request 인터셉터 추가
instance.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem('accessToken');

  if(accessToken) {
    // 토큰이 존재하면 헤더에 추가
    config.headers.Authorization = accessToken;
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});

export default instance;
