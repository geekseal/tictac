<template>
  <div class="wrapper">
    <RouterLink :to="'/@' + comment.userId">
      <img class="img-profile" src="@/assets/sample/excitedinseun.png" />
    </RouterLink>
    <ul class="cont-info">
      <RouterLink :to="'/@' + comment.userId">
        <li class="username">{{ comment.username }}</li>
      </RouterLink>
      <li>{{ comment.content }}</li>
      <li>
        <ul class="regDate">
          <li>{{ comment.regDate }}</li>
          <button>회신</button>
          <button @click="handleDeleteCommentButton" v-if="isSameUser()">삭제</button>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';

const route = useRoute();

const props = defineProps(['comment']);

const emit = defineEmits(['deleteComment']);

// TODO: 생각해볼 문제 - 클라이언트에서 sessionStorage.setItem('userId', 'inseung') 등의 공격이 들어올 수 있음.
const isSameUser = () => props.comment!.userId === sessionStorage.getItem('userId');

const handleDeleteCommentButton = () => {
  emit('deleteComment', route.params.videoId, props.comment!.commentId);
};
</script>

<style scoped>
.username {
  font-weight: bold;
}

.regDate {
  display: flex;
  flex-direction: row;
  /* align-items: center; */
  gap: 10px;
  color: var(--vt-c-divider-dark-2);
}

.img-profile {
  height: 2rem;
  border-radius: 100%;
  aspect-ratio: 1/1;
  object-fit: cover;
}

.wrapper {
  display: flex;
  flex-direction: row;
  /* align-items: center; */
  gap: 10px;
  padding: 10px;
}

.cont-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

/* button {
  border: 1px solid var(--vt-c-divider-light-2);
} */
</style>
