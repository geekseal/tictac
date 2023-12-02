import { ref } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import UserView from '@/views/UserView.vue';
import VideoView from '@/views/VideoView.vue';
import VideoUploadView from '@/views/VideoUploadView.vue';
import LoginView from '@/views/LoginView.vue';

const isLogin = ref(sessionStorage.getItem('access-token') == null);

const checkLogin = () => {
  isLogin.value = sessionStorage.getItem('access-token') == null;
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        // 유저 정보
        {
          // @으로 시작하는 것만 매핑 -> 다른 것과 구분하기 위함 예를 들면 login
          path: '/:userId(@.+)',
          name: 'user',
          component: UserView
        },
        {
          path: '/upload',
          name: 'upload',
          component: VideoUploadView,
          beforeEnter: (to, from) => {
            checkLogin();
            if (isLogin.value) {
              console.log('로그인 안 된 상태임');
              return { name: 'login' };
            } else {
              console.log('로그인된 상태');
            }
          }
        },
        {
          path: '/:userId(@.+)/video/:videoId',
          name: 'videoDetail',
          component: VideoView
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    }
  ]
});

export default router;
