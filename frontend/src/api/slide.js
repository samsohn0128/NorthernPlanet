import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
const instance = axios.create({
  baseURL: BASE_URL + '/slide',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

function getSlide(slideId) {
  return instance.get('?slideId=' + slideId);
}

function updateScript(updateScriptReq) {
  return instance.put('/script', updateScriptReq);
}

export { getSlide, updateScript };
