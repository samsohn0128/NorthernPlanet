import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
const instance = axios.create({
  baseURL: BASE_URL + '/board/',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});

function downloadFile(filereq) {
  return instance.post('down', filereq);
}
function showRooms(input) {
  return instance.get('room/' + input);
}
function showfiledetail(ShowFileReq) {
  return instance.post('detail', ShowFileReq);
}
function deletefile(FileReq) {
  return instance.post('deletefile', FileReq);
}
function deletetot(ShowFileReq) {
  return instance.post('delete', ShowFileReq);
}

function updatefile(FileReq) {
  return instance.post('updatefile', FileReq);
}

export {
  downloadFile,
  showRooms,
  deletetot,
  showfiledetail,
  deletefile,
  updatefile,
};
