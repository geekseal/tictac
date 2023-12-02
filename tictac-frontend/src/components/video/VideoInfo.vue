<template>
  <div class="cont-video-info">
    <p class="txt-content">{{ content }}</p>
    <SmallProfile :user="user" />
  </div>
</template>

<script setup lang="ts">
import SmallProfile from '../Profile/SmallProfile.vue';

const props = defineProps(['content', 'user', 'content', 'userId']);
const user = {
  // user dto
  userId: props.userId,
  id: ''
};

// console.log(props.content);
</script>

<style scoped>
.cont-video-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding-top: 4px;
  padding-bottom: 8px;
}
.txt-content {
  overflow: hidden;
  white-space: normal;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
