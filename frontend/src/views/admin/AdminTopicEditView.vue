<script setup>
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {getAdminTopic, updateAdminTopic,} from '@/api/admin/topics'

const route = useRoute()
const router = useRouter()

const topic = ref(null)
const loading = ref(true)
const saving = ref(false)

const error = ref(null)
const saveError = ref(null)

const form = ref({
  name: '',
  description: '',
  parentUuid: null,
  sortOrder: null,
})

onMounted(async () => {
  try {
    topic.value = await getAdminTopic(route.params.uuid)

    form.value = {
      name: topic.value.name,
      description: topic.value.description ?? '',
      parentUuid: topic.value.parentUuid,
      sortOrder: topic.value.sortOrder,
    }
  } catch (exception) {
    error.value = 'Не удалось загрузить тему.'
  } finally {
    loading.value = false
  }
})

async function saveTopic() {
  saving.value = true
  saveError.value = null

  try {
    await updateAdminTopic(route.params.uuid, form.value)

    await router.push('/admin/topics')
  } catch (exception) {
    saveError.value = 'Не удалось сохранить тему.'
  } finally {
    saving.value = false
  }
}
</script>

<template>
  <main class="admin-topic-edit">
    <p v-if="loading">
      Загружаем тему...
    </p>

    <p v-else-if="error">
      {{ error }}
    </p>

    <section v-else>
      <h1>Редактирование темы</h1>

      <form @submit.prevent="saveTopic">
        <label>
          Название

          <input
            v-model="form.name"
            type="text"
            required
          >
        </label>

        <label>
          Описание

          <textarea
            v-model="form.description"
            rows="5"
          ></textarea>
        </label>

        <label>
          Родительская тема UUID

          <input
            v-model="form.parentUuid"
            type="text"
          >
        </label>

        <label>
          Порядок

          <input
            v-model.number="form.sortOrder"
            type="number"
          >
        </label>

        <p v-if="saveError">
          {{ saveError }}
        </p>

        <button
          type="submit"
          :disabled="saving"
        >
          {{ saving ? 'Сохраняем...' : 'Сохранить' }}
        </button>
      </form>
    </section>
  </main>
</template>

<style scoped>
.admin-topic-edit {
  max-width: 700px;
  margin: 0 auto;
  padding: 40px 20px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

label {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

input,
textarea {
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font: inherit;
}

button {
  align-self: flex-start;
  padding: 10px 18px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

button:disabled {
  cursor: default;
  opacity: 0.6;
}
</style>
