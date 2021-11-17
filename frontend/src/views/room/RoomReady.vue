<template>
  <div>
    <section>
      <div class="container py-4">
        <div class="row">
          <div
            class="
              col-xl-7 col-lg-10
              mx-auto
              d-flex
              justify-content-center
              flex-column
              px-10
            "
          >
            <div class="header">
              <h2 class="title text-center">
                {{ this.roomId }} - {{ roomName }} Room Ready
              </h2>
            </div>
            <div class="body">
              <form>
                <div class="form-group">
                  <label for="message-text" class="col-form-label"
                    >Room Description:
                    <p>{{ roomDescription }}</p></label
                  >
                </div>
                <div class="form-group mb-4">
                  <label><h6>My Name</h6></label>
                  <textarea
                    v-model="userName"
                    type="text"
                    class="form-control"
                    rows="1"
                    placeholder="이름을 적어주세요."
                  ></textarea>
                </div>
              </form>
              <!-- local video element -->
              <video
                width="100%"
                :id="'local-video' + roomId"
                autoplay="true"
                poster="@/assets/img/logos/focus_camera3.jpg"
              ></video>
              <!-- local video element -->
              <i class="bi bi-mic-fill"></i>
              <button
                type="button"
                class="btn"
                :class="{
                  'button-color-setting': isMicOn,
                  'btn-outline-dark': !isMicOn,
                }"
                @click="micOnOff"
              >
                <span
                  class="fas"
                  :class="{
                    'fa-microphone': isMicOn,
                    'fa-microphone-slash': !isMicOn,
                  }"
                ></span>
              </button>
              <button
                type="button"
                class="btn"
                :class="{
                  'button-color-setting': isVideoOn,
                  'btn-outline-dark': !isVideoOn,
                }"
                @click="videoOnOff"
              >
                <span
                  class="fas fa-video"
                  :class="{
                    'fa-video': isVideoOn,
                    'fa-video-slash': !isVideoOn,
                  }"
                ></span>
              </button>
              <button
                type="button"
                class="btn button-create-setting"
                style="float: right"
                @click="sendMsgToKurento"
              >
                Join
              </button>
            </div>
            <div class="footer"></div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import { getRoom } from '@/api/rooms.js';
import store from '@/store';
export default {
  name: 'RoomReady',
  components: {},
  computed: {
    roomId() {
      return this.$route.params.room_id;
    },
  },
  data() {
    return {
      roomInfo: null,
      roomName: null,
      manager: null,
      userName: store.state.users.user.name
        ? store.state.users.user.name
        : null,
      userId: Math.random(),
      roomDescription: null,
      isMicOn: false,
      isVideoOn: false,
    };
  },
  async created() {
    try {
      //websocket init
      const url = 'wss://' + location.host + '/groupcall';
      console.log(url);
      this.$store.dispatch('meetingRoom/wsInit', url);

      //roomId로 roomInfo 받아와서 data setting 하기
      this.roomInfo = await getRoom(this.roomId);
      this.roomName = this.roomInfo.data.name;
      this.manager =
        this.roomInfo.data.managerName + '-' + this.roomInfo.data.managerId;
      this.roomDescription = this.roomInfo.data.description;
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    micOnOff: function () {
      if (this.isMicOn) {
        this.isMicOn = false;
      } else {
        this.isMicOn = true;
      }
    },
    videoOnOff: function () {
      if (this.isVideoOn) {
        this.isVideoOn = false;
        this.stopVideoFromCamera();
      } else {
        this.isVideoOn = true;
        this.playVideoFromCamera();
      }
    },
    sendMsgToKurento() {
      if (!this.userName) {
        this.$toastError('이름을 입력해주세요!');
        return;
      } else if (!this.$nameValidate(this.userName)) {
        this.$toastError('특수문자 - 는 사용할 수 없습니다.');
        return;
      }
      const myNameId = this.userName + '-' + this.userId;
      const roomNameId = this.roomName + '-' + this.roomId;
      const message = {
        id: 'joinRoom',
        name: myNameId,
        room: roomNameId,
      };
      const meetingInfo = {
        myName: myNameId,
        myId: this.userId,
        roomName: roomNameId,
        manager: this.manager,
        startWithMic: this.isMicOn,
        startWithVideo: this.isVideoOn,
      };
      console.log('message: ', message);
      console.log('meetingInfo: ', meetingInfo);
      this.$store.dispatch('meetingRoom/setMeetingInfo', meetingInfo);
      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
    playVideoFromCamera: async function () {
      try {
        const constraints = { video: true, audio: false };
        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        const videoElement = document.getElementById(
          'local-video' + this.roomId,
        );
        videoElement.srcObject = stream;
      } catch (error) {
        console.error('Error opening video camera.', error);
      }
    },
    stopVideoFromCamera: async function () {
      try {
        const videoElement = document.getElementById(
          'local-video' + this.roomId,
        );
        var stream = videoElement.srcObject;
        var tracks = stream.getTracks();

        for (var i = 0; i < tracks.length; i++) {
          var track = tracks[i];
          track.stop();
        }
        videoElement.srcObject = null;
      } catch (err) {
        console.error('Error stop video camera.', err);
      }
    },
  },
};
</script>
<style scoped>
.btn {
  margin: 4px;
}
.button-create-setting {
  background: linear-gradient(90deg, #77dfde, #dbd64a);
  color: white;
}
.button-color-setting {
  background: #66806a;
  color: white;
}
</style>
