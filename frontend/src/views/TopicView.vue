<script setup>
import {onMounted, ref} from 'vue'
import {useRoute} from 'vue-router'
import {getPublicQuestionsByTopic, getPublicTopicBySlug} from '@/api/public/topics'
import QuestionCard from '@/components/QuestionCard.vue'

const route = useRoute()

const topic = ref(null)
const loading = ref(true)
const error = ref(null)

const questions = ref([])
const questionsLoading = ref(true)
const questionsError = ref(null)

async function loadTopic() {
  try {
    topic.value = await getPublicTopicBySlug(route.params.slug)
  } catch (exception) {
    error.value = 'Не удалось загрузить тему.'
  } finally {
    loading.value = false
  }
}

async function loadQuestions() {
  try {
    const response = await getPublicQuestionsByTopic(route.params.slug)
    questions.value = response.content
  } catch (exception) {
    questionsError.value = 'Не удалось загрузить вопросы.'
  } finally {
    questionsLoading.value = false
  }
}

onMounted(() => {
  loadTopic()
  loadQuestions()
})
</script>

<template>
  <main class="topic">
    <p v-if="loading">
      Загружаем тему...
    </p>

    <p v-else-if="error">
      {{ error }}
    </p>

    <section v-else>
      <h1>{{ topic.name }}</h1>

      <p>
        {{ topic.description }}
      </p>

      <div class="questions">
        <h2>Вопросы</h2>

        <p v-if="questionsLoading">
          Загружаем вопросы...
        </p>

        <p v-else-if="questionsError">
          {{ questionsError }}
        </p>

        <p v-else-if="questions.length === 0">
          В этой теме пока нет вопросов.
        </p>

        <div v-else class="question-list">
          <QuestionCard
            v-for="question in questions"
            :key="question.uuid"
            :question="question.question"
            :difficulty="question.difficulty"
          />
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
.topic {
  max-width: 900px;
  margin: 0 auto;
  padding: 40px 20px;
}

.topic h1 {
  margin-bottom: 16px;
}

.topic p {
  line-height: 1.6;
}

.questions {
  margin-top: 48px;
}

.questions h2 {
  margin-bottom: 20px;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
