<template>
  <form class="form" action="" @submit.prevent="handleUploadCommentButton">
    <label for="content" class="sr-only">댓글</label>
    <input
      class="inp-comment"
      type="text"
      id="content"
      name="content"
      placeholder="따뜻한 한 마디 전해주세요..."
      v-model="comment.content"
      ref="inputElem"
    />
    <button type="submit" class="btn-uploadComment" :disabled="!isFormFilled">등록</button>
  </form>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue';
import { useCommentStore } from '@/stores/comment';
import { useRoute } from 'vue-router';
import router from '@/router';

const commentStore = useCommentStore();
const route = useRoute();

const inputElem = ref(null);

const isFormFilled = computed(() => comment.value.content.length > 0);

function b64DecodeUnicode(str) {
  return decodeURIComponent(
    Array.prototype.map
      .call(atob(str), function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join('')
  );
}

const getLoginUser = () => {
  if (!sessionStorage.getItem('access-token')) return;

  const token = sessionStorage.getItem('access-token').split('.');
  let loginInfo = token[1]; // 3개 중에 payload 고름
  loginInfo = b64DecodeUnicode(loginInfo);
  console.log(loginInfo);
  loginInfo = JSON.parse(loginInfo);

  comment.value.userId = loginInfo['userId'];
  comment.value.username = loginInfo['username'];
};

const comment = ref({
  userId: '', // commentId 말고 userId
  username: '',
  content: ''
});

const emit = defineEmits(['uploadComment']);
const handleUploadCommentButton = async (e) => {
  getLoginUser();
  emit('uploadComment', route.params.videoId, comment.value);
  inputElem.value.value = '';
  comment.value.content = '';
};
</script>

<style scoped>
.form {
  display: flex;
  justify-content: space-between;
}

.inp-comment {
  flex-grow: 1;
  padding: 12px;
  border: 1px solid var(--vt-c-text-dark-2);
  border-radius: 4px 0 0 4px;
}

.btn-uploadComment {
  padding: 4px 16px;
  font-size: 16px;
  border: 1px solid var(--vt-c-text-dark-2);
  background-color: hsla(160, 100%, 37%, 1);
  color: var(--vt-c-white-soft);
  border-radius: 0 4px 4px 0;
}

.btn-uploadComment:disabled {
  background-color: var(--vt-c-text-dark-2);
  color: var(--vt-c-divider-dark-1);
  cursor: auto;
}
</style>
