<template>
  <div class="wrapper">
    <div class="cont-video">
      <RouterLink :to="`/@${video.userId}/video/${video.videoId}`">
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
      </RouterLink>

      <div class="cont-control">
        <div>
          <IconBase>
            <IconPlay></IconPlay>
          </IconBase>
          <span>12.7M</span>
        </div>
        <button>
          <IconBase v-if="showVolumeBtn" @click="handleVolumeBtn">
            <IconVolumeUp v-if="showVolumeOn"></IconVolumeUp>
            <IconVolumeOff v-else></IconVolumeOff>
          </IconBase>
        </button>
      </div>
    </div>
    <slot name="outer"></slot>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import type { Ref } from 'vue';
import type { Video } from '@/stores/video';

import IconBase from '../icon/IconBase.vue';
import IconPlay from '../icon/IconPlay.vue';
import IconVolumeUp from '../icon/IconVolumeUp.vue';
import IconVolumeOff from '../icon/IconVolumeOff.vue';

const LOCAL_SERVER = import.meta.env.VITE_LOCAL_SERVER;

const emit = defineEmits(['videoHover']);
const props = defineProps<{ video: Video }>();
//TODO: 숫자를 1.7M 같은 형태로 가공하기

//영상 호버 자동재생
const videoElem: Ref<HTMLVideoElement | null> = ref(null);
const showVideoRef: Ref<boolean> = ref(false);
const showVolumeBtn = computed(() => showVideoRef.value);
const emitHover = () => {
  emit('videoHover', showVideoRef, videoElem);
};

//볼륨 버튼 조작
const showVolumeOn = ref(false);
const handleVolumeBtn = () => {
  showVolumeOn.value = !showVolumeOn.value;
  videoElem.value!.muted = !videoElem.value!.muted;
};

//영상 모달 띄우기
/*
const router = useRouter();
const addHashToLocation = () => {
  history.pushState({}, '', '/@' + 'ryurlah' + '/video/' + encodeURIComponent(10000));
};
//10000 -> props.video.id
<button @click="addHashToLocation">
*/
</script>

<style scoped>
.wrapper {
  width: 252px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.cont-video {
  position: relative;
  width: 100%;
  height: 336px;

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

.cont-control {
  display: flex;
  justify-content: space-between;

  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;

  padding: 10px 14px;
  font-weight: bolder;
  color: var(--vt-c-white);
}
</style>
