export async function getPublicTopics() {
  const response = await fetch('/api/v1/public/topics')

  if (!response.ok) {
    throw new Error(`Failed to load topics: ${response.status}`)
  }

  return response.json()
}

export async function getPublicTopicBySlug(slug) {
  const response = await fetch(`/api/v1/public/topics/${slug}`)

  if (!response.ok) {
    throw new Error(`Failed to load topic: ${response.status}`)
  }

  return response.json()
}
