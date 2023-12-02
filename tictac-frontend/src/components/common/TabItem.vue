<template>
  <button @click="handleTab" :class="[active, 'tab']">
    <IconBase v-if="isNotMyList">
      <IconLock></IconLock>
    </IconBase>
    {{ label }}
  </button>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';

import IconBase from '../icon/IconBase.vue';
import IconLock from '../icon/IconLock.vue';

const route = useRoute();
const props = defineProps(['id', 'label', 'currentId']);
const emit = defineEmits(['update:currentId', 'update:currentWidth']);
const active = computed(() => (props.currentId === props.id ? 'active' : false));

const handleTab = (e: MouseEvent) => {
  emit('update:currentId', props.id);
  const button = e.target as HTMLButtonElement;
  emit('update:currentWidth', button.clientWidth);
};

const loginUserId = sessionStorage.getItem('userId');
const isNotMyList = props.label === '즐겨찾기' && loginUserId !== route.params.userId.slice(1);
</script>

<style scoped>
.tab {
  padding: 0 32px;
  font-size: 18px;
  color: var(--vt-c-divider-dark-1);
  height: 44px;
  font-weight: bold;
  border-bottom: 2px solid transparent;
}
.active {
  color: var(--vt-c-black);
}
</style>
