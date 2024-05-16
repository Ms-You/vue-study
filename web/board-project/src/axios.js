import axios from 'axios';
import store from './scripts/store'

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


// 토큰 만료 시 삭제하는 인터셉터 추가
instance.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    const { status } = error.response;

    if(status === 401) {
      store.dispatch('logout')
    }

    return Promise.reject(error);
  }
)

export default instance;
