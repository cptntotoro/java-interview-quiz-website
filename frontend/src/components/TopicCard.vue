<script setup>
defineProps({
  name: {
    type: String,
    required: true,
  },
  slug: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <article class="topic-card">
    <h2>{{ name }}</h2>

    <RouterLink
      :to="`/topics/${slug}`"
      class="start-button"
    >
      Начать
    </RouterLink>

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

.start-button {
  display: inline-block;
  padding: 10px 18px;
  border-radius: 8px;
  background: #333;
  color: white;
  text-decoration: none;
}
</style>
