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
import RoomReady from '@/views/room/RoomReady';

import MeetingRoom from '@/views/meetingRoom/MeetingRoom.vue';

import MyPage from '@/views/mypage/MyPage.vue';
import MyInfo from '@/views/mypage/MyInfo.vue';
import Presentation from '@/views/mypage/Presentation.vue';
import PresentationPreview from '@/views/mypage/PresentationPreview.vue';
import ModifyPresentation from '@/views/mypage/ModifyPresentation.vue';
import PageNotFound from '@/components/common/PageNotFound.vue';

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
        path: '/front/oauth2/redirect',
        name: 'Oauth2Redirect',
        component: Oauth2Redirect,
      },
    ],
  },

  {
    path: '/dashboard',
    component: RoomPage,
    children: [
      {
        path: '',
        name: 'RoomDashboard',
        component: RoomDashboard,
        meta: { authRequired: true },
      },
      {
        path: '/dashboard/create',
        component: RoomCreate,
        meta: { authRequired: true },
      },
      {
        path: '/dashboard/info',
        component: RoomInfo,
        meta: { authRequired: true },
      },
      {
        path: '/dashboard/tutorial',
        component: RoomTutorial,
        meta: { authRequired: true },
      },
      {
        path: '/dashboard/:room_id',
        component: RoomReady,
        meta: { authRequired: false },
      },
    ],
  },
  {
    path: '/meetingroom/:roomNumber',
    name: 'MeetingRoom',
    component: MeetingRoom,
    meta: { authRequired: true },
  },
  {
    path: '/mypage',
    component: MyPage,
    meta: { authRequired: true },
    children: [
      {
        path: '',
        name: 'MyInfo',
        component: MyInfo,
      },
      {
        path: '/mypage/presentation',
        name: 'Presentation',
        component: Presentation,
      },
    ],
  },
  {
    path: '/mypage/presentation/:presentation_id/:name',
    name: 'ModifyPresentation',
    component: ModifyPresentation,
    meta: { authRequired: true },
  },
  {
    // path: '/mypage/presentationpreview/:presentation_id/:name',
    path: '/mypage/presentationpreview/:userId/:presentationId',
    name: 'PresentationPreview',
    component: PresentationPreview,
    meta: { authRequired: true },
  },
  {
    path: '/*',
    name: 'PageNotFound',
    component: PageNotFound,
  },
];
