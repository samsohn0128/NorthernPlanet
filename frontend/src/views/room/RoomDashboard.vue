<template>
  <div>
    <RoomGroup v-bind:title="now" />
    <RoomGroup v-bind:title="future" />
    <RoomGroup v-bind:title="history" />
  </div>
</template>

<script>
import RoomGroup from '@/views/room/components/RoomGroup';
import store from '@/store';

export default {
  name: 'RoomDashboard',

  components: { RoomGroup },
  data() {
    return {
      now: 'Now',
      future: 'Future',
      history: 'History',
    };
  },
  computed: {
    user() {
      return store.getters['users/getUser'];
    },
  },
  created() {
    store.dispatch('rooms/fetchRooms', this.user.userId);

    const url = 'wss://' + location.host + '/groupcall';
    store.dispatch('meetingRoom/wsInit', url);
  }, //axios
};
</script>
