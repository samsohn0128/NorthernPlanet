import axios from 'axios';
import { BASE_URL } from '@/constant/index.js';
//import store from '@/store';
const instance = axios.create({
  baseURL: BASE_URL + '/room',
  headers: {
    'Content-type': 'application/json',
  },
  withCredentials: true,
});
// const oauth = axios.create({
//   baseURL: `${BASE_URL}/room`,
//   headers: {
//     'Content-type': 'application/json',
//     Authorization: `Bearer ${store.getters['users/getToken']}`,
//   },
//   withCredentials: true,
// });

function createRoom(roomData) {
  console.log('create room: ', roomData);
  return instance.post('', roomData);
}

function updateRoom(roomData) {
  return instance.put('/' + roomData.roomId, roomData);
}

function deleteRoom(roomId) {
  return instance.delete('/' + roomId);
}

function getRooms(userId) {
  console.log('get rooms- userId: ', userId);
  return instance.get('/list/' + userId);
}

function getRoom(roomId) {
  console.log('get room- roomId: ', roomId);
  return instance.get('/' + roomId);
}

function getRoomIsOnLive(roomId) {
  return instance.get('onlive/' + roomId);
}

function setRoomOnLive(roomData) {
  return instance.put('onlive/', roomData);
}

export {
  createRoom,
  getRooms,
  getRoom,
  updateRoom,
  deleteRoom,
  getRoomIsOnLive,
  setRoomOnLive,
};
