import Participant from './js/participant.js';
import Vue from 'vue';
import router from '../../router';
import { setRoomOnLive } from '@/api/rooms.js';
import { BASE_URL } from '@/constant/index.js';
import kurentoUtils from 'kurento-utils';
import axios from 'axios';
import _ from 'lodash';

const API_SERVER_URL = BASE_URL;

export default {
  namespaced: true,
  // state
  state: () => ({
    ws: null,
    // meetingRoom 에서 그룹콜 중일때만 쓰는 state
    roomName: null,
    roomNumber: null,
    participants: null,
    myName: null,
    myId: null,
    chat: null,
    messageList: null,
    /* nowImageUrl: null, */
    currentPage: null,
    manager: null,
    presenter: null,
    size: null,
    location: null,
    // 발표자에게 필요한 state
    presentationContents: null, // 최초 API 요청으로 콘텐츠들의 size, user_id 받아옴
    /* imageUrls: null, */
    imageSrcs: null,
    scriptList: null,
    effectList: null,
    selectedContentId: null,
    transition: null,
    // 초기 카메라, 마이크 세팅
    startWithMic: null,
    startWithVideo: null,
    prev: null,
    now: null,
    next: null,
    imgLength: null,
    // 단축키 설정을 위한 변수
    // idx
    plus: 0,
    minus: 0,
    // size
    size0: 0,
    size1: 0,
    size2: 0,
    size3: 0,
    size4: 0,
    // location
    left: 0,
    top: 0,
    right: 0,
    // 대본 끄고 킬 때
    showScript: true,
  }),
  // mutations
  mutations: {
    WS_INIT(state, url) {
      state.ws = new WebSocket(url);
      return false;
    },
    /*     WS_ONMESSAGE(state, parsedMessage) {
      state.serverMessage = parsedMessage;
    }, */
    SET_MEETING_INFO(state, meetingInfo) {
      state.myName = meetingInfo.myName;
      state.myId = meetingInfo.myId;
      state.roomName = meetingInfo.roomName;
      state.manager = meetingInfo.manager;
      state.roomNumber = _.split(meetingInfo.roomName, '-')[1];
      state.startWithMic = meetingInfo.startWithMic;
      state.startWithVideo = meetingInfo.startWithVideo;
    },
    ADD_PARTICIPANT(state, { name, participant }) {
      if (state.participants === null) {
        state.participants = {};
      }
      // 객체 변경 감지를 위한 추가법
      Vue.set(state.participants, name, participant);
      //state.participants[name] = participant
      // 디버깅
      /* console.log('participant added', state.participants); */
      // 임시코드 종료
    },
    DISPOSE_PARTICIPANT(state, participantName) {
      // 객체 변경 감지를 위한 삭제법
      Vue.delete(state.participants, participantName);
      /* delete state.participants[participantName]; */
    },
    // 커스텀 웹소켓 메시지
    CHANGE_PRESENTATION(state, message) {
      // 디버깅 콘솔
      /* console.log('CHANGE_PRESENTATION', message); */
      /* state.nowImageUrl = message.imageUri; */
      state.currentPage = message.currentPage;
      state.size = message.size;
      state.location = message.location;
      state.transition = message.transition;
    },
    // 발표자 변경, 발표자료 null 로 설정
    CHANGE_PRESENTER(state, message) {
      /* console.log('CHANGE_PRESENTER', message); */
      state.presenter = message.presenter;
      /* state.nowImageUrl = null; */
      state.currentPage = null;
      state.size = null;
      state.location = null;
      state.selectedContentId = null;
      state.transition = null;
    },
    CHANGE_CONTENT(state, message) {
      state.selectedContentId = message.presentationUserId;
    },
    /* leave room */
    LEAVE_ROOM(state) {
      state.ws = null;
      state.roomName = null;
      state.roomNumber = null;
      state.participants = null;
      state.myName = null;
      state.chat = null;
      state.messageList = null;
      /* state.nowImageUrl = null; */
      state.currentPage = null;
      state.manager = null;
      state.presenter = null;
      state.size = null;
      state.location = null;
      state.presentationContents = null;
      /* state.imageUrls = null; */
      state.imageSrcs = null;
      state.selectedContentId = null;
      state.transition = null;
    },
    SET_CONTENTS(state, message) {
      state.presentationContents = message.data;
    },
    SET_IMAGE_SRCS(state, imageSrcs) {
      state.imageSrcs = imageSrcs;
      state.currentPage = 0;
      state.location = state.location === null ? 'right' : state.location;
      state.size = state.size === null ? '2' : state.size;
      state.transition =
        state.transition === null ? 'default' : state.transition;
    },
    SET_SCRIPTS(state, scriptList) {
      state.scriptList = scriptList;
    },
    SET_EFFECTS(state, effectList) {
      state.effectList = effectList;
    },
    SET_ONGOING_PRESENTATION(state, { message, imageSrcs }) {
      state.imageSrcs = imageSrcs;
      state.currentPage = message.currentPage;
      state.location = message.location;
      state.size = message.size;
      state.transition = message.transition;
    },
    /* SET_IMAGE_URLS(state, imageUrls) {
      state.imageUrls = imageUrls;
      console.log(state.imageUrls);
    }, */
    /* SET_SELECTED_CONTENT_ID(state, id) {
      state.selectedContentId = id;
    }, */
    ADD_CHAT(state, { time, name, chatContent }) {
      state.chat = { time, name, chatContent };
    },
    ADD_CHAT_MESSAGE(state, chatMessage) {
      if (state.messageList === null) {
        state.messageList = [];
      }

      state.messageList.push(chatMessage);
    },
    GO_PREV(state) {
      state.prev -= 1;
      state.now -= 1;
      state.next -= 1;
      state.currentPage = state.now;
    },
    GO_NEXT(state) {
      state.next += 1;
      state.now += 1;
      state.prev += 1;
      state.currentPage = state.now;
    },
    SET_LENGTH(state, imglength) {
      console.log(imglength);
      state.imgLength = imglength;
      console.log(state.imgLength);
    },
    PLUS_IDX(state) {
      if (state.plus == 1) {
        state.plus = 0;
      } else {
        state.plus = 1;
      }
    },
    MINUS_IDX(state) {
      if (state.minus == 1) {
        state.minus = 0;
      } else {
        state.minus = 1;
      }
    },
    SIZE0_SIZE(state) {
      if (state.size0 == 1) {
        state.size0 = 0;
      } else {
        state.size0 = 1;
      }
    },
    SIZE1_SIZE(state) {
      if (state.size1 == 1) {
        state.size1 = 0;
      } else {
        state.size1 = 1;
      }
    },
    SIZE2_SIZE(state) {
      if (state.size2 == 1) {
        state.size2 = 0;
      } else {
        state.size2 = 1;
      }
    },
    SIZE3_SIZE(state) {
      if (state.size3 == 1) {
        state.size3 = 0;
      } else {
        state.size3 = 1;
      }
    },
    SIZE4_SIZE(state) {
      if (state.size4 == 1) {
        state.size4 = 0;
      } else {
        state.size4 = 1;
      }
    },
    LEFT_LOCATION(state) {
      if (state.left == 1) {
        state.left = 0;
      } else {
        state.left = 1;
      }
    },
    TOP_LOCATION(state) {
      if (state.top == 1) {
        state.top = 0;
      } else {
        state.top = 1;
      }
    },
    RIGHT_LOCATION(state) {
      if (state.right == 1) {
        state.right = 0;
      } else {
        state.right = 1;
      }
    },
    SHOW_SCRIPT(state) {
      state.showScript = !state.showScript;
    },
  },
  // actions
  actions: {
    // meetingRoom view 생성시 웹소켓 생성 action
    wsInit(context, url) {
      context.commit('WS_INIT', url);
      context.state.ws.onmessage = function (message) {
        let parsedMessage = JSON.parse(message.data);
        // console.info('Received message: ' + message.data)
        /* context.commit('WS_ONMESSAGE', parsedMessage); */
        /* console.log(parsedMessage); */
        context.dispatch('onServerMessage', parsedMessage);
        return false;
      };
      return false;
    },
    // 웹소켓 메시지에 따른 동작
    onServerMessage(context, message) {
      switch (message.id) {
        case 'existingParticipants': {
          context.dispatch('onExistingParticipants', message);
          break;
        }
        case 'newParticipantArrived': {
          context.dispatch('onNewParticipant', message);
          break;
        }
        case 'participantLeft': {
          context.dispatch('onParticipantLeft', message);
          break;
        }
        case 'receiveVideoAnswer': {
          context.dispatch('receiveVideoResponse', message);
          break;
        }
        // 커스텀 웹소켓 메시지 시작
        // 발표자료 변경 정보 수신 시
        case 'changePresentation': {
          context.dispatch('changePresentation', message);
          break;
        }
        // 발표자 변경 정보 수신 시
        case 'changePresenter': {
          context.dispatch('changePresenter', message);
          break;
        }
        // 발표자료 묶음 (Content) 변경 수신 시
        case 'changeContent': {
          context.dispatch('changeContent', message);
          break;
        }
        case 'iceCandidate': {
          context.state.participants[message.name].rtcPeer.addIceCandidate(
            message.candidate,
            function (error) {
              if (error) {
                console.error('Error adding candidate: ' + error);
                return;
              }
            },
          );
          break;
        }
        case 'addChat': {
          context.dispatch('addChat', message);
          break;
        }
        default: {
          console.error('Unrecognized message' + message);
        }
      }
    },
    // 웹소켓으로 메시지 발신 action
    sendMessage(context, message) {
      var jsonMessage = JSON.stringify(message);
      /* console.log('Sending message: ' + jsonMessage); */
      context.state.ws.send(jsonMessage);
    },
    // user 이름 설정 action
    setMeetingInfo(context, meetingInfo) {
      context.commit('SET_MEETING_INFO', meetingInfo);
    },
    // onExistingParticipants 메시지에 대한 반응
    onExistingParticipants(context, message) {
      // console.log('onExistingParticipants start')
      // 브라우저 사용자의 Participant 오브젝트 정의
      let participant = new Participant(context.state.myName);
      var video = participant.getVideoElement();

      // WebRtcPeer의 옵션 설정
      // var constraints = {
      //   audio: true,
      //   video: {
      //     width: 320,
      //     height: 240,
      //     framerate: 15,
      //   },
      // };

      var options = {
        localVideo: video,
        // mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant),
      };

      // user participant 오브젝트의 WebRtcPeer 선언
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
        options,
        function (error) {
          if (error) {
            //debugging
            /* console.log(participant, video); */
            return console.error(error);
          }
          // this -> kurentoUtils.WebRtcPeer.WebRtcPeerSendonly
          // generateOffer:
          this.generateOffer(participant.offerToReceiveVideo.bind(participant));
          this.audioEnabled = context.state.startWithMic;
          this.videoEnabled = context.state.startWithVideo;
        },
      );

      // state에 user participant 오브젝트 추가
      const myName = context.state.myName;
      context.commit('ADD_PARTICIPANT', { name: myName, participant });

      // state에 방에 있던 participant들 오브젝트 추가
      message.data.forEach(function (sender) {
        /* console.log('forEach문 sender: ' + sender); */
        context.dispatch('receiveVideo', sender);
      });
      // presenter 설정, presentation 설정
      context.dispatch('changePresenter', message);
      context.dispatch('setOngoingPresentation', message);
      // console.log('onExistingParticipants end')
      // console.log('MeetingRoom', context.state.roomNumber);
      router.push({
        name: 'MeetingRoom',
        params: {
          roomNumber: context.state.roomNumber,
        },
      });
    },
    // 다른 참가자 participant 비디오 받기
    receiveVideo(context, sender) {
      let participant = new Participant(sender);
      var video = participant.getVideoElement();
      var options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant),
      };
      // 참가자의 rtcPeer 설정
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options,
        function (error) {
          if (error) {
            return console.error(error);
          }
          // this -> kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly
          this.generateOffer(participant.offerToReceiveVideo.bind(participant));
        },
      );
      // 참가자를 state의 참가자 목록에 추가
      context.commit('ADD_PARTICIPANT', { name: sender, participant });
    },
    // 방에 참여해있는 상태에서 새로운 참가자가 들어왔을 때
    onNewParticipant(context, request) {
      /* console.log('onNewParticipant' + request.name); */
      context.dispatch('receiveVideo', request.name);
    },
    // participant 객체에서 삭제 메서드를 사용했을 때
    onParticipantLeft(context, request) {
      /* console.log('Participant' + request.name + 'left'); */
      var participant = context.state.participants[request.name];
      participant.dispose();
    },
    // participant.dispose에서 오는 요청
    disposeParticipant(context, participantName) {
      context.commit('DISPOSE_PARTICIPANT', participantName);
    },
    leaveRoom(context) {
      window.location.reload(); // 새로고침
      router.push({ path: '/dashboard' });
      // console.log(context.state.roomNumber);
      if (context.state.myName === context.state.manager) {
        const roomData = {
          room_id: context.state.roomNumber,
          on_live: false,
        };
        setRoomOnLive(roomData);
      }
      context.commit('LEAVE_ROOM');
    },
    receiveVideoResponse(context, result) {
      context.state.participants[result.name].rtcPeer.processAnswer(
        result.sdpAnswer,
        function (error) {
          if (error) {
            return console.error(error);
          }
        },
      );
    },
    // 발표자가 바뀔때 자기 자신이라면 contents를 업데이트
    setContents(context) {
      axios({
        method: 'get',
        url: `${API_SERVER_URL}/presentation/${context.state.myId}`,
      })
        .then(res => {
          context.commit('SET_CONTENTS', res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 커스텀 웹소켓 메시지 by 동우님
    changePresentation(context, message) {
      context.commit('CHANGE_PRESENTATION', message);
    },
    changePresenter(context, message) {
      context.commit('CHANGE_PRESENTER', message);
      // 발표자가 바뀔때 자기 자신이라면 미리 contents를 업데이트
      if (context.state.myName === message.presenter) {
        context.dispatch('setContents');
      }
    },
    changeContent(context, message) {
      axios({
        method: 'get',
        url: `${API_SERVER_URL}/presentation/${context.state.myId}/${message.presentationUserId}`,
      }).then(res => {
        const imageSrcs = [];
        const scriptList = [];
        const effectList = [];
        res.data.slideList.forEach(data => {
          let imageSrc = 'data:image/jpeg;base64,' + data.slideFile;
          imageSrcs.push(imageSrc);
          scriptList.push(data.script);
          effectList.push(data.effect);
        });
        context.commit('SET_IMAGE_SRCS', imageSrcs);
        context.commit('SET_SCRIPTS', scriptList);
        console.log(res.data.slideList.length);
        //SET_LENGTH 지우거나 쓰시는분?
        context.commit('SET_LENGTH', res.data.slideList.length);
        context.commit('SET_EFFECTS', effectList);
      });
      context.commit('CHANGE_CONTENT', message);
    },
    // ContentSelector에서 컨텐츠 선택 시 action
    /* setImageUrls(context, imageUrls) {
        context.commit('SET_IMAGE_URLS', imageUrls);
      }, */
    /* setSelectedContentId(context, id) {
        context.commit('SET_SELECTED_CONTENT_ID', id);
      }, */
    setOngoingPresentation(context, message) {
      if (message.presentationUserId !== null) {
        axios({
          method: 'get',
          url: `${API_SERVER_URL}/presentation/${context.state.myId}/${message.presentationUserId}`,
        }).then(res => {
          const imageSrcs = [];

          res.data.forEach(data => {
            let imageSrc = 'data:image/jpeg;base64,' + data.slideFile;
            imageSrcs.push(imageSrc);
          });
          context.commit('SET_ONGOING_PRESENTATION', { message, imageSrcs });
        });
      }
    },
    addChat(context, message) {
      context.commit('ADD_CHAT', message);
    },
    addChatMessage(context, chatMessage) {
      context.commit('ADD_CHAT_MESSAGE', chatMessage);
    },
    goPrev(context) {
      if (context.state.now > 0) {
        context.commit('GO_PREV');
      }
    },
    goNext(context) {
      if (context.state.now < context.state.imgLength - 1) {
        context.commit('GO_NEXT');
      }
    },
    // 단축키 설정
    // idx
    plusIdx(context) {
      context.commit('PLUS_IDX');
    },
    minusIdx(context) {
      context.commit('MINUS_IDX');
    },
    // size
    size0Size(context) {
      context.commit('SIZE0_SIZE');
    },
    size1Size(context) {
      context.commit('SIZE1_SIZE');
    },
    size2Size(context) {
      context.commit('SIZE2_SIZE');
    },
    size3Size(context) {
      context.commit('SIZE3_SIZE');
    },
    size4Size(context) {
      context.commit('SIZE4_SIZE');
    },
    // location
    leftLocation(context) {
      context.commit('LEFT_LOCATION');
    },
    topLocation(context) {
      context.commit('TOP_LOCATION');
    },
    rightLocation(context) {
      context.commit('RIGHT_LOCATION');
    },
    // script 설정
    showScript(context) {
      context.commit('SHOW_SCRIPT');
    },
  },
  getters: {
    getChat(state) {
      return state.chat;
    },
    getMessageList(state) {
      return state.messageList;
    },
    getMyName(state) {
      return state.myName;
    },
    getPrev(state) {
      return state.prev;
    },
    getNext(state) {
      return state.next;
    },
    getNow(state) {
      return state.now;
    },
  },
};
