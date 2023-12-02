<template>
  <div class="wrapper-videoview">
    <div class="cont-video">
      <img ref="imgElem" src="" alt="background of video" class="img-background" />
      <video ref="videoElem" src="" class="video" controls></video>
      <!-- <iframe ref="videoElem" src="" class="video" frameborder="0"></iframe> -->
    </div>
    <div class="cont-info">
      <div class="cont-profile">
        <RegularProfile :user="user"></RegularProfile>
        <template v-if="isSameUser">
          <button v-if="!isUserUpdatingContent" class="btn-update" @click="handleUpdateVideoButton">
            수정
          </button>
          <button v-if="!isUserUpdatingContent" class="btn-delete" @click="deleteVideo">
            삭제
          </button>
        </template>
      </div>
      <p v-if="!isUserUpdatingContent" class="txt-content">{{ videoStore.video?.content }}</p>
      <form v-else action="" style="display: flex" @submit.prevent="updateVideo">
        <label class="sr-only" for="">내용 수정</label>
        <input
          ref="contentInputElem"
          type="text"
          class="inp-content"
          :value="videoStore.video?.content"
        />
        <button class="btn-update">수정</button>
        <button class="btn-update" type="button" @click="isUserUpdatingContent = false">
          취소
        </button>
      </form>
    </div>
    <div class="cont-comment" ref="commentDivElem">
      <p class="tit-comment">댓글 {{ numberOfComment }}개</p>
      <CommentUploadForm @upload-comment="uploadComment" />
      <CommentCard
        v-for="comment in commentStore.commentList"
        :comment="comment"
        @delete-comment="deleteComment"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, type Ref, computed, type ComputedRef } from 'vue';
import { useCommentStore, type Comment } from '@/stores/comment';
import { useVideoStore, type Video } from '@/stores/video';
import { useRoute } from 'vue-router';

import CommentUploadForm from '@/components/comment/CommentUploadForm.vue';
import CommentCard from '@/components/comment/CommentCard.vue';
import RegularProfile from '@/components/profile/RegularProfile.vue';
import { useUserStore, type User } from '@/stores/user';
import router from '@/router';

const LOCAL_SERVER = import.meta.env.VITE_LOCAL_SERVER;

const user: Ref<User> = ref({
  userId: '',
  username: ''
});

const videoStore = useVideoStore();
const commentStore = useCommentStore();
const userStore = useUserStore();
const route = useRoute();

const videoElem: Ref<null | HTMLIFrameElement> = ref(null);
const imgElem: Ref<null | HTMLImageElement> = ref(null);

const numberOfComment = computed(() => commentStore.commentList.length);

const isSameUser = computed(() => videoStore.video?.userId === sessionStorage.getItem('userId'));
onMounted(async () => {
  await videoStore.getVideo(parseInt(route.params.videoId as string));
  // video의 src를 자바스크립트로 주입하는 건 CORB를 유발하는 요소?
  videoElem.value!.src = `http://localhost:8080/stream/${videoStore.video!.videoSrc}`;
  imgElem.value!.src = `http://localhost:8080/thumbnail/${videoStore.video!.thumbnailImgSrc}`;
  await userStore.getUsername(videoStore.video?.userId!);
  user.value.userId = videoStore.video?.userId!;
  user.value.username = userStore.username;
  await commentStore.getCommentList(route.params.videoId);
});

const isUserUpdatingContent = ref(false);
const commentDivElem: Ref<null | HTMLDivElement> = ref(null);
const deleteComment = async (videoId: number, commentId: number) => {
  await commentStore.deleteComment(videoId, commentId);
  await commentStore.getCommentList(route.params.videoId);
  commentDivElem.value!.scrollIntoView({ behavior: 'smooth' });
};

const uploadComment = async (videoId: number, comment: Comment) => {
  if (!sessionStorage.getItem('userId')) {
    router.push({ name: 'login' });
    return;
  }

  await commentStore.uploadComment(videoId, comment);
  await commentStore.getCommentList(route.params.videoId);
  commentDivElem.value!.scrollIntoView({ behavior: 'smooth' });
};

const handleUpdateVideoButton = () => {
  isUserUpdatingContent.value = true;
};
const deleteVideo = async () => {
  await videoStore.deleteVideo(parseInt(route.params.videoId as string));
  router.push({ name: 'home' });
};
const contentInputElem: Ref<null | HTMLInputElement> = ref(null);
const updateVideo = async () => {
  const newVideo: Video = JSON.parse(JSON.stringify(videoStore.video));

  // 수정 내용이 같을 경우 요청을 보내지 않음.
  if (newVideo.content === contentInputElem.value!.value) {
    isUserUpdatingContent.value = false;
    return;
  }

  newVideo.content = contentInputElem.value!.value;
  await videoStore.updateVideo(newVideo);
  await videoStore.getVideo(parseInt(route.params.videoId as string));
  isUserUpdatingContent.value = false;
};
</script>

<style scoped>
.wrapper-videoview {
  display: flex;
  flex-direction: column;
  min-width: 520px;
  max-width: 800px;
  margin: 0 auto;
}

.cont-video {
  width: 100%;
  border-radius: 10px 10px 0 0;
  overflow: hidden;
  position: relative;
}

.video {
  width: 100%;
  aspect-ratio: 1/0.85;
  object-fit: contain;
}

.img-background {
  position: absolute;
  width: 100%;
  aspect-ratio: inherit;
  object-fit: cover;
  filter: blur(50px) brightness(0.8);
  z-index: -1;
}

.txt-content {
  padding: 0 0 0 4px;
}

.cont-info {
  background-color: var(--vt-c-text-dark-2);
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
  padding: 20px;
  border-radius: 0 0 10px 10px;
}

.cont-profile {
  display: flex;
}

.inp-content {
  padding: 12px;
  border: 1px solid var(--vt-c-text-dark-2);
  border-radius: 4px;
  background-color: var(--vt-c-white);
  width: 100%;
  box-sizing: border-box;
}

.tit-comment {
  font-size: 20px;
  font-weight: bolder;
}

.btn-delete {
  padding: 0 14px;
  font-size: 16px;
  border: 1px solid var(--vt-c-text-dark-2);
  background-color: hsla(160, 100%, 37%, 1);
  color: var(--vt-c-white-soft);
  border-radius: 4px;
}

.btn-update {
  padding: 0 14px;
  font-size: 16px;
  border: 1px solid var(--vt-c-text-dark-2);
  background-color: var(--vt-c-white-soft);
  margin-right: 10px;
}

.btn-delete:hover {
  background-color: rgb(0, 165, 110);
}

.btn-update:hover {
  background-color: var(--vt-c-text-dark-2);
}
</style>
