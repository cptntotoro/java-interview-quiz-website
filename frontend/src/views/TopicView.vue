<script setup>
import {onMounted, ref} from 'vue'
import {useRoute} from 'vue-router'
import {getPublicTopicBySlug} from '@/api/public/topics'

const route = useRoute()

const topic = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    topic.value = await getPublicTopicBySlug(route.params.slug)
  } catch (exception) {
    error.value = 'Не удалось загрузить тему.'
  } finally {
    loading.value = false
  }
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
</style>
