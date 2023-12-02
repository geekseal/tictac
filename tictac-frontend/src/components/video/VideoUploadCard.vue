<template>
  <div class="wrapper">
    <!-- before drop -->
    <article
      v-if="isBeforeDrop"
      draggable
      class="cont-before"
      @click="handleUploadButton"
      @drop.prevent="handleDrop"
      @dragover.prevent=""
      @change="handleChange"
    >
      <IconBase width="48" height="48" icon-color="var(--vt-c-text-light-2)">
        <IconVideo></IconVideo>
      </IconBase>
      <p class="cont-restrict">
        <span style="font-size: 18px; color: black">업로드할 동영상 선택</span>
        <span>또는 파일을 끌어서 놓기</span>
        <span>MP4 파일</span>
        <span>720x1280 해상도 이상</span>
        <span>최대 3분</span>
        <span>3GB 미만</span>
      </p>
      <input ref="inputElem" accept="video/mp4" id="file" type="file" style="display: none" />
      <button class="btn btn-upload">파일 선택</button>
    </article>
    <!-- after drop -->
    <form v-else class="cont-after" method="POST" @submit.prevent="handleSubmit">
      <label for="category">카테고리</label>
      <div class="cont-category">
        <select ref="categoryElem" name="category" id="category">
          <option value="파쿠르">파쿠르</option>
          <option value="스케이트보드">스케이트보드</option>
          <option value="카포에라">카포에라</option>
          <option value="광선검">광선검</option>
          <option value="독 어질리티">독 어질리티</option>
        </select>
      </div>
      <div class="tit-content">
        <label for="content">내용</label><span>{{ wordCnt }} / {{ wordLimit }}</span>
      </div>
      <div class="cont-textarea">
        <textarea
          :maxlength="wordLimit"
          @input="calcHeight"
          name="content"
          id="content"
          rows="1"
          class="inp-content"
        ></textarea>
      </div>
      <label for="thumbnail" @click.prevent="">커버</label>
      <video
        preload="auto"
        @loadedmetadata="onLoadedMetadata"
        src=""
        ref="videoElem"
        style="display: none"
      ></video>
      <canvas ref="canvasElem" style="display: none"></canvas>
      <img ref="test" src="" alt="" />
      <div class="cont-pick">
        <div class="cont-candidate">
          <img
            v-for="snapshot in snapshots"
            :key="snapshot"
            :src="snapshot"
            alt="candidate"
            class="img-candidate"
          />
        </div>
        <div ref="chooseElem" class="cont-chosen">
          <video preload="auto" src="" ref="chosenVideoElem" class="video-chosen"></video>
        </div>
      </div>
      <p>
        TicTac에 동영상을 제출하면 TicTac 커뮤니티 가이드라인에 동의함을 인정하는 것입니다. 불법
        촬영 콘텐츠를 업로드하면 법률(통신사업법, 22-5조)에 따라 처벌되고 삭제될 수 있습니다.
      </p>
      <div class="cont-btn">
        <button type="button" class="btn btn-delete">삭제</button>
        <button type="submit" class="btn btn-upload">게시</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, type Ref, nextTick } from 'vue';
import { useVideoStore } from '@/stores/video';

import IconBase from '../icon/IconBase.vue';
import IconVideo from '@/components/icon/IconVideo.vue';
import { useRouter } from 'vue-router';

const inputElem: Ref<null | HTMLInputElement> = ref(null);
const categoryElem: Ref<null | HTMLSelectElement> = ref(null);

const handleUploadButton = () => {
  inputElem.value!.click();
};

const wordCnt = ref(0);
const wordLimit = 800;
// control textarea
const calcHeight = (e: Event) => {
  const target = e.target as HTMLInputElement;
  target.style.height = 'auto';
  target.style.height = `${target.scrollHeight}px`;

  wordCnt.value = target.value.length;
};

// 파일 업로드
const isBeforeDrop = ref(true);
const videoElem: Ref<null | HTMLVideoElement> = ref(null);
const canvasElem: Ref<null | HTMLCanvasElement> = ref(null);
const chooseElem: Ref<null | HTMLDivElement> = ref(null);
const chosenVideoElem: Ref<null | HTMLVideoElement> = ref(null);
let uploadedFile: File;

// 1.기존 input upload 방식
const handleChange = () => {
  if (!inputElem.value?.files) return;
  const file = inputElem.value?.files[0];
  fileHandler(file);
};
// 2. drag & drop
const handleDrop = (e: DragEvent) => {
  if (!e.dataTransfer) return;
  const file = e.dataTransfer.files[0];
  fileHandler(file);
};
// 3. 공통 로직
const snapshots: Ref<string[]> = ref([]);
const fileHandler = async (file: File) => {
  if (!file?.type.startsWith('video')) {
    alert('지원되지 않는 파일입니다. 대신 mp4 등의 영상을 사용하세요.');
    return;
  }

  // change view
  isBeforeDrop.value = false;
  uploadedFile = file;
  // wait, because of v-if
  await nextTick();
  const videourl = URL.createObjectURL(uploadedFile);
  videoElem.value!.src = videourl;
  chosenVideoElem.value!.src = videourl;

  // choose bar 옮기기
  let active = false;
  let currentX: number;
  let initialX: number;
  let xOffset: number = 0;
  if (!chooseElem.value) return;
  chooseElem.value.addEventListener('touchstart', dragStart, false);
  chooseElem.value.addEventListener('touchend', dragEnd, false);
  chooseElem.value.addEventListener('touchmove', drag, false);
  chooseElem.value.addEventListener('mousedown', dragStart, false);
  chooseElem.value.addEventListener('mouseup', dragEnd, false);
  chooseElem.value.addEventListener('mousemove', drag, false);

  function dragStart(e: any) {
    if (e.type === 'touchstart') {
      initialX = e.touches[0].clientX - xOffset;
    } else {
      initialX = e.clientX - xOffset;
    }

    if (e.target === chosenVideoElem.value) {
      active = true;
    }
  }

  function dragEnd(e: any) {
    initialX = currentX;
    active = false;
  }

  function drag(e: any) {
    if (active) {
      e.preventDefault();

      if (e.type === 'touchmove') {
        currentX = e.touches[0].clientX - initialX;
      } else {
        currentX = e.clientX - initialX;
      }

      if (currentX < 4 || currentX > 580) return;

      xOffset = currentX;
      setTranslate(currentX, chooseElem.value);
      const duration = videoElem.value!.duration;

      // 유저가 설정한 시간대로 변경
      chosenVideoElem.value!.currentTime = duration * (currentX / 576);
    }
  }

  function setTranslate(xPos: number, el: any) {
    el.style.transform = 'translate3d(' + xPos + 'px, ' + '1px, 0) scaleX(1.1) scaleY(1.1)';
  }
};

const onLoadedMetadata = () => {
  // 캡처할 캔버스의 크기 동적으로 주입
  // console.log(videoElem.value!.videoWidth);
  // console.log(videoElem.value!.videoHeight);
  canvasElem.value!.width = videoElem.value!.videoWidth;
  canvasElem.value!.height = videoElem.value!.videoHeight;
  const duration = videoElem.value!.duration;
  const term = duration / 7;

  // TODO: 맞는 방법인지 불확실
  let i = 0;
  var id: any;
  id = setInterval(() => {
    videoElem.value!.currentTime = i * term;
    canvasElem
      .value!.getContext('2d')!
      .drawImage(videoElem.value!, 0, 0, canvasElem.value!.width, canvasElem.value!.height);

    canvasElem.value!.toBlob((blob: any) => {
      snapshots.value.push(window.URL.createObjectURL(blob));
    });
    if (++i > 7) {
      clearInterval(id);
    }
  }, 200);
};

const test: Ref<null | HTMLImageElement> = ref(null);
const router = useRouter();
// upload video
const videoStore = useVideoStore();
const handleSubmit = async (e: Event) => {
  const target = e.target as HTMLFormElement;

  const formData = new FormData(target);
  // userId
  formData.append('userId', sessionStorage.getItem('userId')!);
  // category
  console.log(categoryElem.value!.value);
  formData.append('categoryId', categoryElem.value!.value);
  // video
  if (uploadedFile) {
    formData.append('file', uploadedFile);
  }
  // thumbnail
  canvasElem
    .value!.getContext('2d')!
    .drawImage(chosenVideoElem.value!, 0, 0, canvasElem.value!.width, canvasElem.value!.height);
  const blob: Blob = await new Promise((resolve) =>
    canvasElem.value!.toBlob((blob: any) => resolve(blob))
  );
  /* equals to
  const blob: Blob = await new Promise((resolve: any) =>
    canvasElem.value!.toBlob(resolve)
  );
  */
  test.value!.src = URL.createObjectURL(blob);
  formData.append('file', blob);

  // 통신
  try {
    await videoStore.uploadVideo(formData);
    router.push({
      name: 'home'
    });
  } catch (error) {
    alert('업로드 실패');
    console.log(error);
  }
};
</script>

<style scoped>
.wrapper {
  width: 700px;
  margin: 0 auto;
}
.cont-before {
  padding: 80px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.15s ease-in-out 0s;
  border: 2px dashed var(--vt-c-text-dark-2);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 30px;
  min-width: 460px;
  max-width: 900px;
}

.cont-before:hover {
  background-color: var(--vt-c-text-dark-2);
}

span {
  font-size: 14px;
  color: var(--vt-c-text-light-2);
}
.cont-restrict {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4px;
}

.btn {
  width: 100%;
  padding: 10px 0;
  text-align: center;
  border-radius: 2px;
}

.btn-upload {
  background-color: hsla(160, 100%, 37%, 1);
  color: var(--vt-c-white-soft);
}

.btn-upload:hover {
  background-color: rgb(0, 165, 110);
}

/* after dropped */
.cont-after {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.cont-category select {
  padding: 12px;
  border: 1px solid var(--vt-c-text-dark-2);
  border-radius: 4px;
}

.cont-category select:focus {
  box-shadow: 0 0 3pt 2pt hsla(160, 100%, 37%, 0.2);
  border: none;
  outline: none;
}

.tit-content {
  display: flex;
  justify-content: space-between;
}

.cont-textarea {
  padding: 12px;
  border: 1px solid var(--vt-c-text-dark-2);
  border-radius: 4px;
}

.inp-content {
  width: 100%;
  user-select: text;
  max-height: 200px;
  overflow-wrap: break-word;
  white-space: pre-wrap;
  vertical-align: bottom;
}

.cont-candidate {
  display: flex;
}
.img-candidate {
  width: 85.75px;
  height: 150px;
  object-fit: cover;
  opacity: 0.3;
  user-select: none;
}

.cont-pick {
  position: relative;
  line-height: 0;
  padding: 6px;
  border: 1px solid var(--vt-c-white-mute);
  border-radius: 4px;
  width: 684px;
}
.cont-chosen {
  cursor: grab;
  position: absolute;
  top: 0;
  left: 0;
  width: 85.75px;
  height: 150px;
  transform-origin: center;
  transform: translate3d(4px, 1px, 0px) scaleX(1.1) scaleY(1.1);
  border: 6px solid rgb(255, 255, 255);
  overflow: hidden;
  box-shadow: rgba(34, 90, 89, 0.2) 2px 4px 20px;
  border-radius: 8px;

  z-index: 100;
  box-sizing: initial;
}

.video-chosen {
  object-fit: cover;
  vertical-align: top;
  width: 100%;
  height: 100%;
}

.cont-after .btn {
  width: 164px;
  box-sizing: border-box;
}
.btn-delete {
  width: 164px;
  border: 1px solid var(--vt-c-text-dark-2);
}

.btn-delete:hover {
  background-color: var(--vt-c-text-dark-2);
}

.cont-btn {
  display: flex;
  gap: 10px;
}
</style>
