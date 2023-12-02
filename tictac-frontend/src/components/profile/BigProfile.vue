<template>
  <div class="wrapper">
    <div class="cont-img">
      <RouterLink :to="'/@' + user.id" class="link-img">
        <img class="img-profile" src="@/assets/sample/excitedinseun.png" alt="프로필 사진입니다." />
      </RouterLink>
    </div>
    <div class="cont-info">
      <RouterLink :to="'/@' + user.id" class="link-id">
        {{ user.id }}
      </RouterLink>
      <RouterLink :to="'/@' + user.id" class="link-username">
        {{ user.username.value }}
      </RouterLink>
      <!-- <RouterLink :to="'/@' + user.id" class="btn-edit"> 프로필 편집 </RouterLink> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router';

defineProps(['user']);
</script>

<style scoped>
.wrapper {
  max-width: 624px;
  min-width: 520px;
  height: fit-content;

  display: flex;
  gap: 30px;
}

.link-img {
  display: inline-block;
  width: 116px;
  height: 116px;
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
  justify-content: center;
  gap: 20px;
}

.link-id {
  font-size: 42px;
  font-weight: bold;
  line-height: 1;
}

.link-username {
  font-size: 18px;
  font-weight: 600;
  line-height: 1;
}

/* .btn-edit {
  font-size: 16px;
  border: 1px solid;
  padding: 0 16px;
  border-radius: 4px;
  color: var(--vt-c-divider-dark-1);
} */
</style>
