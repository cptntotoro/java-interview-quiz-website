export async function getAdminTopics(page = 0, size = 20) {
  const params = new URLSearchParams({
    page, size,
  })

  const response = await fetch(`/api/v1/admin/topics?${params}`)

  if (!response.ok) {
    throw new Error(`Failed to load admin topics: ${response.status}`)
  }

  return response.json()
}

export async function createAdminTopic(topic) {
  const response = await fetch('/api/v1/admin/topics', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(topic),
  })

  if (!response.ok) {
    throw new Error(`Failed to create topic: ${response.status}`)
  }

  return response.json()
}

export async function getAdminTopic(uuid) {
  const response = await fetch(`/api/v1/admin/topics/${uuid}`)

  if (!response.ok) {
    throw new Error(`Failed to load topic: ${response.status}`)
  }

  return response.json()
}

export async function updateAdminTopic(uuid, topic) {
  const response = await fetch(`/api/v1/admin/topics/${uuid}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(topic),
  })

  if (!response.ok) {
    throw new Error(`Failed to update topic: ${response.status}`)
  }

  return response.json()
}

export async function publishAdminTopic(uuid) {
  const response = await fetch(`/api/v1/admin/topics/${uuid}/publish`, {
    method: 'PATCH',
  })

  if (!response.ok) {
    throw new Error(`Failed to publish topic: ${response.status}`)
  }

  return response.json()
}

export async function archiveAdminTopic(uuid) {
  const response = await fetch(`/api/v1/admin/topics/${uuid}/archive`, {
    method: 'PATCH',
  })

  if (!response.ok) {
    throw new Error(`Failed to archive topic: ${response.status}`)
  }

  return response.json()
}
