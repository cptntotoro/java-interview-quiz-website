export async function getPublicTopics() {
  const response = await fetch('/api/v1/public/topics')

  if (!response.ok) {
    throw new Error(`Failed to load topics: ${response.status}`)
  }

  return response.json()
}

export async function getPublicQuestionsByTopic(topicSlug, page = 0, size = 20) {
  const params = new URLSearchParams({
    page,
    size,
  })

  const response = await fetch(
    `/api/v1/public/questions/topics/${topicSlug}/questions?${params}`,
  )

  if (!response.ok) {
    throw new Error(`Failed to load questions: ${response.status}`)
  }

  return response.json()
}
