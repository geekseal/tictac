<template>
  <div class="wrapper">
    <div class="cont-video" @click="updateCategory">
      <button>
        <picture>
          <img
            @mouseover="emitHover"
            :src="`http://localhost:8080/thumbnail/${video.thumbnailImgSrc}`"
            alt="사진입니다."
            class="img"
          />
        </picture>
        <video
          v-if="showVideoRef"
          ref="videoElem"
          muted="true"
          autoplay
          playsinline
          preload="auto"
          loop
          class="video"
        >
          <source :src="`http://localhost:8080/stream/${video.videoSrc}`" />
        </video>
      </button>

      <button class="btn-category">
        {{ video.categoryId }}
      </button>
    </div>
    <slot name="outer"></slot>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import type { Ref } from 'vue';
import type { Video } from '@/stores/video';

const LOCAL_SERVER = import.meta.env.VITE_LOCAL_SERVER;

const emit = defineEmits(['videoHover', 'updateCategory']);
const props = defineProps<{ video: Video }>();

//영상 호버 자동재생
const videoElem: Ref<HTMLVideoElement | null> = ref(null);
const showVideoRef: Ref<boolean> = ref(false);
const emitHover = () => {
  emit('videoHover', showVideoRef, videoElem);
};

const updateCategory = () => {
  emit('updateCategory', props.video.categoryId);
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.cont-video {
  position: relative;
  width: 100%;
  height: 302px;

  border-radius: 10px;
  overflow: hidden;
}

.img {
  width: 100%;
  height: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  object-fit: cover;
}

.video {
  width: 100%;
  height: 100%;

  object-fit: cover;
  position: absolute;
  bottom: 0;
  left: 0;
  /* aspect-ratio: 4/3; */
}

.btn-category {
  display: inline-block;
  text-align: center;

  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;

  margin: 30px 30px;
  padding: 10px 5px;
  font-size: 17px;
  font-weight: bolder;
  border-radius: 4px;
  color: var(--vt-c-white);
  background-color: hsla(160, 100%, 37%, 0.9);
}

.btn-category:hover {
  background-color: rgb(0, 165, 110);
}
</style>
