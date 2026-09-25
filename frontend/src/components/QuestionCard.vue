<script setup>
defineProps({
  question: {
    type: String,
    required: true,
  },
  difficulty: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <article class="question-card">
    <span class="difficulty">
      {{ difficulty }}
    </span>

    <h2>{{ question }}</h2>
  </article>
</template>

<style scoped>
.question-card {
  padding: 24px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background: white;
  text-align: left;
}

.question-card h2 {
  margin: 12px 0 0;
  font-size: 20px;
  line-height: 1.4;
}

.difficulty {
  font-size: 14px;
  color: #666;
}
</style>
