import IntroPage from '@/views/intro/IntroPage.vue';
import IntroContent from '@/views/intro/IntroContent.vue';
import IntroLogin from '@/views/intro/IntroLogin.vue';
import IntroSignup from '@/views/intro/IntroSignup.vue';
import Oauth2Redirect from '@/views/intro/Oauth2Redirect.vue';

import RoomPage from '@/views/room/RoomPage';
import RoomDashboard from '@/views/room/RoomDashboard';
import RoomCreate from '@/views/room/RoomCreate';
import RoomInfo from '@/views/room/RoomInfo';
import RoomTutorial from '@/views/room/RoomTutorial';

import MeetingRoom from '@/views/meetingRoom/MeetingRoom.vue';

import MyPage from '@/views/mypage/MyPage.vue';
import MyInfo from '@/views/mypage/MyInfo.vue';
import MyPPT from '@/views/mypage/MyPPT.vue';
import ModifyPPT from '@/views/mypage/ModifyPPT.vue';

export default [
  {
    path: '/',
    component: IntroPage,
    meta: { authRequired: false },
    children: [
      {
        path: '',
        component: IntroContent,
      },
      {
        path: '/login',
        component: IntroLogin,
      },
      {
        path: '/signup',
        component: IntroSignup,
      },
      {
        path: '/oauth2/redirect',
        name: 'Oauth2Redirect',
        component: Oauth2Redirect,
      },
    ],
  },

  {
    path: '/dashboard',
    component: RoomPage,
    meta: { authRequired: false },
    // meta: { authRequired: true },
    children: [
      {
        path: '',
        name: 'RoomDashboard',
        component: RoomDashboard,
      },
      {
        path: '/dashboard/create',
        component: RoomCreate,
      },
      {
        path: '/dashboard/info',
        component: RoomInfo,
      },
      {
        path: '/dashboard/tutorial',
        component: RoomTutorial,
      },
    ],
  },
  {
    path: '/meetingroom',
    name: 'MeetingRoom',
    component: MeetingRoom,
    meta: { authRequired: false },
    // meta: { authRequired: true },
  },
  {
    path: '/mypage',
    component: MyPage,
    meta: { authRequired: true },
    children: [
      {
        path: '',
        component: MyInfo,
      },
    ],
  },
  {
    path: '',
    component: MyPage,
    meta: { authRequired: false },
    // meta: { authRequired: true },
    children: [
      {
        path: '/mypage',
        component: MyInfo,
      },
      {
        path: '/mypage/myppt',
        name: 'MyPPT',
        component: MyPPT,
      },
    ],
  },
  {
    path: '/mypage/modifyppt/:id',
    name: 'ModifyPPT',
    component: ModifyPPT,
  },
];
