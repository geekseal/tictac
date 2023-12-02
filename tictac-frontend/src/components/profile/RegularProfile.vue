<template>
  <div class="wrapper-profile">
    <div class="cont-img">
      <RouterLink class="link-img" :to="'/@' + user.userId">
        <img class="img-profile" src="@/assets/sample/excitedinseun.png" alt="프로필 사진입니다." />
      </RouterLink>
    </div>
    <div class="cont-info">
      <RouterLink :to="'/@' + user.userId" class="link-id">
        {{ user.userId }}
      </RouterLink>
      <RouterLink :to="'/@' + user.userId" class="link-username">
        {{ user.username }}
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router';

import { type User } from '@/stores/user';

export interface Props {
  user: User;
}

defineProps<Props>();
</script>

<style scoped>
.wrapper-profile {
  min-width: 520px;

  display: flex;
  gap: 20px;
}

.cont-img {
  display: flex;
  align-items: center;
}

.link-img {
  width: 40px;
  height: 40px;
  display: inline-block;
}

.img-profile {
  width: 100%;
  aspect-ratio: 1/1;
  object-fit: cover;
  border-radius: 100%;
}

.cont-info {
  display: flex;
  flex-direction: column;
}

.link-id {
  font-size: 16px;
  font-weight: bold;
}

.link-username {
  font-size: 12px;
  font-weight: 600;
}
</style>
