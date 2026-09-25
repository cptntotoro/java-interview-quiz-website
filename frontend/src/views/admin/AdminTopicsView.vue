<script setup>
import {onMounted, ref} from 'vue'
import {
  archiveAdminTopic,
  createAdminTopic,
  getAdminTopics,
  publishAdminTopic
} from '@/api/admin/topics'

const topics = ref([])
const loading = ref(true)
const error = ref(null)

const form = ref({
  slug: '',
  name: '',
  description: '',
  parentUuid: null,
})

const creating = ref(false)
const createError = ref(null)

const actionError = ref(null)
const actionUuid = ref(null)

onMounted(async () => {
  try {
    const response = await getAdminTopics()
    topics.value = response.content
  } catch (exception) {
    error.value = 'Не удалось загрузить темы.'
  } finally {
    loading.value = false
  }
})

async function createTopic() {
  creating.value = true
  createError.value = null

  try {
    const topic = await createAdminTopic(form.value)

    topics.value.unshift(topic)

    form.value = {
      slug: '',
      name: '',
      description: '',
      parentUuid: null,
    }
  } catch (exception) {
    createError.value = 'Не удалось создать тему.'
  } finally {
    creating.value = false
  }
}

async function publishTopic(uuid) {
  actionUuid.value = uuid
  actionError.value = null

  try {
    const updatedTopic = await publishAdminTopic(uuid)

    const index = topics.value.findIndex(
      topic => topic.uuid === uuid,
    )

    if (index !== -1) {
      topics.value[index] = updatedTopic
    }
  } catch (exception) {
    actionError.value = 'Не удалось опубликовать тему.'
  } finally {
    actionUuid.value = null
  }
}

async function archiveTopic(uuid) {
  actionUuid.value = uuid
  actionError.value = null

  try {
    const updatedTopic = await archiveAdminTopic(uuid)

    const index = topics.value.findIndex(
      topic => topic.uuid === uuid,
    )

    if (index !== -1) {
      topics.value[index] = updatedTopic
    }
  } catch (exception) {
    actionError.value = 'Не удалось архивировать тему.'
  } finally {
    actionUuid.value = null
  }
}
</script>

<template>
  <main class="admin-topics">
    <h1>Темы</h1>

    <p v-if="actionError">
      {{ actionError }}
    </p>

    <section class="topics-list">
      <p v-if="loading">
        Загружаем темы...
      </p>

      <p v-else-if="error">
        {{ error }}
      </p>

      <p v-else-if="topics.length === 0">
        Тем пока нет.
      </p>

      <table v-if="!loading && !error && topics.length > 0">
        <thead>
        <tr>
          <th>Действия</th>
          <th>Название</th>
          <th>Slug</th>
          <th>Статус</th>
          <th>Порядок</th>
        </tr>
        </thead>

        <tbody>
        <tr
          v-for="topic in topics"
          :key="topic.uuid"
        >
          <td class="actions">
            <RouterLink
              :to="`/admin/topics/${topic.uuid}/edit`"
            >
              Редактировать
            </RouterLink>

            <button
              v-if="topic.status === 'DRAFT'"
              :disabled="actionUuid === topic.uuid"
              @click="publishTopic(topic.uuid)"
            >
              Опубликовать
            </button>

            <button
              v-if="topic.status === 'DRAFT' || topic.status === 'PUBLISHED'"
              :disabled="actionUuid === topic.uuid"
              @click="archiveTopic(topic.uuid)"
            >
              Архивировать
            </button>
          </td>

          <td>{{ topic.name }}</td>
          <td>{{ topic.slug }}</td>
          <td>{{ topic.status }}</td>
          <td>{{ topic.sortOrder }}</td>
        </tr>
        </tbody>
      </table>
    </section>

    <section class="create-topic">
      <h2>Создать тему</h2>

      <form @submit.prevent="createTopic">
        <label>
          Название

          <input
            v-model="form.name"
            type="text"
            required
          >
        </label>

        <label>
          Slug

          <input
            v-model="form.slug"
            type="text"
            required
          >
        </label>

        <label>
          Описание

          <textarea
            v-model="form.description"
            rows="4"
          ></textarea>
        </label>

        <p v-if="createError">
          {{ createError }}
        </p>

        <button
          type="submit"
          :disabled="creating"
        >
          {{ creating ? 'Создаём...' : 'Создать тему' }}
        </button>
      </form>
    </section>
  </main>
</template>

<style scoped>
.admin-topics {
  max-width: 1100px;
  margin: 0 auto;
  padding: 40px 20px;
}

.actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.create-topic {
  margin-bottom: 40px;
  padding: 24px;
  border: 1px solid #ddd;
  border-radius: 12px;
}

.create-topic form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.create-topic label {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.create-topic input,
.create-topic textarea {
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font: inherit;
}

.create-topic button {
  align-self: flex-start;
  padding: 10px 18px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.create-topic button:disabled {
  cursor: default;
  opacity: 0.6;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 12px;
  border-bottom: 1px solid #ddd;
  text-align: left;
}

th {
  font-weight: 600;
}
</style>
