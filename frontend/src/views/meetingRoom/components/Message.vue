<template>
  <div>
    <li v-if="message.name === myName" class="clearfix">
      <div align="right" class="message-data text-right">
        <span class="message-data-time"
          >{{ message.time }}, <b>{{ realName }}</b></span
        >
      </div>
      <div class="message other-message float-right">
        {{ message.chatContent }}
      </div>
    </li>
    <li v-else class="clearfix">
      <div class="message-data">
        <span class="message-data-time"
          >{{ message.time }}, <b>{{ realName }}</b></span
        >
      </div>
      <div class="message my-message">{{ message.chatContent }}</div>
    </li>
  </div>
</template>

<script>
export default {
  name: 'Message',
  props: {
    message: Object,
  },

  data() {
    return {};
  },
  computed: {
    myName() {
      return this.$store.getters['meetingRoom/getMyName'];
    },
    realName() {
      return this.message.name.split('-')[0];
    },
  },

  mounted() {},

  methods: {},
};
</script>

<style lang="scss" scoped>
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
  background: #dbc194;
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
  border-bottom-color: #dbc194;
  border-width: 10px;
  margin-left: -10px;
}

.chat .chat-history .other-message {
  background: #d8da90;
  text-align: right;
}

.chat .chat-history .other-message:after {
  border-bottom-color: #d8da90;
  left: 65%;
}

.chat .chat-message {
  padding: 20px;
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
</style>
