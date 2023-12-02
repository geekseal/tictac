<template>
  <ul class="list-category">
    <li v-for="video in videoList" :key="video.videoId">
      <CategoryCard
        @update-category="updateCategory"
        class="box"
        @video-hover="handleHover"
        :video="video"
      >
      </CategoryCard>
    </li>
  </ul>
</template>

<script setup lang="ts">
import CategoryCard from './CategoryCard.vue';
import { onMounted, ref, type Ref } from 'vue';
import { useVideoStore } from '@/stores/video';
import { type Video } from '@/stores/video';

const videoStore = useVideoStore();
const emit = defineEmits(['updateCategory']);

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

const getCategories = async (categoryNums: number) => {
  await videoStore.getWholeVideoList();
  const tempList = [];
  // categoryNums 만큼 video 가져옴
  for (let i = 0; i < categoryNums; i++) {
    tempList.push(videoStore.videoList[i]);
  }
  videoList.value = tempList;
};

const videoList: Ref<Video[]> = ref([]);

onMounted(() => {
  getCategories(5);
});

const updateCategory = function (categoryId: string) {
  emit('updateCategory', categoryId);
};
</script>

<style scoped>
.list-category {
  display: flex;
  flex-direction: row;
  gap: 20px;
}

.list-category li {
  height: 302px;
  flex-shrink: 0;
  flex-basis: 226px;
}
</style>
