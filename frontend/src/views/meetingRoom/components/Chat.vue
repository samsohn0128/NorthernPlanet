<template>
  <div>
    <link
      href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
      rel="stylesheet"
    />

    <div class="container">
      <div id="make-chatting-bottom" class="chat-room-setting row clearfix">
        <div class="col-lg-12">
          <div class="chat">
            <div class="chat-history">
              <ul class="m-b-0">
                <Message
                  v-for="(message, index) in messageList"
                  :message="message"
                  :key="index"
                />
              </ul>
            </div>
          </div>
          <div class="chat-message clearfix chat-input">
            <div class="input-group mb-0">
              <input
                type="text"
                class="form-control chat-input-div"
                placeholder="Enter text here..."
                v-model="chatInput"
                @keyup.enter="addChat"
              />
              <div class="input-group-prepend" @click="addChat">
                <span class="input-group-text send-icon"
                  ><i class="fa fa-send"></i
                ></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Message from './Message.vue';

export default {
  name: 'Chat',
  components: { Message },
  props: {
    messageList: Array,
  },
  data() {
    return {
      chatInput: '',
      date: null,
      nowTime: '',
    };
  },
  watch: {
    messageList() {
      let currentChat = document.getElementById('make-chatting-bottom');
      // console.log('요기', currentChat.scrollTop, currentChat.scrollHeight);
      currentChat.scrollTop = currentChat.scrollHeight;
    },
  },
  computed: {
    myName() {
      return this.$store.getters['meetingRoom/getMyName'];
    },
  },

  methods: {
    addChat: function () {
      if (this.chatInput === '') return;
      // 현재 채팅을 전송한 시간
      this.date = new Date();
      let ampm = this.date.getHours() >= 12 ? 'PM' : 'AM';
      let hours = this.date.getHours() % 12;
      hours = hours ? hours : 12;
      let minutes = this.date.getMinutes();
      if (minutes >= 0 && minutes <= 9) minutes = '0'.concat(minutes);
      this.nowTime = hours + ':' + minutes + ' ' + ampm;

      const message = {
        id: 'addChat',
        time: this.nowTime,
        name: this.myName,
        chatContent: this.chatInput,
      };

      this.chatInput = '';

      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
  },
};
</script>

<style scoped>
body {
  background-color: #f4f7f6;
  margin-top: 20px;
}
.chat-room-setting {
  overflow: auto;
  height: calc(100vh - 160px);
}

.chat-app .people-list {
  width: 280px;
  position: absolute;
  left: 0;
  top: 0;
  padding: 20px;
  z-index: 7;
}

.chat-app .chat {
  margin-left: 280px;
  border-left: 1px solid #eaeaea;
}

.people-list {
  -moz-transition: 0.5s;
  -o-transition: 0.5s;
  -webkit-transition: 0.5s;
  transition: 0.5s;
}

.people-list .chat-list li {
  padding: 10px 15px;
  list-style: none;
  border-radius: 3px;
}

.people-list .chat-list li:hover {
  background: #efefef;
  cursor: pointer;
}

.people-list .chat-list li.active {
  background: #efefef;
}

.people-list .chat-list li .name {
  font-size: 15px;
}

.people-list .chat-list img {
  width: 45px;
  border-radius: 50%;
}

.people-list img {
  float: left;
  border-radius: 50%;
}

.people-list .about {
  float: left;
  padding-left: 8px;
}

.people-list .status {
  color: #999;
  font-size: 13px;
}

.chat .chat-header {
  padding: 15px 20px;
  border-bottom: 2px solid #f4f7f6;
}

.chat .chat-header img {
  float: left;
  border-radius: 40px;
  width: 40px;
}

.chat .chat-header .chat-about {
  float: left;
  padding-left: 10px;
}

.chat .chat-history ul {
  padding: 0;
}

.chat .chat-history ul li {
  list-style: none;
  margin-bottom: 30px;
}

.chat .chat-history ul li:last-child {
  margin-bottom: 0px;
}

.chat .chat-history .message-data {
  margin-bottom: 15px;
}

.chat .chat-history .message-data img {
  border-radius: 40px;
  width: 40px;
}

.chat .chat-history .message-data-time {
  color: #434651;
  padding-left: 6px;
}

.chat .chat-history .message {
  color: #444;
  padding: 18px 20px;
  line-height: 26px;
  font-size: 16px;
  border-radius: 7px;
  display: inline-block;
  position: relative;
}

.chat .chat-history .message:after {
  bottom: 100%;
  left: 7%;
  border: solid transparent;
  content: ' ';
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
  border-bottom-color: #fff;
  border-width: 10px;
  margin-left: -10px;
}

.chat .chat-history .my-message {
  background: white;
}

.chat .chat-history .my-message:after {
  bottom: 100%;
  left: 30px;
  border: solid transparent;
  content: ' ';
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
  border-bottom-color: white;
  border-width: 10px;
  margin-left: -10px;
}

.chat .chat-history .other-message {
  background: lightgoldenrodyellow;
  text-align: right;
}

.chat .chat-history .other-message:after {
  border-bottom-color: lightgoldenrodyellow;
  left: 93%;
}

.chat .chat-message {
  padding: 5px;
}

.float-right {
  float: right;
}

.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0;
  content: ' ';
  clear: both;
  height: 0;
}

.chat-input {
  position: fixed;
  bottom: 0;
  border-top: 2px solid #fff;
  width: 300px;
}

.chat-input-div {
  width: 80%;
  border: 1.5px solid #6d9e8a;
  border-radius: 8px;
  margin-bottom: 10px;
}

.send-icon {
  display: inline-block;
  margin-left: 1px;
  background: #6d9e8a;
  color: white;
}

.chat-input-div:focus {
  border: 0px solid;
}

@media only screen and (max-width: 767px) {
  .chat-app .people-list {
    height: 465px;
    width: 100%;
    overflow-x: auto;
    background: #fff;
    left: -400px;
    display: none;
  }
  .chat-app .people-list.open {
    left: 0;
  }
  .chat-app .chat {
    margin: 0;
  }
  .chat-app .chat .chat-header {
    border-radius: 0.55rem 0.55rem 0 0;
  }
  .chat-app .chat-history {
    height: 300px;
    overflow-x: auto;
  }
}

@media only screen and (min-width: 768px) and (max-width: 992px) {
  .chat-app .chat-list {
    height: 650px;
    overflow-x: auto;
  }
  .chat-app .chat-history {
    height: 600px;
    overflow-x: auto;
  }
}

@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: landscape) and (-webkit-min-device-pixel-ratio: 1) {
  .chat-app .chat-list {
    height: 480px;
    overflow-x: auto;
  }
  .chat-app .chat-history {
    height: calc(100vh - 350px);
    overflow-x: auto;
  }
}

/* scroll bar*/
/* 스크롤바 설정*/
::-webkit-scrollbar {
  width: 5px;
}

/* 스크롤바 막대 설정*/
::-webkit-scrollbar-thumb {
  height: 5px;
  background-color: #6d9e8a;
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0.33);
}
</style>
