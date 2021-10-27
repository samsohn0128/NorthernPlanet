<template>
  <div>
    <AppNav />
    <div>
      <router-view></router-view>
    </div>
    <Editor />
    <AppFooter />
  </div>
</template>
<script>
import AppNav from '@/components/common/AppNav.vue';
import AppFooter from '@/components/common/AppFooter.vue';
import '@toast-ui/editor/dist/toastui-editor.css';

import { Editor } from '@toast-ui/vue-editor';

export default {
  name: 'Script',
  components: { AppNav, AppFooter, Editor },
  data() {
    return {
      myValue: '',
    };
  },
};
</script>
