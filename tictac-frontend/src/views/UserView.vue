<template>
  <div>
    <div class="wrapper" v-if="isExistUser">
      <BigProfile :user="user"></BigProfile>
      <div class="cont-nav">
        <ul class="list-nav" id="tabList">
          <li v-for="tab in tabs" :key="tab.id">
            <TabItem
              v-bind="tab"
              v-model:current-id="currentId"
              v-model:current-width="currentWidth"
            ></TabItem>
          </li>
        </ul>
        <div :style="barStyle" class="bar"></div>
      </div>

      <section>
        <h3 v-if="!isNotMyList" class="tit-video">동영상</h3>
        <div v-if="isNotMyList" style="text-align: center">
          <IconBase height="50" width="50">
            <IconLock></IconLock>
          </IconBase>
          <p style="font-size: 25px">이 사용자가 즐겨찾기한 동영상은 비공개입니다.</p>
          <p style="font-size: 20px">
            {{ route.params.userId.slice(1) }} 님이 즐겨찾기한 동영상은 현재 숨겨져 있습니다.
          </p>
        </div>
        <ul class="list-video">
          <!-- TODO: key 수정: videoList에서 코드 가져옴 -->
          <li class="boxes">
            <!-- <VideoCard :video="video"> </VideoCard> -->
            <VideoCard
              class="box"
              @video-hover="handleHover"
              v-for="video in currentList"
              :video="video"
            >
              <template v-slot:outer>
                <VideoInfo :content="video.content" :userId="video.userId" />
              </template>
            </VideoCard>
          </li>
        </ul>
      </section>
    </div>
    <div v-if="!isExistUser" class="instruction">
      <IconBase width="100px" height="100px">
        <IconProfile></IconProfile>
      </IconBase>
      <p style="font-size: 25px">이 계정을 찾을 수 없음</p>
      <p style="font-size: 20px">
        동영상을 찾고 계시나요? 인기 있는 크리에이터, 해시태그 및 사운드를 찾아보세요.
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, watch, onMounted, ref, type Ref } from 'vue';
import BigProfile from '@/components/profile/BigProfile.vue';
import TabItem from '@/components/common/TabItem.vue';
import VideoCard from '@/components/video/VideoCard.vue';
import VideoInfo from '@/components/video/VideoInfo.vue';
import { useVideoStore } from '@/stores/video';
import { useUserStore } from '@/stores/user';
import { routerKey, useRoute, useRouter } from 'vue-router';
import { type Video } from '@/stores/video';
import IconBase from '@/components/icon/IconBase.vue';
import IconLock from '@/components/icon/IconLock.vue';
import IconProfile from '@/components/icon/IconProfile.vue';

const video1: Video = {
  videoId: 0,
  userId: '',
  content: '',
  videoSrc: '',
  orgVideoSrc: '',
  regDate: new Date(),
  heartCnt: 0,
  bookmarkCnt: 0,
  viewCnt: 0,
  thumbnailImgSrc: '',
  orgThumbnailImgSrc: '',
  categoryId: ''
};

const videoStore = useVideoStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const user = {
  username: ref<String>(),
  id: route.params.userId.slice(1)
};

const isExistUser = ref(false);

const getUsername = async () => {
  await userStore.getUsername(route.params.userId.slice(1));
  user.username.value = userStore.username;
  // 있는 user만 마이페이지 뜨도록 함
  if (user.username.value !== null && user.username.value !== '') {
    isExistUser.value = true;
  }
};

const getUploadList = async () => {
  await videoStore.getUploadList(user.id);
  tabs.value[0].content = videoStore.videoList;
};

const getMyBookmarkList = async () => {
  await videoStore.getMyBookmarkList(user.id);
  tabs.value[1].content = videoStore.videoList;
};

onMounted(() => {
  // 0. 로그인 안돼있어서 userId가 null이면 로그인페이지로 이동
  if (sessionStorage.getItem('userId') === null) {
    router.push('/login');
  }

  // 1. url에 적힌 userId에 대응하는 username 받음
  getUsername();

  // 2. 업로드한 동영상 통신
  getUploadList();

  // 3. 찜한 동영상 통신 -> watch() 로 옮김
  // getMyBookmarkList();
});

// tab
const currentId = ref(0);
const tabs = ref([
  { id: 0, label: '동영상', content: [video1], offset: 0 }, //비디오 객체가 들어갈 곳
  { id: 1, label: '즐겨찾기', content: [video1], offset: 118 }
]);

// const currentList = computed(() => tabs[currentId.value].content);
const currentList: any = ref([]);

const loginUserId = sessionStorage.getItem('userId');
let isNotMyList = computed(
  () =>
    tabs.value[currentId.value].label === '즐겨찾기' && loginUserId !== route.params.userId.slice(1)
);
// const handleisNotMyList;

watch(
  tabs,
  (newValue) => {
    console.log('계산했다');
    currentList.value = tabs.value[currentId.value].content;
  },
  { deep: true }
);

// 동영상tab <-> 즐겨찾기tab 이동할때를 위한 watch
watch(
  currentId,
  (newValue) => {
    if (newValue == 1) {
      // console.log('계산했다2-즐겨찾기tab');
      // console.log(loginUserId);
      if (loginUserId === route.params.userId.slice(1)) {
        // 3. 찜한 동영상 통신
        getMyBookmarkList();
      } else {
        tabs.value[1].content = [];
      }
    } else if (newValue == 0) {
      // console.log('계산했다2-동영상tab');
      // 2. 업로드한 동영상 통신
      getUploadList();
    }
    // isNotMyList =
    //   tabs.value[currentId.value].label === '즐겨찾기' &&
    //   loginUserId !== route.params.userId.slice(1);
  },
  { deep: true }
);

const currentWidth: Ref<Number> = ref(118);
const barStyle = computed(() => ({
  width: currentWidth.value + 'px',
  transform: 'translateX(' + tabs.value[currentId.value].offset + 'px)'
}));

// videoList에서 코드 가져옴
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
</script>

<style scoped>
.list-nav {
  display: flex;
  margin-top: 40px;
  border-bottom: 1px solid var(--vt-c-text-dark-2);
}
.cont-nav {
  position: relative;
  margin-bottom: 20px;
}

.cont-nav .bar {
  position: absolute;
  height: 2px;
  background: rgb(22, 24, 35);
  bottom: 0px;
  transition: transform 0.3s ease 0s;
}

.tit-video {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

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

.instruction {
  text-align: center;
  padding-top: 300px;
}
</style>
