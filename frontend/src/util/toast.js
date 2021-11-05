import Vue from 'vue';
import Toast from 'vue-toastification';
Vue.use(Toast);
const methods = {
  toastError: message => {
    Vue.$toast.error(message, {
      timeout: 2000,
      draggable: false,
      position: 'bottom-right',
      pauseOnFocusLoss: false,
      pauseOnHover: false,
    });
  },
  toastSuccess: message => {
    Vue.$toast.success(message, {
      timeout: 2000,
      draggable: false,
      position: 'bottom-right',
      pauseOnFocusLoss: false,
      pauseOnHover: false,
    });
  },
  toastInfo: message => {
    Vue.$toast.info(message, {
      timeout: 2000,
      draggable: false,
      position: 'bottom-right',
      pauseOnFocusLoss: false,
      pauseOnHover: false,
    });
  },
  toastWarning: message => {
    Vue.$toast.warning(message, {
      timeout: 2000,
      draggable: false,
      position: 'bottom-right',
      pauseOnFocusLoss: false,
      pauseOnHover: false,
    });
  },
};

export { methods };
