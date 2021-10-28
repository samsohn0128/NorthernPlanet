import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
const instance = axios.create({
  // baseURL: 'https://i5a107.p.ssafy.io:8446/mypage/',
  baseURL: BASE_URL + '/presentation/',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

function getpresentations(userData) {
  return instance.get('list/' + userData.user_Id);
}

function addpresentation(userData) {
  return instance.post('', userData);
}

export { getpresentations, addpresentation };
