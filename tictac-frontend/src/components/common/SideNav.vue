<template>
  <aside class="wrapper">
    <nav class="nav">
      <ul class="list-nav">
        <li>
          <RouterLink to="/">
            <IconBase stroke-width="0.5">
              <IconHome></IconHome>
            </IconBase>
            홈
          </RouterLink>
        </li>
        <li v-if="isLogin">
          <RouterLink :to="'/@' + loginUserId">
            <IconBase stroke-width="0.5">
              <IconProfile></IconProfile>
            </IconBase>
            마이페이지
          </RouterLink>
        </li>
      </ul>
    </nav>

    <small>
      <div class="list-info">
        <RouterLink to="">도움말</RouterLink>
        <RouterLink to="">개인 정보</RouterLink>
        <RouterLink to="">저작권</RouterLink>
      </div>

      <span>&copy; 2023 TicTac</span>
    </small>
  </aside>
</template>

<script setup lang="ts">
import IconBase from '../icon/IconBase.vue';
import IconHome from '../icon/IconHome.vue';
import IconProfile from '../icon/IconProfile.vue';
import { RouterLink } from 'vue-router';
import { ref } from 'vue';

const loginUserId = sessionStorage.getItem('userId');

const isLogin = ref(loginUserId !== null);
</script>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
  gap: 10px;

  margin: 20px 0 26px 8px;
  box-shadow: rgba(0, 0, 0, 0.06) 0px 2px 8px;
  height: auto;
}

.nav a {
  color: var(--vt-c-divider-dark-1);
}

.list-nav {
  border-bottom: 1px solid var(--vt-c-text-dark-2);
}

.list-nav li a {
  display: inline-block;
  width: 208px;
  font-weight: 600;
  font-size: 18px;
  padding: 10px;
}

.list-nav li a.router-link-exact-active {
  color: hsla(160, 100%, 37%, 1);
}

small {
  font-size: 12px;
}

small .list-info {
  display: flex;
  gap: 6px;
}
</style>
