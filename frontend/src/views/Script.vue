<template>
  <div>
    <AppNav />
    <div>
      <router-view></router-view>
    </div>
    <div>
      <Editor
        ref="toastuiEditor"
        :initialValue="editorText"
        :options="editorOptions"
        height="400px"
        initialEditType="wysiwyg"
        previewStyle="vertical"
        @change="onEditorChange"
      />
    </div>
    <div>
      <Viewer :initialValue="viewerText" height="500px" />
    </div>
    <AppFooter />
  </div>
</template>
<script>
import AppNav from '@/components/common/AppNav.vue';
import AppFooter from '@/components/common/AppFooter.vue';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

import { Editor, Viewer } from '@toast-ui/vue-editor';

export default {
  name: 'Script',
  components: { AppNav, AppFooter, Editor, Viewer },
  data() {
    return {
      editorText: '대본을 작성해주세요.',
      viewerText:
        '<p>대본을 작성해주세요.<strong>볼드테스트입니다.</strong></p>',
      editorOptions: {
        minHeight: '200px',
        hideModeSwitch: true,
        toolbarItems: [
          ['heading', 'bold', 'italic', 'strike'],
          ['hr', 'quote'],
          ['ul', 'ol', 'task', 'indent', 'outdent'],
          ['scrollSync'],
        ],
      },
    };
  },
  methods: {
    onEditorChange() {
      console.log(this.$refs.toastuiEditor.invoke('getHTML'));
    },
  },
};
</script>
