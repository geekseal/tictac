<template>
  <header class="app-header">
    <h1 class="tit-header">
      <RouterLink to="/" class="link-home">
        <img src="@/assets/logo.png" alt="" />
      </RouterLink>
    </h1>
    <!-- <SearchBar></SearchBar> -->
    <div class="cont-nav">
      <RouterLink to="/upload" class="btn-upload">
        <IconBase icon-name="upload">
          <IconPlus></IconPlus>
        </IconBase>
        업로드
      </RouterLink>
      <RouterLink to="/login" class="btn-login" v-if="isLogin">로그인</RouterLink>
      <button v-else @click="logout" class="btn-login">로그아웃</button>
      <RouterLink :to="'/@' + user.id" v-if="!isLogin">
        <img class="img-profile" src="@/assets/sample/excitedinseun.png" alt="" />
      </RouterLink>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';

import SearchBar from './SearchBar.vue';
import IconBase from '../icon/IconBase.vue';
import IconPlus from '../icon/IconPlus.vue';
import router from '@/router';

const isLogin = ref(sessionStorage.getItem('access-token') === null);

const checkLogin = () => {
  isLogin.value = sessionStorage.getItem('access-token') === null;
};

const logout = () => {
  sessionStorage.clear();
  router.go(0); // 새로고침
};

const user = {
  username: sessionStorage.getItem('username'),
  id: sessionStorage.getItem('userId')
};

onMounted(() => {
  checkLogin();
});
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid var(--vt-c-text-dark-2);

  padding: 10px 16px 10px 24px;
}

.tit-header {
  height: fit-content;
}

.link-home {
  display: inline-block;
  border-radius: 8px;
  overflow: hidden;
  vertical-align: top;
}

.cont-nav {
  display: flex;
  gap: 20px;
  align-items: center;
  font-weight: bold;
}

.btn-upload {
  display: flex;
  gap: 2px;

  padding: 4px 16px 4px 8px;
  font-size: 16px;
  border: 1px solid var(--vt-c-text-dark-2);
}

.btn-login {
  padding: 4px 16px;
  font-size: 16px;
  border: 1px solid var(--vt-c-text-dark-2);
  background-color: hsla(160, 100%, 37%, 1);
  color: var(--vt-c-white-soft);
  border-radius: 4px;
}

.btn-login:hover {
  background-color: rgb(0, 165, 110);
}

.btn-upload:hover {
  background-color: var(--vt-c-text-dark-2);
}

.img-profile {
  width: 32px;
  height: 32px;
  border-radius: 100%;
  object-fit: cover;
}
</style>
