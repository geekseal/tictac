<!-- https://v2.vuejs.org/v2/cookbook/editable-svg-icons -->
<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    :width="width"
    :height="height"
    viewBox="0 0 25 24"
    :aria-labelledby="iconName"
    role="presentation"
  >
    <title :id="iconName" lang="en">{{ iconName }} icon</title>
    <g :fill="iconColor" :stroke="strokeColor" :stroke-width="strokeWidth">
      <slot />
    </g>
  </svg>
</template>

<script setup lang="ts">
defineProps({
  iconName: {
    type: String,
    default: 'box'
  },
  width: {
    type: [Number, String],
    default: 25
  },
  height: {
    type: [Number, String],
    default: 24
  },
  iconColor: {
    type: String,
    default: 'currentColor'
  },
  strokeColor: {
    type: String,
    default: 'currentColor'
  },
  strokeWidth: {
    type: [Number, String],
    default: 0
  }
});
</script>

<style scoped>
svg {
  vertical-align: top;
}
</style>
