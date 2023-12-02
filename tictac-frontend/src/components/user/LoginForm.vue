<template>
  <article class="wrapper">
    <h2 class="tit-login">TicTac에 로그인</h2>
    <form action="" class="form-login" @submit.prevent="handleLoginButton">
      <label for="id">아이디</label>
      <input type="text" id="id" v-model="id" />
      <label for="password">패스워드</label>
      <input type="password" id="password" v-model="password" />
      <button type="button">비밀번호를 잊으셨나요?</button>
      <button type="submit" class="btn-login" :disabled="!isFormFilled">로그인</button>
    </form>
  </article>
</template>

<script setup lang="ts">
import { computed, ref, type Ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const router = useRouter();

const id: Ref<string> = ref('');
const password: Ref<string> = ref('');
const isFormFilled = computed(() => id.value.length > 0 && password.value.length > 0);

const userStore = useUserStore();

const handleLoginButton = async () => {
  await userStore.login(id.value, password.value);
  router.push({ name: 'home' });
};
</script>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  width: 400px;
  margin: 0 auto;
}
.tit-login {
  font-size: 32px;
  font-weight: bold;
}

.form-login {
  display: flex;
  flex-direction: column;
  gap: 10px;

  width: 100%;
}

label {
  font-size: 16px;
  font-weight: bold;
}

input {
  font-size: 16px;
  height: 44px;
  padding: 0 10px;

  background: rgba(22, 24, 35, 0.06);
  transition: all 0.3s ease 0s;
  border-radius: 4px;
  display: flex;
  /* border: 1px solid rgb(255, 76, 58); */
}

.btn-login {
  border-radius: 4px;
  width: 100%;
  font-size: 14px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 0;

  background-color: hsla(160, 100%, 37%, 1);
  color: var(--vt-c-white-soft);
}

.btn-login:disabled {
  background-color: var(--vt-c-text-dark-2);
  color: var(--vt-c-divider-dark-1);
  cursor: auto;
}
</style>
