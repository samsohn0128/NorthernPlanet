import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
import store from '@/store';
const instance = axios.create({
  baseURL: BASE_URL + '/presentation',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

const file = axios.create({
  baseURL: BASE_URL + '/presentation',
  headers: {
    'Content-type': 'multipart/form-data',
    Authorization: `Bearer ${store.getters['users/getToken']}`,
  },
  withCredentials: true,
});

const oauth = axios.create({
  baseURL: `${BASE_URL}/presentation`,
  headers: {
    'Content-type': 'application/json',
    Authorization: `Bearer ${store.getters['users/getToken']}`,
  },
  withCredentials: true,
});

function getPresentations(userId) {
  return instance.get('/' + userId);
}

function addPresentation(presentationData) {
  return file.post('/', presentationData);
}

function addPptpdf(pptpdfData) {
  return file.post('/pptpdf', pptpdfData);
}

function modifyPresentationName(presentationId, presentationName) {
  return oauth.put('/' + presentationId, presentationName);
}

function deletePresentation(presentationId) {
  return oauth.delete('/' + presentationId);
}

function getPresentationDetail(userId, presentationId) {
  return instance.get('/' + userId + '/' + presentationId);
}

// function presentationAddDelete(presentationId, data) {
//   return instance.put('/' + presentationId, data);
//   // return instance.patch(`/${presentationId}`, data);
// }

function savePresentation(data) {
  return oauth.put('/', data);
}

function addSlide(data) {
  return file.post('/slide', data);
}

function deleteSlide(slideId) {
  return oauth.delete(`/slide/${slideId}`);
}

export {
  getPresentations,
  addPresentation,
  addPptpdf,
  modifyPresentationName,
  deletePresentation,
  getPresentationDetail,
  // presentationAddDelete,
  savePresentation,
  addSlide,
  deleteSlide,
};
