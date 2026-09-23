<script setup>
import {onMounted, ref} from 'vue'
import TopicCard from '@/components/TopicCard.vue'
import {getPublicTopics} from '@/api/public/topics'

const started = ref(false)
const topics = ref([])
const loading = ref(true)
const error = ref(null)

function startTraining() {
  started.value = true
}

onMounted(async () => {
  try {
    topics.value = await getPublicTopics()
  } catch (exception) {
    error.value = 'Не удалось загрузить темы.'
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <main class="home">
    <section class="hero">
      <div v-if="!started">
        <h1>Java Interview Quiz</h1>

        <p class="subtitle">
          Подготовка к Java-собеседованиям через вопросы, практику и повторение.
        </p>

        <button class="start-button" @click="startTraining">
          Начать тренировку
        </button>
      </div>

      <div v-else>
        <h1>Начинаем тренировку</h1>

        <p class="subtitle">
          Выбери тему, которую хочешь изучить.
        </p>

        <p v-if="loading">
          Загружаем темы...
        </p>

        <p v-else-if="error">
          {{ error }}
        </p>

        <div class="topics">
          <TopicCard
            v-for="topic in topics"
            :key="topic.uuid"
            :name="topic.name"
          />
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
.home {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
}

.hero {
  width: 100%;
  max-width: 720px;
  text-align: center;
}

h1 {
  margin-bottom: 20px;
  font-size: 48px;
}

.subtitle {
  margin-bottom: 32px;
  font-size: 20px;
  line-height: 1.5;
}

.start-button,
.topics button {
  padding: 14px 28px;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
}

.topics {
  display: flex;
  justify-content: center;
  gap: 12px;
  flex-wrap: wrap;

  //grid-template-columns: repeat(3, 1fr);
  //gap: 16px;
}
</style>
