import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
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
  },
  withCredentials: true,
});
function getPresentations(userId) {
  return instance.get('/' + userId);
}

function addPresentation(userData) {
  return file.post('/', userData);
}

function addPptpdf(userData) {
  return file.post('/pptpdf', userData);
}

function modifyPresentationName(presentationId, userData) {
  return instance.put('/' + presentationId, userData);
}

function deletePresentation(presentationId) {
  return instance.delete(presentationId);
}

function getPresentationDetail(userId, presentationId) {
  return instance.get('/' + userId + '/' + presentationId);
}

// function presentationAddDelete(presentationId, data) {
//   return instance.put('/' + presentationId, data);
//   // return instance.patch(`/${presentationId}`, data);
// }

function savePresentation(presentationId, data) {
  return instance.put('/' + presentationId, data);
}

function addSlide(data) {
  return instance.patch('/slide', data);
}

function deleteSlide(slideId) {
  return instance.delete(`/slide/${slideId}`);
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
