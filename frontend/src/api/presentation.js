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

function getPresentations(userId) {
  return instance.get('list/' + userId);
}

function addPresentation(userData) {
  return instance.post('', userData);
}

function modifyPresentationName(presentationId, userData) {
  return instance.patch(presentationId, userData);
}

function deletePresentation(presentationId) {
  return instance.delete(presentationId);
}

function getPresentationDetail(presentationId) {
  return instance.get(presentationId);
}

function presentationAddDelete(presentationId, data) {
  return instance.put(presentationId, data);
}

function savePresentation(data) {
  let presentationId = data.slides[0].slideId;
  return instance.put(presentationId, data);
}

export {
  getPresentations,
  addPresentation,
  modifyPresentationName,
  deletePresentation,
  getPresentationDetail,
  presentationAddDelete,
  savePresentation,
};
