export async function getAdminQuestions({
                                          page = 0,
                                          size = 20,
                                          difficulty,
                                          status,
                                        } = {}) {
  const params = new URLSearchParams({
    page,
    size,
  })

  if (difficulty) {
    params.set('difficulty', difficulty)
  }

  if (status) {
    params.set('status', status)
  }

  const response = await fetch(`/api/v1/admin/questions?${params}`)

  if (!response.ok) {
    throw new Error(`Failed to load admin questions: ${response.status}`)
  }

  return response.json()
}

export async function createAdminQuestion(question) {
  const response = await fetch('/api/v1/admin/questions', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(question),
  })

  if (!response.ok) {
    throw new Error(`Failed to create question: ${response.status}`)
  }

  return response.json()
}

export async function updateAdminQuestion(uuid, question) {
  const response = await fetch(`/api/v1/admin/questions/${uuid}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(question),
  })

  if (!response.ok) {
    throw new Error(`Failed to update question: ${response.status}`)
  }

  return response.json()
}

export async function publishAdminQuestion(uuid) {
  const response = await fetch(
    `/api/v1/admin/questions/${uuid}/publish`,
    {
      method: 'PATCH',
    },
  )

  if (!response.ok) {
    throw new Error(`Failed to publish question: ${response.status}`)
  }

  return response.json()
}

export async function archiveAdminQuestion(uuid) {
  const response = await fetch(
    `/api/v1/admin/questions/${uuid}/archive`,
    {
      method: 'PATCH',
    },
  )

  if (!response.ok) {
    throw new Error(`Failed to archive question: ${response.status}`)
  }

  return response.json()
}
