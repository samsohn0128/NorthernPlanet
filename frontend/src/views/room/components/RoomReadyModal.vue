<template>
  <div
    class="modal fade"
    :id="'RoomReadyModal' + roomInfo.roomId"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalMessageTitle"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered text-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" :id="'RoomReadyModal' + roomInfo.roomId">
            {{ roomName }}
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group">
              <label for="message-text" class="col-form-label"
                >Room Description:
                <p>{{ roomDescription }}</p></label
              >
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
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-outline-dark"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn button-create-setting"
            @click="sendMsgToKurento"
            data-bs-dismiss="modal"
          >
            Join
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import { getRoomIsOnLive, setRoomOnLive } from '@/api/rooms.js';
import { mapGetters } from 'vuex';

export default {
  name: 'RoomReadyModal',
  props: { roomInfo: Object },
  components: {},
  data() {
    return {
      roomName: this.roomInfo.name,
      roomId: this.roomInfo.roomId,
      manager: this.roomInfo.managerName + '-' + this.roomInfo.managerId,
      userName: this.$store.state.users.user.name,
      userId: this.$store.state.users.user.userId,
      roomDescription: this.roomInfo.description,
      isMicOn: false,
      isVideoOn: false,
      srcObject: {},
      isManager: (this.roomInfo.managerId =
        this.$store.state.users.user.userId),
    };
  },
  computed: { ...mapGetters(['room']) },
  mounted() {},
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
    join: function () {
      if (this.isManager) {
        // const roomData = {
        //   room_id: this.roomId,
        //   on_live: true,
        // };
        // setRoomOnLive(roomData)
        //   .then(({ status }) => {
        //     if (status != 200) {
        //       return;
        //     }
        //     this.sendMsgToKurento();
        //   })
        //   .catch(() => {
        //   });
      } else {
        // getRoomIsOnLive(this.roomId)
        //   .then(({ status }) => {
        //     if (status != 200) {
        //       return;
        //     }
        //     this.sendMsgToKurento();
        //   })
        //   .catch(() => {
        //   });
      }
    },

    async sendMsgToKurento() {
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
      console.log('message==================');
      console.log(message);
      console.log('meetingInfo==================');
      console.log(meetingInfo);
      await this.$store.dispatch('meetingRoom/setMeetingInfo', meetingInfo);
      await this.$store.dispatch('meetingRoom/sendMessage', message);
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
.button-color-setting {
  background: #66806a;
  color: white;
}
.button-outline-setting {
  border: 1.5px solid #66806a;
  color: #456357;
}
.button-danger-setting {
  background: #ba635f;
  color: white;
}
.button-create-setting {
  background: linear-gradient(90deg, #77dfde, #dbd64a);
  color: white;
}
.button-setting {
  width: 70px;
  height: 40px;
  padding: 5px;
  margin: 0 10px 0 10px;
}
</style>
