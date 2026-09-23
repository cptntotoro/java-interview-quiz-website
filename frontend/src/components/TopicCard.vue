<script setup>
defineProps({
  name: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <article class="topic-card">
    <h2>{{ name }}</h2>

    <button>
      Начать
    </button>
  </article>
</template>

<style scoped>
.topic-card {
  padding: 24px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background: white;
}

.topic-card h2 {
  margin: 0 0 16px;
}
</style>
