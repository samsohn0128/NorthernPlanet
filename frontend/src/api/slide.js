import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
import store from '@/store';
// const instance = axios.create({
//   baseURL: BASE_URL + '/slide',
//   headers: {
//     'Content-type': 'application/json',
//   },
//   withCredentials: true,
// });

const oauth = axios.create({
  baseURL: `${BASE_URL}/slide`,
  headers: {
    'Content-type': 'application/json',
    Authorization: `Bearer ${store.getters['users/getToken']}`,
  },
  withCredentials: true,
});

function getSlide(slideId) {
  return oauth.get('?slideId=' + slideId);
}

function updateScript(updateScriptReq) {
  return oauth.put('/script', updateScriptReq);
}

export { getSlide, updateScript };
