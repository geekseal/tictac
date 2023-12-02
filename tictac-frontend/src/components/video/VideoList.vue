<template>
  <ul>
    <li class="boxes">
      <VideoCard class="box" @video-hover="handleHover" v-for="video in videoList" :video="video">
        <template v-slot:outer>
          <VideoInfo :content="video.content" :userId="video.userId" />
        </template>
      </VideoCard>
    </li>
  </ul>
</template>

<script setup lang="ts">
import VideoCard from './VideoCard.vue';
import VideoInfo from './VideoInfo.vue';
import { onMounted, ref, type Ref, watch, computed } from 'vue';
import { useVideoStore } from '@/stores/video';
import { type Video } from '@/stores/video';

const videoStore = useVideoStore();
const props = defineProps(['categoryId']);

const queue: Ref<boolean>[] = [];
const handleHover = (showVideoRef: Ref<boolean>) => {
  //해당 영상만 재생
  showVideoRef.value = true;
  queue.push(showVideoRef);
  if (queue.length > 1) {
    while (queue.length > 1) {
      queue.shift()!.value = false;
    }
  }
};

const getCategoryList = async (categoryId: string) => {
  await videoStore.getCategoryList(categoryId);
  videoList.value = videoStore.videoList;
};

const videoList: Ref<Video[]> = ref([]);

onMounted(() => {
  getCategoryList(category.value);
});

const category = computed(() => {
  return props.categoryId;
});

watch(
  category,
  (newValue) => {
    if (queue.length >= 1) {
      while (queue.length > 0) {
        queue.shift()!.value = false;
      }
    }
    getCategoryList(category.value);
  },
  { deep: true }
);
</script>

<style scoped>
.boxes {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  flex-basis: 200px;
}

.box {
  margin: 6px;
  min-width: 100px;
}
</style>
