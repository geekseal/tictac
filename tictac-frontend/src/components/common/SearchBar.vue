<template>
  <form class="wrapper">
    <label for="SearchBar" class="sr-only">계정과 영상 검색 바</label>
    <input type="text" id="SearchBar" placeholder="검색" class="inp-search" />
    <button class="btn-search">
      <IconBase>
        <IconSearch></IconSearch>
      </IconBase>
    </button>
  </form>
</template>

<script setup lang="ts">
import IconBase from '../icon/IconBase.vue';
import IconSearch from '../icon/IconSearch.vue';
</script>

<style scoped>
.wrapper {
  background-color: var(--vt-c-text-dark-2);
  display: flex;
  border-radius: 16px;
  overflow: hidden;
}

.wrapper:hover {
  border: 0.25px solid var(--vt-c-divider-dark-2);
}

.wrapper:hover .btn-search {
  background-color: var(--vt-c-divider-dark-2);
}

.inp-search {
  background-color: initial;
  font-size: 16px;
  padding: 10px 14px;
}

.btn-search {
  display: flex;
  align-items: center;
  padding: 0px 14px;
  cursor: pointer;

  border-left: 1px solid var(--vt-c-divider-light-2);
}
</style>
