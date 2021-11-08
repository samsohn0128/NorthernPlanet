<template>
  <div>
    <link
      href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
      rel="stylesheet"
    />

    <div class="container">
      <div class="row clearfix">
        <div class="col-lg-12">
          <div class="chat">
            <div class="chat-history">
              <ul class="m-b-0">
                <Message
                  v-for="(message, index) in messageList"
                  :message="message"
                  :key="index"
                />
                <!-- <li class="clearfix">
                  <div align="right" class="message-data text-right">
                    <span class="message-data-time">10:10 AM, Today</span>
                  </div>
                  <div class="message other-message float-right">
                    Hi Aiden, how are you? How is the project coming along?
                  </div>
                </li>
                <li class="clearfix">
                  <div class="message-data">
                    <span class="message-data-time">10:12 AM, minji</span>
                  </div>
                  <div class="message my-message">Are we meeting today?</div>
                </li>
                <li class="clearfix">
                  <div class="message-data">
                    <span class="message-data-time">10:15 AM, youngeun</span>
                  </div>
                  <div class="message my-message">
                    Project has been already finished and I have results to show
                    you.
                  </div>
                </li>
                <li class="clearfix">
                  <div class="message-data">
                    <span class="message-data-time">10:15 AM, youngeun</span>
                  </div>
                  <div class="message my-message">
                    Project has been already finished and I have results to show
                    you.
                  </div>
                </li> -->
              </ul>
            </div>
            <div class="chat-message clearfix">
              <div class="input-group mb-0">
                <input
                  type="text"
                  class="form-control"
                  placeholder="Enter text here..."
                  v-model="chatInput"
                  @keyup.enter="addChat"
                />
                <div class="input-group-prepend" @click="addChat">
                  <span class="input-group-text"
                    ><i class="fa fa-send"></i
                  ></span>
                </div>
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
  data() {
    return {
      chatInput: '',
      date: null,
      nowTime: '',
    };
  },
  computed: {
    user() {
      return this.$store.getters['users/getUser'];
    },
    chat() {
      return this.$store.getters['meetingRoom/getChat'];
    },
    messageList() {
      return this.$store.getters['meetingRoom/getMessageList'];
    },
  },
  watch: {
    chat(value) {
      console.log('watch chat', value);

      this.$store.dispatch('meetingRoom/addChatMessage', value);
    },
    messageList(value) {
      console.log('watch messageList', value);
    },
  },
  methods: {
    addChat: function () {
      // 현재 채팅을 전송한 시간
      this.date = new Date();
      let ampm = this.date.getHours() >= 12 ? 'PM' : 'AM';
      let hours = this.date.getHours() % 12;
      hours = hours ? hours : 12;
      this.nowTime = hours + ':' + this.date.getMinutes() + ' ' + ampm;

      const message = {
        id: 'addChat',
        time: this.nowTime,
        name: this.user.name,
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
/* .card {
  background: #fff;
  transition: 0.5s;
  border: 0;
  margin-bottom: 30px;
  border-radius: 0.55rem;
  position: relative;
  width: 100%;
  box-shadow: 0 1px 2px 0 rgb(0 0 0 / 10%);
} */
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

.chat .chat-history {
  padding: 20px;
  border-bottom: 2px solid #fff;
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
  padding: 20px;
}

/* .online,
.offline,
.me {
  margin-right: 2px;
  font-size: 8px;
  vertical-align: middle;
}

.online {
  color: #86c541;
}

.offline {
  color: #e47297;
} */

/* .me {
  color: #1d8ecd;
} */

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
</style>
