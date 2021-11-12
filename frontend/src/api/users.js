import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
import { GOOGLE_OAUTH_URL } from '@/constant/index.js';
import store from '@/store';

const instance = axios.create({
  baseURL: BASE_URL + '/user',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

const oauth = axios.create({
  baseURL: `${BASE_URL}/user`,
  headers: {
    'Content-type': 'application/json',
    Authorization: `Bearer ${store.getters['users/getToken']}`,
  },
  withCredentials: true,
});

function registerUser(userData) {
  return instance.post('/register', userData);
}

function loginUser(userData) {
  return instance.post('/login', userData);
}

function findUser(email) {
  return oauth.get('/search/' + email);
}

function checkUser(email) {
  return instance.get('/check/' + email);
}
function updateUserName(userId, userData) {
  return oauth.put('/' + userId, userData);
}
function updateUserPwd(userData) {
  return instance.put('/update/password', userData);
}

function deleteUser(userId) {
  return oauth.delete('/' + userId);
}

function googleLoginUser() {
  window.location.href = `${GOOGLE_OAUTH_URL}`;
}
function getUser() {
  return instance.get('/oauth2/login');
}

export {
  registerUser,
  loginUser,
  findUser,
  checkUser,
  updateUserName,
  updateUserPwd,
  deleteUser,
  googleLoginUser,
  getUser,
};
