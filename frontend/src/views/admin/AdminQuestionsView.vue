<script setup>
import {onMounted, ref} from 'vue'
import {archiveAdminQuestion, getAdminQuestions, publishAdminQuestion,} from '@/api/admin/questions'

const questions = ref([])
const loading = ref(true)
const error = ref(null)

const actionError = ref(null)
const actionUuid = ref(null)

onMounted(async () => {
  try {
    const response = await getAdminQuestions()

    questions.value = response.content
  } catch (exception) {
    error.value = 'Не удалось загрузить вопросы.'
  } finally {
    loading.value = false
  }
})

async function publishQuestion(uuid) {
  actionUuid.value = uuid
  actionError.value = null

  try {
    const updatedQuestion = await publishAdminQuestion(uuid)

    const index = questions.value.findIndex(
      question => question.uuid === uuid,
    )

    if (index !== -1) {
      questions.value[index] = {
        ...questions.value[index],
        ...updatedQuestion,
      }
    }
  } catch (exception) {
    actionError.value = 'Не удалось опубликовать вопрос.'
  } finally {
    actionUuid.value = null
  }
}

async function archiveQuestion(uuid) {
  actionUuid.value = uuid
  actionError.value = null

  try {
    const updatedQuestion = await archiveAdminQuestion(uuid)

    const index = questions.value.findIndex(
      question => question.uuid === uuid,
    )

    if (index !== -1) {
      questions.value[index] = {
        ...questions.value[index],
        ...updatedQuestion,
      }
    }
  } catch (exception) {
    actionError.value = 'Не удалось архивировать вопрос.'
  } finally {
    actionUuid.value = null
  }
}
</script>

<template>
  <main class="admin-questions">
    <h1>Вопросы</h1>

    <p v-if="actionError">
      {{ actionError }}
    </p>

    <p v-if="loading">
      Загружаем вопросы...
    </p>

    <p v-else-if="error">
      {{ error }}
    </p>

    <p v-else-if="questions.length === 0">
      Вопросов пока нет.
    </p>

    <table v-if="!loading && !error && questions.length > 0">
      <thead>
      <tr>
        <th>Вопрос</th>
        <th>Тема</th>
        <th>Сложность</th>
        <th>Тип</th>
        <th>Статус</th>
        <th>Действия</th>
      </tr>
      </thead>

      <tbody>
      <tr
        v-for="question in questions"
        :key="question.uuid"
      >
        <td>{{ question.question }}</td>
        <td>{{ question.topicSlug }}</td>
        <td>{{ question.difficulty }}</td>
        <td>{{ question.type }}</td>
        <td>{{ question.status }}</td>

        <td class="actions">
          <RouterLink
            :to="`/admin/questions/${question.uuid}/edit`"
          >
            Редактировать
          </RouterLink>

          <button
            v-if="question.status === 'DRAFT'"
            :disabled="actionUuid === question.uuid"
            @click="publishQuestion(question.uuid)"
          >
            Опубликовать
          </button>

          <button
            v-if="
                question.status === 'DRAFT' ||
                question.status === 'PUBLISHED'
              "
            :disabled="actionUuid === question.uuid"
            @click="archiveQuestion(question.uuid)"
          >
            Архивировать
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </main>
</template>

<style scoped>
.admin-questions {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
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

.actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}
</style>
